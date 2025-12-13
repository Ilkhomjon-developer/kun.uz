package dasturlash.uz.dto.profile;

import com.fasterxml.jackson.annotation.JsonInclude;
import dasturlash.uz.enums.ProfileRole;
import dasturlash.uz.enums.ProfileStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDTO {


    private Long id;

    @NotBlank(message = "name cannot be null")
    private String name;

    @NotBlank(message = "surname cannot be null")
    private String surname;

    @NotBlank(message = "username cannot be null")
    private String username;    // email/phone

    @NotBlank(message = "password cannot be null")
    private String password;

    private ProfileStatus status;
    private List<ProfileRole> roleList;
    private LocalDateTime createdDate;
    private String token;
    private String jwt;


}
