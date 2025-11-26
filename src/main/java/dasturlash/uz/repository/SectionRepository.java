package dasturlash.uz.repository;

import dasturlash.uz.entity.SectionEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SectionRepository extends CrudRepository<SectionEntity,Integer>, PagingAndSortingRepository<SectionEntity,Integer> {

    Optional<SectionEntity> findByIdAndVisibleTrue(Integer id);

    Optional<SectionEntity> findBySectionKey(String sectionKey);

    @Modifying
    @Transactional
    @Query("update SectionEntity set visible = ?2 where id = ?1 ")
    int updateVisibleById(Integer id, boolean visible);

    Iterable<SectionEntity> findAllByVisibleIsTrue();

    Page<SectionEntity> findAllByVisibleIsTrue(Pageable pageable);

}

