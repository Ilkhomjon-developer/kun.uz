package dasturlash.uz.entity.mail;

import dasturlash.uz.base.BaseLongEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "token")
@Getter
@Setter
public class TokenEntity extends BaseLongEntity {
    @Column(name = "token", length = 500)
    private String token;

}
