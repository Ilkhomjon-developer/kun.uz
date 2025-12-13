package dasturlash.uz.entity.profile;

import dasturlash.uz.base.BaseLongEntity;
import dasturlash.uz.enums.ProfileRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "profile_role")
@Getter
@Setter
public class ProfileRoleEntity extends BaseLongEntity{

    @Column(name = "profile_id")
    private Long profileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private ProfileEntity profile;

    @Enumerated(EnumType.STRING)
    @Column(name = "roles")
    private ProfileRole roles;

}
