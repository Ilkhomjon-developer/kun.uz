package dasturlash.uz.repository.profile;

import dasturlash.uz.entity.profile.ProfileEntity;
import dasturlash.uz.enums.ProfileStatus;
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
public interface ProfileRepository extends CrudRepository<ProfileEntity,Long>, PagingAndSortingRepository<ProfileEntity,Long> {


    Optional<ProfileEntity> findByIdAndVisibleIsTrue( Long id);

    @Modifying
    @Transactional
    @Query("update ProfileEntity set visible = ?2 where id = ?1 ")
    int updateVisibleById(Long id, Boolean visible);

    Optional<ProfileEntity> findByUsernameAndVisibleIsTrue(String username);

    Page<ProfileEntity> findAllByVisibleIsTrue(Pageable pageable);

    @Modifying
    @Transactional
    @Query("update ProfileEntity set status =?2  where username = ?1")
    void updateStatus(String username, ProfileStatus profileStatus);
}
