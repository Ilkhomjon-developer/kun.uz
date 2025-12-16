package dasturlash.uz.repository.article;

import dasturlash.uz.entity.article.ArticleEntity;
import dasturlash.uz.enums.AppLanguageEnum;
import dasturlash.uz.enums.ArticleStatus;
import dasturlash.uz.mapper.ArticleMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends CrudRepository<ArticleEntity, Long> {

    @Transactional
    @Modifying
    @Query("Update  ArticleEntity set visible = false where id =?1")
    int delete(Long articleId);

    @Transactional
    @Modifying
    @Query("Update ArticleEntity set status = ?2 where id =?1")
    int changeStatus(Long articleId, ArticleStatus status);

    @Query("select a from ArticleEntity as a " +
            "inner join ArticleSectionEntity  as ac on ac.articleId = a.id" +
            " where ac.sectionId = ?1 and a.visible = true  and a.status = 'PUBLISHED' " +
            "order by a.createdDate desc limit ?2")
    Iterable<ArticleEntity> findBySectionId(Long sectionId, int limit);

    @Query(value="(select * from article where id in (?1) and visible = true and published = true) union all (select * from article where visisble=true and published = true and id not in (?1) order by created_date desc limit 12) order by created_date desc", nativeQuery=true)
    Iterable<ArticleEntity> getLastN(List<Long> articleId);


    @Query("select a from ArticleEntity as a " +
            "inner join ArticleCategoryEntity  as ac on ac.articleId = a.id" +
            " where ac.categoryId = ?1 and a.visible = true  and a.status = 'PUBLISHED' " +
            "order by a.createdDate desc limit ?2")
    Iterable<ArticleEntity> getLastNByCategoryId(Long categoryId ,int limit);

    @Query("select a from ArticleEntity as a where a.regionId = ?1 and a.visible = true order by a.createdDate desc limit ?2")
    List<ArticleEntity> getLastNByRegionId(Long regionId, int limit);


    @Query("select a from ArticleEntity a where a.id <> ?1 and a.visible = true and a.status = 'PUBLISHED' order by a.viewCount desc ")
    List<ArticleEntity> getNMostReadArticles(Long articleId);

    @Transactional
    @Modifying
    @Query("update ArticleEntity set viewCount = viewCount + 1 where id = ?1")
    Long increaseArticleViewCountByArticleId(Long articleId);

    @Transactional
    @Modifying
    @Query("update ArticleEntity set sharedCount = sharedCount + 1 where id = ?1")
    Long increaseArticleSharedCountByArticleId(Long articleId);
}
