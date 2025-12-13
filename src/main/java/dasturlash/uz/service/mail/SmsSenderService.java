package dasturlash.uz.service.mail;

import dasturlash.uz.exps.AppBadException;
import dasturlash.uz.util.RandomNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


@Service
public class SmsSenderService {

    @Autowired
    private SmsHistoryService smsHistoryService;


    @Autowired
    private RestTemplate restTemplate;


    @Value("${sms.eskiz.email}")
    private String email;

    @Value("${sms.eskiz.password}")
    private String password;


    @Autowired
    private TokenService tokenService;


    public void sendRegistrationSMS(String phone) {
        int smsCode = RandomNumberGenerator.generate();
        String body = "Bu Eskiz dan test "; // test message
        try {
            sendSms(phone, body);
            smsHistoryService.save(phone, smsCode, body);

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppBadException("Something went wrong");
        }
    }



    private void sendSms(String phone, String body){

        String url = "https://notify.eskiz.uz/api/message/sms/send";

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("mobile_phone", phone);
        formData.add("message", body);


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Bearer " + tokenService.getToken());

        RequestEntity<MultiValueMap<String, String>> request = RequestEntity
                .post(url)
                .headers(headers)
                .body(formData);

        restTemplate.exchange(request, String.class);
    }


}
