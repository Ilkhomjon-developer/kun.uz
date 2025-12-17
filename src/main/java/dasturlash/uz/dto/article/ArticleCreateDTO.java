package dasturlash.uz.dto.article;

import dasturlash.uz.dto.CategoryDTO;
import dasturlash.uz.dto.SectionDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ArticleCreateDTO {

   private String  title;
   private String description;
   private String content;
   private String imageId;
   private Long regionId;
   private Integer readTime;
   private Long moderatorId;

   private List<CategoryDTO> categoryList;
   private List<SectionDTO> sectionList;

}
