package dasturlash.uz.repository;

import dasturlash.uz.entity.SectionEntity;
import dasturlash.uz.mapper.SectionMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SectionRepository extends CrudRepository<SectionEntity,Long>, PagingAndSortingRepository<SectionEntity,Long> {

    Optional<SectionEntity> findByIdAndVisibleTrue(Long id);

    Optional<SectionEntity> findBySectionKey(String sectionKey);

    @Modifying
    @Transactional
    @Query("update SectionEntity set visible = ?2 where id = ?1 ")
    int updateVisibleById(Long id, boolean visible);


    @Query("SELECT c.id AS id, " +
            "CASE :lang " +
            "   WHEN 'UZ' THEN c.nameUz " +
            "   WHEN 'RU' THEN c.nameRu " +
            "   WHEN 'EN' THEN c.nameEn " +
            "END AS name, " +
            "c.orderNumber AS orderNumber, " +
            "c.sectionKey AS sectionKey " +
            "FROM SectionEntity c " +
            "WHERE c.visible = true order by orderNumber asc")
    Iterable<SectionMapper> findAllByLanguageAndVisibleIsTrue(@Param("lang") String lang);


    Page<SectionEntity> findAllByVisibleIsTrue(Pageable pageable);

    @Query("SELECT s.id AS id, " +
            "CASE :lang " +
            "   WHEN 'UZ' THEN s.nameUz " +
            "   WHEN 'RU' THEN s.nameRu " +
            "   WHEN 'EN' THEN s.nameEn " +
            "END AS name, " +
            "s.sectionKey AS sectionKey " +
            "FROM SectionEntity s " +
            " inner join ArticleSectionEntity  ase on ase.sectionId = s.id " +
            "WHERE ase.articleId = :articleId and s.visible = true order by s.orderNumber asc")
    List<SectionMapper> getSectionListByArticleIdAndLang(Long id, String name);
}

