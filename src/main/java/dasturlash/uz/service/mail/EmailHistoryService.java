package dasturlash.uz.service.mail;

import dasturlash.uz.entity.mail.EmailHistoryEntity;
import dasturlash.uz.exps.AppBadException;
import dasturlash.uz.repository.mail.EmailHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class EmailHistoryService {

    @Autowired
    private EmailHistoryRepository emailHistoryRepository;


    public void create(String body, Integer smsCode, String toAccount) {

        EmailHistoryEntity entity = new EmailHistoryEntity();
        entity.setMessage(body);
        entity.setCode(smsCode);
        entity.setEmail(toAccount);
        emailHistoryRepository.save(entity);
    }


    public boolean isSmsSendToAccount(String username, Integer emailCode){

        Optional<EmailHistoryEntity> emailOptional = emailHistoryRepository.findByUsername(username);
        if(emailOptional.isEmpty()){
            return false;
        }
        if(!emailOptional.get().getCode().equals(emailCode)){
            return false;
        }

        if(emailOptional.get().getCreatedDate().plus(Duration.ofMinutes(2)).isBefore(LocalDateTime.now())){
          throw new AppBadException("Code  expired");
        }

        return true;

    }

}
