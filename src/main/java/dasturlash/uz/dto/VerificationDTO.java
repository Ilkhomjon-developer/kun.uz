package dasturlash.uz.dto;

import jakarta.validation.constraints.NotNull;

public record VerificationDTO(@NotNull(message = ("Username cannot be null")) String username,
                              @NotNull(message = ("Email code cannot be null")) Integer code) {
}
