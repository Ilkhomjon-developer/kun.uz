package dasturlash.uz.repository;

import dasturlash.uz.entity.CategoryEntity;
import dasturlash.uz.mapper.CategoryMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<CategoryEntity,Long>{

    Optional<CategoryEntity> findByIdAndVisibleIsTrue(Long id);

    Optional<CategoryEntity> findByCategoryKey(String categoryKey);

    @Modifying
    @Transactional
    @Query("update CategoryEntity set visible = ?2 where id = ?1 ")
    int updateVisibleById(Long id, boolean visible);

    @Query("from CategoryEntity  where visible = true order by orderNumber")
    Iterable<CategoryEntity> getAllByVisibleIsTrue();


    @Query("SELECT c.id AS id, " +
            "CASE :lang " +
            "   WHEN 'uz' THEN c.nameUz " +
            "   WHEN 'ru' THEN c.nameRu " +
            "   WHEN 'en' THEN c.nameEn " +
            "END AS name, " +
            "c.orderNumber AS orderNumber, " +
            "c.categoryKey AS categoryKey " +
            "FROM CategoryEntity c " +
            "WHERE c.visible = true order by orderNumber asc")
    Iterable<CategoryMapper> findAllByLanguageAndVisibleIsTrue(@Param("lang") String lang);

    List<CategoryEntity> findByIdIn(Collection<Long> id);

    @Query("SELECT c.id AS id, " +
            "CASE :lang " +
            "   WHEN 'UZ' THEN c.nameUz " +
            "   WHEN 'RU' THEN c.nameRu " +
            "   WHEN 'EN' THEN c.nameEn " +
            "END AS name, " +
            "c.categoryKey AS categoryKey " +
            "FROM CategoryEntity c " +
            " inner join ArticleCategoryEntity ace on ace.categoryId = c.id " +
            "WHERE ace.articleId = :articleId and c.visible = true order by c.orderNumber asc")
    List<CategoryMapper> getCategoryListByArticleIdAndLang(@Param("articleId") Long articleId,@Param("lang") String lang);
}
