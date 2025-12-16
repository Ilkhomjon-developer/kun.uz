package dasturlash.uz.entity.article;

import dasturlash.uz.base.BaseLongEntity;
import dasturlash.uz.entity.CategoryEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "article_category")
@Getter
@Setter
public class ArticleCategoryEntity extends BaseLongEntity {

    @Column(name = "category_id")
    private Long categoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private CategoryEntity category;

    @Column(name = "article_id")
    private Long articleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", insertable = false, updatable = false)
    private ArticleEntity article;
}
