package dasturlash.uz.entity.mail;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "email_history")
@Getter
@Setter
public class EmailHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "message")
    private String message;

    @Column(name = "email")
    private String email;

    @Column(name = "verification_code")
    private Integer code;

    @Column(name = "created_date")
    @CreationTimestamp
    private LocalDateTime createdDate;

}
