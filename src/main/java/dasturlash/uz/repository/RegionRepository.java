package dasturlash.uz.repository;

import dasturlash.uz.entity.RegionEntity;
import dasturlash.uz.mapper.RegionMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegionRepository extends CrudRepository<RegionEntity, Integer> {


    Optional<RegionEntity> findByRegionKey(String regionKey);

    Optional<RegionEntity> findByIdAndVisibleIsTrue(Integer id);

    @Modifying
    @Transactional
    @Query("update RegionEntity set visible = ?2 where id = ?1 ")
    int updateVisibleById(Integer id, Boolean visible);

    Iterable<RegionEntity> findAllByVisibleIsTrue();

    @Query("SELECT c.id AS id, " +
            "CASE :lang " +
            "   WHEN 'UZ' THEN c.nameUz " +
            "   WHEN 'RU' THEN c.nameRu " +
            "   WHEN 'EN' THEN c.nameEn " +
            "END AS name, " +
            "c.orderNumber AS orderNumber, " +
            "c.regionKey AS regionKey " +
            "FROM RegionEntity c " +
            "WHERE c.visible = true order by orderNumber asc")
    Iterable<RegionMapper> findAllByLanguageAndVisibleIsTrue(@Param("lang") String lang);

}
