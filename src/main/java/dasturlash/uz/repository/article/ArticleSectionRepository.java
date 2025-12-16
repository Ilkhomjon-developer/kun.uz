package dasturlash.uz.repository.article;

import dasturlash.uz.entity.article.ArticleSectionEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArticleSectionRepository extends CrudRepository <ArticleSectionEntity, Long>{

    @Query("select sectionId from ArticleSectionEntity where articleId =?1")
    List<Long> getCategoryIdListByArticleId(Long articleId);

    @Modifying
    @Transactional
    @Query("delete  from  ArticleSectionEntity where articleId =?1 and sectionId =?2")
    void deleteByArticleIdAndSectionId(Long articleId, Long section);
}
