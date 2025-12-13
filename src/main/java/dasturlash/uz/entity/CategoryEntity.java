package dasturlash.uz.entity;

import dasturlash.uz.base.BaseLongEntity;
import dasturlash.uz.entity.article.ArticleEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "category")
@Getter
@Setter
public class CategoryEntity extends BaseLongEntity {

    @Column(name = "order_number")
    private Integer orderNumber;

    @Column(name = "name_uz")
    private String nameUz;

    @Column(name = "name_ru")
    private String nameRu;

    @Column(name = "name_en")
    private String nameEn;

    @Column(name = "category_key")
    private String categoryKey;

    @Column(name = "visible")
    private Boolean visible = Boolean.TRUE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Set<ArticleEntity> articles;

}
