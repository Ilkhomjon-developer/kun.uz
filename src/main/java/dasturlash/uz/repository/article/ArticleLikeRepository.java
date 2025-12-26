package dasturlash.uz.repository.article;

import dasturlash.uz.entity.article.ArticleLikeEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ArticleLikeRepository extends CrudRepository<ArticleLikeEntity, String> {

    Optional<ArticleLikeEntity> findByArticleIdAndProfileIdAndVisibleTrue(Long articleId, Long profileId);

    @Transactional
    @Modifying
    @Query("update ArticleLikeEntity  set visible = false, deletedDate = current_timestamp where articleId =?1 and profileId =?2 and visible = true")
    int deleteByArticleIdAndProfileId(Long articleId, Long profileId);
}