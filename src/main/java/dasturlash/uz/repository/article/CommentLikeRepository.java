package dasturlash.uz.repository.article;

import dasturlash.uz.entity.article.CommentLikeEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface CommentLikeRepository extends CrudRepository<CommentLikeEntity, String> {

    Optional<CommentLikeEntity> findByCommentIdAndProfileIdAndVisibleTrue(Long articleId, Long profileId);

    @Transactional
    @Modifying
    @Query("update CommentLikeEntity  set visible = false, deletedDate = current_timestamp where commentId =?1 and profileId =?2 and visible = true")
    int deleteByCommentIdAndProfileId(String commentId, Long profileId);
}