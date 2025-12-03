package dasturlash.uz.repository;

import dasturlash.uz.entity.EmailHistoryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Repository
public interface EmailHistoryRepository extends CrudRepository<EmailHistoryEntity,Integer> {

    @Query(" from EmailHistoryEntity  where email = ?1 order by createdDate desc limit 1")
    Optional<EmailHistoryEntity> findByUsername(@RequestParam String email);

}
