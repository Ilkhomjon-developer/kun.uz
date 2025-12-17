package dasturlash.uz.dto.article;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleFilterDTO {

    private String title;
    private Long regionId;
    private Long categoryId;
    private Long sectionId;
    private String publishedDateFrom;
    private String publishedDateTo;
}
