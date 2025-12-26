package dasturlash.uz.config;

import dasturlash.uz.entity.profile.ProfileEntity;
import dasturlash.uz.enums.ProfileRole;
import dasturlash.uz.repository.profile.ProfileRepository;
import dasturlash.uz.repository.profile.ProfileRoleRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Setter
@Getter
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final ProfileRepository profileRepository;
    private final ProfileRoleRepository profileRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<ProfileEntity> optional = profileRepository.findByUsernameAndVisibleIsTrue(username);

        if(optional.isEmpty()){
            throw new UsernameNotFoundException("User not found");
        }

        ProfileEntity profile = optional.get();
        List<ProfileRole> roles = profileRoleRepository.getAllRolesListByProfileId(profile.getId());

        return new CustomUserDetails(profile, roles);
    }
}
