package dasturlash.uz.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ArticleInfoDTO {

   private String  title;
   private String description;
   private String content;
   private Integer imageId;
   private Integer regionId;
   private Integer moderatorId;
   private List<Long> categoryList;
   private List<Long> sectionList;

}
