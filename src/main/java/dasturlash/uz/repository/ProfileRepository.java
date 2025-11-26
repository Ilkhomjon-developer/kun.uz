package dasturlash.uz.repository;

import dasturlash.uz.entity.ProfileEntity;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends CrudRepository<ProfileEntity,Integer>, PagingAndSortingRepository<ProfileEntity,Integer> {


    Optional<ProfileEntity> findByIdAndVisibleIsTrue( Integer id);

    @Modifying
    @Transactional
    @Query("update ProfileEntity set visible = ?2 where id = ?1 ")
    int updateVisibleById(Integer id, Boolean visible);

    Optional<ProfileEntity> findByUsernameAndVisibleIsTrue(String username);

    Page<ProfileEntity> findAllByVisibleIsTrue(Pageable pageable);
}
