package dasturlash.uz.service.mail;

import dasturlash.uz.dto.sms.SmsProviderTokenDTO;
import dasturlash.uz.dto.sms.SmsTokenProviderResponse;
import dasturlash.uz.entity.mail.TokenEntity;
import dasturlash.uz.repository.mail.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;
    private final RestTemplate restTemplate;

    @Value("${sms.eskiz.email}")
    private String email;
    @Value("${sms.eskiz.password}")
    private String password;

    private final String url = "https://notify.eskiz.uz/api/";



    public String getToken(){

        Optional<TokenEntity> optional = tokenRepository.findTopByOrderByCreatedDateDesc();

        if(optional.isPresent()){

            TokenEntity tokenEntity = optional.get();

            LocalDateTime tokenCreatedDate = tokenEntity.getCreatedDate();
            LocalDateTime now = LocalDateTime.now();

            long days = Duration.between(tokenCreatedDate, now).toDaysPart();

            if(days >= 30){
                createNewToken();
            }else if(days == 29){
                refreshToken(tokenEntity.getToken());
            }else{
                    return tokenEntity.getToken();
            }

        }
        return createNewToken();
    }


    private String createNewToken() {
        SmsProviderTokenDTO smsProviderTokenDTO = new SmsProviderTokenDTO();
        smsProviderTokenDTO.setEmail(email);
        smsProviderTokenDTO.setPassword(password);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        RequestEntity<SmsProviderTokenDTO> request = RequestEntity
                .post(url + "auth/login")
                .headers(headers)
                .body(smsProviderTokenDTO);

        var response = restTemplate.exchange(request, SmsTokenProviderResponse.class);

        String token = response.getBody().getData().getToken();

        TokenEntity entity = new TokenEntity();
        entity.setToken(token);

        tokenRepository.save(entity);

        return token;
    }


    public String refreshToken(String oldToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + oldToken);
        headers.set("Content-Type", "application/json");

        RequestEntity<Void> request = RequestEntity
                .patch(url + "auth/refresh")
                .headers(headers)
                .build();

        var response = restTemplate.exchange(request, SmsTokenProviderResponse.class);
        String newToken = response.getBody().getData().getToken();

        TokenEntity entity = new TokenEntity();
        entity.setToken(newToken);
        tokenRepository.save(entity);

        return newToken;
    }

}
