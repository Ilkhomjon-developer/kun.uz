package dasturlash.uz.dto.article;

import dasturlash.uz.enums.LikeEmotion;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleLikeCreateDTO {
    @NotBlank(message = "ArticleId required")
    private Long articleId;

    @NotNull(message = "Emotion required")
    private LikeEmotion emotion;
}
