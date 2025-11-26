package dasturlash.uz.entity;

import dasturlash.uz.enums.ProfileRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "profile_role")
@Getter
@Setter
public class ProfileRoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(name = "profile_id")
    private Integer profileId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private ProfileEntity profile;

    @Enumerated(EnumType.STRING)
    @Column(name = "roles")
    private ProfileRole roles;

    @Column(name = "created_date")
    @CreationTimestamp
    private LocalDateTime createdDate;
}
