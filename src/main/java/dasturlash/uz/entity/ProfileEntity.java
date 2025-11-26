package dasturlash.uz.entity;

import dasturlash.uz.enums.GeneralStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

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
    private GeneralStatus status;


    @Column(name = "visible")
    private Boolean visible = Boolean.TRUE;


    @Column(name = "created_date")
    @CreationTimestamp
    private LocalDateTime createdDate;

}
