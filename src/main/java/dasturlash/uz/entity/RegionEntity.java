package dasturlash.uz.entity;

import dasturlash.uz.base.BaseLongEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "region")
public class RegionEntity extends BaseLongEntity {

    @Column(name = "order_number")
    private Integer orderNumber;

    @Column(name = "name_uz")
    private String nameUz;

    @Column(name = "name_ru")
    private String nameRu;

    @Column(name = "name_en")
    private String nameEn;

    @Column(name = "region_key")
    private String regionKey;

    @Column(name = "visible")
    private Boolean visible = true;

}
