package dasturlash.uz.service;

import dasturlash.uz.dto.ProfileDTO;
import dasturlash.uz.entity.ProfileRoleEntity;
import dasturlash.uz.enums.ProfileRole;
import dasturlash.uz.exps.AppBadException;
import dasturlash.uz.repository.ProfileRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileRoleService {

    @Autowired
    private ProfileRoleRepository profileRoleRepository;

    public void createRole(Integer profileId, ProfileDTO dto){

        if(dto.getRoleList().isEmpty()){

            throw new AppBadException("Role list is empty");
        }

        for (ProfileRole role : dto.getRoleList()){
            ProfileRoleEntity entity = new ProfileRoleEntity();
            entity.setProfileId(profileId);
            entity.setRoles(role);

            profileRoleRepository.save(entity);
        }


    }

    public void updateRole(Integer profileId, ProfileDTO dto){

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
