package dasturlash.uz.repository;

import dasturlash.uz.entity.TokenEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends CrudRepository<TokenEntity, Integer> {

    Optional<TokenEntity> findTopByOrderByCreateDateDesc();
}
