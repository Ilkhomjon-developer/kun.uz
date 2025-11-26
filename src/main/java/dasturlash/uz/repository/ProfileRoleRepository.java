package dasturlash.uz.repository;

import dasturlash.uz.entity.ProfileRoleEntity;
import dasturlash.uz.enums.ProfileRole;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRoleRepository extends CrudRepository<ProfileRoleEntity,Integer> {

    @Query("select p.roles from ProfileRoleEntity p where p.profileId = ?1")
    List<ProfileRole> getAllRolesListByProfileId(Integer profileId);

    @Modifying
    @Transactional
    void deleteByProfileId(Integer profileId);
}
