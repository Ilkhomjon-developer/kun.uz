package dasturlash.uz.dto;

import dasturlash.uz.enums.GeneralStatus;
import dasturlash.uz.enums.ProfileRole;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ProfileDTO {

    @NotBlank
    private Integer id;

    @NotBlank(message = "name cannot be null")
    private String name;

    @NotBlank(message = "surname cannot be null")
    private String surname;

    @NotBlank(message = "username cannot be null")
    private String username;    // email/phone

    @NotBlank(message = "password cannot be null")
    private String password;

    private GeneralStatus status;
    private List<ProfileRole> roleList;
    private LocalDateTime createdDate;


}
