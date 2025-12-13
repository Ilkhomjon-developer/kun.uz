package dasturlash.uz.service.profile;

import dasturlash.uz.dto.profile.ProfileDTO;
import dasturlash.uz.entity.profile.ProfileEntity;
import dasturlash.uz.exps.AppBadException;
import dasturlash.uz.repository.profile.ProfileRepository;
import dasturlash.uz.repository.profile.ProfileRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileRoleService profileRoleService;

    @Autowired
    private ProfileRoleRepository profileRoleRepository;


    public ProfileDTO create(ProfileDTO dto) {

        Optional<ProfileEntity> optional = profileRepository
                .findByUsernameAndVisibleIsTrue(dto.getUsername());


        if(optional.isPresent()){
            throw new AppBadException("Profile already exist found");
        }

        ProfileEntity entity = new ProfileEntity();

        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword());
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setStatus(dto.getStatus());

        profileRepository.save(entity);

        profileRoleService.createRole(entity.getId(),dto.getRoleList());

        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());

        return dto;
    }

    public ProfileDTO update(Long id, ProfileDTO dto) {
        Optional<ProfileEntity> optional = profileRepository
                .findByIdAndVisibleIsTrue(id);
        if(optional.isEmpty()){
            throw new AppBadException("Profile not found");
        }

        ProfileEntity entity = optional.get();

        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword());

        profileRepository.save(entity);

        profileRoleService.updateRole(entity.getId(), dto);

        return dto;
    }

    public ProfileDTO updateAny(Long id, ProfileDTO dto) {
        Optional<ProfileEntity> optional = profileRepository
                .findByIdAndVisibleIsTrue(id);

        if(optional.isEmpty()){
            throw new AppBadException("Profile not found");
        }

        if(!id.equals(dto.getId())){
            throw new AppBadException("You can only update your profile");
        }

        ProfileEntity entity = optional.get();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword());

        profileRepository.save(entity);

        return dto;
    }

    public PageImpl<ProfileDTO> pagination( int page, int size) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<ProfileEntity> pageEntities = profileRepository.findAllByVisibleIsTrue(pageRequest);

        long total = pageEntities.getTotalElements();
        List<ProfileEntity> profileList = pageEntities.getContent();

        List<ProfileDTO> dtoList = new ArrayList<>();

        for (ProfileEntity entity : profileList) {
            ProfileDTO dto = new ProfileDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setUsername(entity.getUsername());
            dto.setSurname(entity.getSurname());
            dto.setPassword(entity.getPassword());
            dto.setRoleList(profileRoleRepository.getAllRolesListByProfileId(entity.getId()));
            dto.setStatus(entity.getStatus());
            dtoList.add(dto);
        }
        return new PageImpl<>(dtoList, pageRequest, total);

    }

    public Boolean delete(Long id) {

        return profileRepository.updateVisibleById(id, false) == 1;
    }

    public Boolean updatePassword(Long id, String newPassword) {

        Optional<ProfileEntity> optional = profileRepository
                .findByIdAndVisibleIsTrue(id);

        if(optional.isEmpty()){
            throw new AppBadException("Profile not found");
        }

        ProfileEntity entity = optional.get();
        entity.setPassword(newPassword);
        profileRepository.save(entity);

        return true;
    }



}
