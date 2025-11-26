package dasturlash.uz.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CategoryDTO {

    // order_number,nameUz, nameRu, nameEn, key
    private Integer id;

    @NotEmpty(message = "Order number required")
    @Positive(message = "Order number must be positive")
    private String orderNumber;

    @NotEmpty(message = "nameUz  required")
    private String nameUz;
    @NotEmpty(message = "nameRu  required")
    private String nameRu;
    @NotBlank(message = "nameEn  required")
    private String nameEn;

    @NotBlank(message = "region key cannot be null")
    private String categoryKey;

    private LocalDateTime createdDate;
    private String name;

}
