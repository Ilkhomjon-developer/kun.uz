package dasturlash.uz.repository.article;

import dasturlash.uz.entity.article.ArticleCategoryEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArticleCategoryRepository extends CrudRepository<ArticleCategoryEntity, Long> {

    @Query("select categoryId from ArticleCategoryEntity where articleId =?1")
    List<Long> getCategoryIdListByArticleId(Long articleId);

    @Modifying
    @Transactional
    @Query("delete  from  ArticleCategoryEntity where articleId =?1 and categoryId =?2")
    void deleteByCategoryIdAndArticleId(Long articleId, Long categoryId);
}
