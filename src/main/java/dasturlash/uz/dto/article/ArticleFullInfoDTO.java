package dasturlash.uz.dto.article;

import dasturlash.uz.dto.AttachDTO;
import dasturlash.uz.dto.CategoryDTO;
import dasturlash.uz.dto.RegionDTO;
import dasturlash.uz.dto.SectionDTO;
import dasturlash.uz.dto.profile.ProfileDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ArticleFullInfoDTO {

    private Long articleId;
    private String title;
    private String description;
    private String context;
    private Integer sharedCount;
    private Long viewCount;
    private AttachDTO attach;
    private RegionDTO region;
    private CategoryDTO category;
    private SectionDTO section;
    private ProfileDTO publisher;
    private ProfileDTO moderator;
    private LocalDateTime publishedDate;

}
