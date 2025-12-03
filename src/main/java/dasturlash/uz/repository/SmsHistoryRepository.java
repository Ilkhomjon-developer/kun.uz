package dasturlash.uz.repository;

import dasturlash.uz.entity.SmsHistoryEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SmsHistoryRepository extends CrudRepository<SmsHistoryEntity,String> {

    @Query(" from SmsHistoryEntity  where phone = ?1 order by createdDate desc limit 1")
    Optional<SmsHistoryEntity> findByPhone(String username);

    @Transactional
    @Modifying
    @Query("update SmsHistoryEntity set attemptCount = attemptCount + 1 where id = ?1")
    void increaseAttempt(String id);

    List<SmsHistoryEntity> findByCreatedDateBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);

    Page<SmsHistoryEntity> findAll(Pageable pageable);


    List<SmsHistoryEntity> findAllByPhone(String phone);
}
