package dasturlash.uz.service;

import dasturlash.uz.dto.auth.AuthDTO;
import dasturlash.uz.dto.profile.ProfileDTO;
import dasturlash.uz.dto.auth.RegistrationDTO;
import dasturlash.uz.dto.auth.VerificationDTO;
import dasturlash.uz.entity.profile.ProfileEntity;
import dasturlash.uz.enums.ProfileRole;
import dasturlash.uz.enums.ProfileStatus;
import dasturlash.uz.exps.AppBadException;
import dasturlash.uz.repository.profile.ProfileRepository;
import dasturlash.uz.repository.profile.ProfileRoleRepository;
import dasturlash.uz.service.mail.*;
import dasturlash.uz.service.profile.ProfileRoleService;
import dasturlash.uz.util.UsernameValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
    private SmsSenderService smsSenderService;

    @Autowired
    private EmailHistoryService emailHistoryService;

    @Autowired
    private SmsHistoryService smsHistoryService;


    public String registration(RegistrationDTO dto){

        Optional<ProfileEntity> optional = profileRepository.findByUsernameAndVisibleIsTrue(dto.username());

        if(optional.isPresent()){

            ProfileEntity profile = optional.get();
            if(profile.getStatus().equals(ProfileStatus.REGISTERING)){

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

        if(UsernameValidation.isEmail(dto.username())){
            emailSendingService.sendRegistrationStyledEmail(dto.username());

        } else if(UsernameValidation.isPhone(dto.username())) {
            smsSenderService.sendRegistrationSMS(dto.username());

        }else {
            throw new AppBadException("Phone or email is incorrect");
        }

        return "Successfully registered";
    }


    public String regVerification(VerificationDTO dto){

        Optional<ProfileEntity> optional = profileRepository.findByUsernameAndVisibleIsTrue(dto.username());
        if(optional.isEmpty()){
            throw new AppBadException("Profile not found");
        }
        if(!optional.get().getStatus().equals(ProfileStatus.REGISTERING)){
            throw new AppBadException("Profile in wrong status");
        }

        ProfileEntity entity = optional.get();

        if(UsernameValidation.isEmail(dto.username()) && emailHistoryService.isSmsSendToAccount(dto.username(), dto.code())){
            entity.setStatus(ProfileStatus.ACTIVE);
            profileRepository.save(entity);
        } else if (UsernameValidation.isPhone(dto.username()) && smsHistoryService.isSmsSendToAccount(dto.username(), dto.code())) {
            entity.setStatus(ProfileStatus.ACTIVE);
            profileRepository.save(entity);

        } else if(entity.getStatus().equals(ProfileStatus.ACTIVE)){
            throw new AppBadException("Profile already active");
        }else{
            throw new AppBadException("Verification code is incorrect or check your phone number or email");
        }

        return "Account Successfully Activated";
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
