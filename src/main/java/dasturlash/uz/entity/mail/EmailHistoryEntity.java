package dasturlash.uz.entity.mail;

import dasturlash.uz.base.BaseLongEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "email_history")
@Getter
@Setter
public class EmailHistoryEntity extends BaseLongEntity {

    @Column(name = "message")
    private String message;

    @Column(name = "email")
    private String email;

    @Column(name = "verification_code")
    private Integer code;

}
