package dasturlash.uz.repository.mail;

import dasturlash.uz.entity.mail.TokenEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends CrudRepository<TokenEntity, Integer> {

    Optional<TokenEntity> findTopByOrderByCreateDateDesc();
}
