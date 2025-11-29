package dasturlash.uz.repository;

import dasturlash.uz.entity.EmailHistoryEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface EmailHistoryRepository extends CrudRepository<EmailHistoryEntity,Integer> {

    Optional<EmailHistoryEntity> findByProfileId(Integer id);

    @Modifying
    @Transactional
    void deleteByProfileId(Integer profileId);
}
