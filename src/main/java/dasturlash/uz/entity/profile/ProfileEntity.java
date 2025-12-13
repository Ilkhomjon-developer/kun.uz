package dasturlash.uz.entity.profile;

import dasturlash.uz.base.BaseLongEntity;
import dasturlash.uz.enums.ProfileStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "profile")
@Getter
@Setter
public class ProfileEntity extends BaseLongEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ProfileStatus status;  // private ProfileStatus status;

    @Column(name = "photo_id")
    private String photoId; // Will do it later (in attach topic)

    @Column(name = "visible")
    private Boolean visible = Boolean.TRUE;



}
