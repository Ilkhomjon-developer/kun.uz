package dasturlash.uz.dto.article;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ArticleFilterDTO {

    private String title;
    private Long regionId;
    private Long categoryId;
    private Long sectionId;
    private LocalDateTime createdDateFrom;
    private LocalDateTime createdDateTo;
}
