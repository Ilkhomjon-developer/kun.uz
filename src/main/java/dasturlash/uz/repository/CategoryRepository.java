package dasturlash.uz.repository;

import dasturlash.uz.entity.CategoryEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
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

}
