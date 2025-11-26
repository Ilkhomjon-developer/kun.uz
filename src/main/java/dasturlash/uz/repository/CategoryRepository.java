package dasturlash.uz.repository;

import dasturlash.uz.entity.CategoryEntity;
import dasturlash.uz.mapper.CategoryMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<CategoryEntity,Integer>{

    Optional<CategoryEntity> findByIdAndVisibleIsTrue(Integer id);

    Optional<CategoryEntity> findByCategoryKey(String categoryKey);

    @Modifying
    @Transactional
    @Query("update CategoryEntity set visible = ?2 where id = ?1 ")
    int updateVisibleById(Integer id, boolean visible);

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


}
