package dasturlash.uz.service.mail;

import dasturlash.uz.dto.sms.SmsHistoryDTO;
import dasturlash.uz.entity.mail.SmsHistoryEntity;
import dasturlash.uz.exps.AppBadException;
import dasturlash.uz.repository.mail.SmsHistoryRepository;
import dasturlash.uz.repository.mail.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SmsHistoryService {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private SmsHistoryRepository smsHistoryRepository;



    public boolean isSmsSendToAccount(String username, Integer smsCode){
        Optional<SmsHistoryEntity> optional = smsHistoryRepository.findByPhone(username);

        if(optional.isEmpty()){
            return false;
        }

       int attempts =  optional.get().getAttemptCount();

        if(attempts >= 5){
            return false;
        }

        if(!optional.get().getCode().equals(smsCode)){

            increaseAttempt(optional.get().getId());
            return false;
        }

        if(optional.get().getCreatedDate().plus(Duration.ofMinutes(2)).isBefore(LocalDateTime.now())){
            throw new AppBadException("Sms Code expired");
        }

        return true;
    }

    public void increaseAttempt(String id){

        smsHistoryRepository.increaseAttempt(id);

    }

    public void save(String phone, int smsCode, String body) {

        SmsHistoryEntity entity = new SmsHistoryEntity();
        entity.setPhone(phone);
        entity.setCode(smsCode);
        entity.setMessage(body);
        smsHistoryRepository.save(entity);
    }



    public SmsHistoryEntity getSmsByPhone(String phone){

        Optional<SmsHistoryEntity> optional = smsHistoryRepository.findByPhone(phone);
        if (optional.isEmpty()){
            throw new AppBadException("Invalid phone number");
        }
        return optional.get();
    }

    public List<SmsHistoryDTO> getSmsHistoryByPhone(String phone) {
        // Find entities by toAccount (which is 'email' in the DTO context)
        List<SmsHistoryEntity> entities = smsHistoryRepository.findAllByPhone(phone);
        // Convert entities to DTOs and return
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<SmsHistoryDTO> getSmsByGivenDate(LocalDate date){

        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

       List<SmsHistoryEntity> entityList = smsHistoryRepository.findByCreatedDateBetween(startOfDay, endOfDay);

       return entityList.stream().map(this::toDto).collect(Collectors.toList());


    }

    public SmsHistoryDTO toDto(SmsHistoryEntity entity) {
        if (entity == null) {
            return null;
        }
        SmsHistoryDTO dto = new SmsHistoryDTO();
        dto.setId(entity.getId());
        dto.setPhoneNumber(entity.getPhone());
        dto.setBody(entity.getMessage());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public PageImpl<SmsHistoryDTO> pagination(int page, int size){

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());

        // Retrieve a page of entities from the repository
        Page<SmsHistoryEntity> entityPage = smsHistoryRepository.findAll(pageable);

        List<SmsHistoryDTO> dtoList = entityPage.getContent().stream().map(this::toDto).collect(Collectors.toList());

        return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
    }
}


