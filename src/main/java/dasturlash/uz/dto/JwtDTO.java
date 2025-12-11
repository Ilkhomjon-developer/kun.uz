package dasturlash.uz.dto;

import dasturlash.uz.enums.ProfileRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@AllArgsConstructor
public class JwtDTO {

    private String username;
    private List<ProfileRole> roles;
}
