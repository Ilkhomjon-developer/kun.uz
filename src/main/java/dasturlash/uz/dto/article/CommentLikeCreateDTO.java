package dasturlash.uz.dto.article;

import dasturlash.uz.enums.LikeEmotion;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentLikeCreateDTO {
    @NotBlank(message = "CommentId required")
    private Long commentId;

    @NotNull(message = "Emotion required")
    private LikeEmotion emotion;
}
