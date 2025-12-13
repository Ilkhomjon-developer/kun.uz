package dasturlash.uz.entity.mail;

import dasturlash.uz.base.BaseLongEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "sms_history")
@Getter
@Setter
public class SmsHistoryEntity extends BaseLongEntity {

    @Column(name = "message")
    private String message;

    @Column(name = "phone")
    private String phone;

    @Column(name = "sms_code")
    private Integer code;

    @Column(name = "attempt_count")
    private Integer attemptCount = 0;

}
