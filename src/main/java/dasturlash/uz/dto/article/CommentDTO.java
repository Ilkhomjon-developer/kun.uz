package dasturlash.uz.dto.article;

import com.fasterxml.jackson.annotation.JsonInclude;
import dasturlash.uz.dto.article.ArticleDTO;
import dasturlash.uz.dto.profile.ProfileDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDTO {
    private Long id;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private ProfileDTO profile;
    private ArticleDTO article;
    private CommentDTO reply;
    private Long likeCount;
    private Long disLikeCount;
}