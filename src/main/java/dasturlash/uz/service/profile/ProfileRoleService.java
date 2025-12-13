package dasturlash.uz.service.profile;

import dasturlash.uz.dto.profile.ProfileDTO;
import dasturlash.uz.entity.profile.ProfileRoleEntity;
import dasturlash.uz.enums.ProfileRole;
import dasturlash.uz.exps.AppBadException;
import dasturlash.uz.repository.profile.ProfileRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileRoleService {

    @Autowired
    private ProfileRoleRepository profileRoleRepository;

    public void createRole(Long profileId, List<ProfileRole> roleList){

        if(roleList.isEmpty()){

            throw new AppBadException("Role list is empty");
        }

        for (ProfileRole role :roleList){
            ProfileRoleEntity entity = new ProfileRoleEntity();
            entity.setProfileId(profileId);
            entity.setRoles(role);

            profileRoleRepository.save(entity);
        }


    }

    public void updateRole(Long profileId, ProfileDTO dto){

        if(dto.getRoleList().isEmpty()){
            throw new AppBadException("Role list is empty");
        }

        profileRoleRepository.deleteByProfileId(profileId);

        for (ProfileRole role : dto.getRoleList()){
            ProfileRoleEntity entity = new ProfileRoleEntity();
            entity.setProfileId(profileId);
            entity.setRoles(role);

            profileRoleRepository.save(entity);
        }

    }
}
