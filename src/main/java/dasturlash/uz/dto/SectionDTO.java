package dasturlash.uz.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SectionDTO {

    private Integer id;


    @Positive(message = "Order number must be positive")
    private Integer orderNumber;

    @NotEmpty(message = "nameUz  required")
    private String nameUz;
    @NotEmpty(message = "nameRu  required")
    private String nameRu;
    @NotBlank(message = "nameEn  required")
    private String nameEn;

    @NotBlank(message = "section key cannot be null")
    private String sectionKey;

    private LocalDateTime createdDate;
    private String name;
}
