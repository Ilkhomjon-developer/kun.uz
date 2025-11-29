package dasturlash.uz.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RegistrationDTO(@NotBlank(message = ("Name cannot be null")) String name,
                              @NotBlank(message = ("Surname cannot be null")) String surname,
                              @NotBlank(message = ("Username cannot be null")) String username,
                              @NotBlank(message = ("Password cannot be null")) String password) {

}
