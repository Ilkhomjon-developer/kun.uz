package dasturlash.uz.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDTO {


    private Long id;

    @Positive(message = "Order number must be positive")
    private Integer orderNumber;

    @NotBlank(message = "nameUz  required")
    private String nameUz;
    @NotBlank(message = "nameRu  required")
    private String nameRu;
    @NotBlank(message = "nameEn  required")
    private String nameEn;

    @NotBlank(message = "Category key required")
    private String categoryKey;

    private LocalDateTime createdDate;
    private String name;

}
