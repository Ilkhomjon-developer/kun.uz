package dasturlash.uz.service;

import dasturlash.uz.dto.AuthDTO;
import dasturlash.uz.dto.ProfileDTO;
import dasturlash.uz.dto.RegistrationDTO;
import dasturlash.uz.dto.VerificationDTO;
import dasturlash.uz.entity.EmailHistoryEntity;
import dasturlash.uz.entity.ProfileEntity;
import dasturlash.uz.enums.ProfileRole;
import dasturlash.uz.enums.ProfileStatus;
import dasturlash.uz.exps.AppBadException;
import dasturlash.uz.repository.EmailHistoryRepository;
import dasturlash.uz.repository.ProfileRepository;
import dasturlash.uz.repository.ProfileRoleRepository;
import dasturlash.uz.util.RandomNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ProfileRoleRepository profileRoleRepository;

    @Autowired
    private ProfileRoleService profileRoleService;

    @Autowired
    private EmailSendingService emailSendingService;

    @Autowired
    private EmailHistoryRepository emailHistoryRepository;


    public String registration(RegistrationDTO dto){

        Optional<ProfileEntity> optional = profileRepository.findByUsernameAndVisibleIsTrue(dto.username());

        if(optional.isPresent()){

            ProfileEntity profile = optional.get();
            if(profile.getStatus().equals(ProfileStatus.REGISTERING)){

                emailHistoryRepository.deleteByProfileId(profile.getId());
                profileRoleRepository.deleteByProfileId(profile.getId());
                profileRepository.deleteById(profile.getId());

            }else {
                throw new AppBadException("Profile already exist");
            }
        }

        ProfileEntity entity = new ProfileEntity();

        entity.setUsername(dto.username());
        entity.setName(dto.name());
        entity.setSurname(dto.surname());
        entity.setStatus(ProfileStatus.REGISTERING);
        entity.setPassword(bCryptPasswordEncoder.encode(dto.password()));

        profileRepository.save(entity);

        List<ProfileRole> roleList = List.of(ProfileRole.ROLE_USER);
        profileRoleService.createRole(entity.getId(), roleList);

        int code = RandomNumberGenerator.generate();
        emailSendingService.sendSimpleMessage(dto.username(), "Complete registration", "Please, confirm your registration "  + code);


        EmailHistoryEntity emailHistoryEntity = new EmailHistoryEntity();
        emailHistoryEntity.setMessage("Please, confirm your registration");
        emailHistoryEntity.setCode(code);
        emailHistoryEntity.setEmail(dto.username());
        emailHistoryEntity.setProfileId(entity.getId());

        emailHistoryRepository.save(emailHistoryEntity);

        return "Successfully registered";
    }

    public String regVerification(VerificationDTO dto){

        Optional<ProfileEntity> optional = profileRepository.findByUsernameAndVisibleIsTrue(dto.username());
        if(optional.isEmpty()){
            throw new AppBadException("Profile not found");
        }
        if (!bCryptPasswordEncoder.matches(dto.password(), optional.get().getPassword())){
            throw new AppBadException("Username or Password is incorrect");
        }
        ProfileEntity entity = optional.get();

        Optional<EmailHistoryEntity> emailOptional = emailHistoryRepository.findByProfileId(entity.getId());
        if(emailOptional.isEmpty()){
            throw new AppBadException("Email history not found");
        }
        if(!emailOptional.get().getCode().equals(dto.code())){
            throw new AppBadException("Email verification Code is incorrect");
        }

        if(emailOptional.get().getCreatedDate().plus(Duration.ofMinutes(2)).isBefore(LocalDateTime.now())){
            throw new AppBadException("Email verification Code is expired");
        }

        if(entity.getStatus().equals(ProfileStatus.REGISTERING)){
            entity.setStatus(ProfileStatus.ACTIVE);
            profileRepository.save(entity);
        }else {
            throw new AppBadException("Profile already active");
        }

        return "Successfully verified";
    }

    public ProfileDTO login(AuthDTO dto){

        Optional<ProfileEntity> optional = profileRepository.findByUsernameAndVisibleIsTrue(dto.username());

        if(optional.isEmpty()){
            throw new AppBadException("Username or password is incorrect");
        }

        if(!bCryptPasswordEncoder.matches(dto.password(), optional.get().getPassword())){
            throw new AppBadException("Username or password is incorrect");
        }

        if(!optional.get().getStatus().equals(ProfileStatus.ACTIVE)){
            throw new AppBadException("Your account is not active");
        }

        ProfileDTO response = new ProfileDTO();
        response.setId(optional.get().getId());
        response.setUsername(optional.get().getUsername());
        response.setName(optional.get().getName());
        response.setSurname(optional.get().getSurname());
        response.setRoleList(profileRoleRepository.getAllRolesListByProfileId(optional.get().getId()));
        response.setStatus(optional.get().getStatus());

        // token

        return response;

    }
}
