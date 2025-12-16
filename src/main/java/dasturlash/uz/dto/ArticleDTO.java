package dasturlash.uz.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import dasturlash.uz.dto.AttachDTO;
import dasturlash.uz.dto.CategoryDTO;
import dasturlash.uz.dto.SectionDTO;
import dasturlash.uz.enums.ArticleStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleDTO {
    private Long id;
    private String title;
    private String description;
    private String content;
    private Integer sharedCount;

    private String imageId;
    private AttachDTO image;
    private Long regionId;

    private Long moderatorId;
    private Long publisherId;

    private ArticleStatus status;

    private Integer readTime; // in second
    private Long viewCount; // in second
    private LocalDateTime publishedDate;
    private List<CategoryDTO> categoryList;
    private List<SectionDTO> sectionList;
}
