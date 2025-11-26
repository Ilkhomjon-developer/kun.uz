package dasturlash.uz.service;

import dasturlash.uz.dto.AuthDTO;
import dasturlash.uz.dto.ProfileDTO;
import dasturlash.uz.dto.RegistrationDTO;
import dasturlash.uz.entity.ProfileEntity;
import dasturlash.uz.enums.ProfileRole;
import dasturlash.uz.enums.ProfileStatus;
import dasturlash.uz.exps.AppBadException;
import dasturlash.uz.repository.ProfileRepository;
import dasturlash.uz.repository.ProfileRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileRoleRepository profileRoleRepository;

    @Autowired
    private ProfileRoleService profileRoleService;

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
        entity.setPassword(dto.password());

        profileRepository.save(entity);

        List<ProfileRole> roleList = List.of(ProfileRole.ROLE_USER);
        profileRoleService.createRole(entity.getId(), roleList);

        return "Successfully registered";

    }

    public ProfileDTO login(AuthDTO dto){

        Optional<ProfileEntity> optional = profileRepository.findByUsernameAndVisibleIsTrue(dto.username());

        if(optional.isEmpty()){
            throw new AppBadException("Username or password is incorrect");
        }

        if(!optional.get().getPassword().equals(dto.password())){
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
