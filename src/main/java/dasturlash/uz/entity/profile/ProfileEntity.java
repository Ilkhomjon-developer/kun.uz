package dasturlash.uz.entity.profile;

import dasturlash.uz.enums.ProfileStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "profile")
@Getter
@Setter
public class ProfileEntity {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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


    @Column(name = "created_date")
    @CreationTimestamp
    private LocalDateTime createdDate;

}
