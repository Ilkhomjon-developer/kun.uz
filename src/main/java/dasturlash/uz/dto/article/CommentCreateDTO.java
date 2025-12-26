package dasturlash.uz.dto.article;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentCreateDTO {
    @NotBlank(message = "Content required")
    private String content;
    @NotBlank(message = "ArticleId required")
    private Long articleId;
    private String replyId;
}