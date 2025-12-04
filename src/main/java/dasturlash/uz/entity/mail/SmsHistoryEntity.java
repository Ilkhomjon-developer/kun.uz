package dasturlash.uz.entity.mail;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "sms_history")
@Getter
@Setter
public class SmsHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "message")
    private String message;

    @Column(name = "phone")
    private String phone;

    @Column(name = "sms_code")
    private Integer code;

    @Column(name = "attempt_count")
    private Integer attemptCount = 0;


    @Column(name = "created_date")
    @CreationTimestamp
    private LocalDateTime createdDate;

}
