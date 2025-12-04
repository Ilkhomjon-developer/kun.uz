package dasturlash.uz.dto.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AuthDTO(@NotNull(message = ("Username cannot be null")) String username,
                      @NotNull(message = ("Password cannot be null")) String password) {
}
