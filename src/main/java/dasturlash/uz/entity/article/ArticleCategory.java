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
public class ArticleCategory extends BaseLongEntity {

    @Column(name = "article_id")
    private Integer articleId;

    @Column(name = "category_id")
    private Integer categoryId;

    @ManyToOne
    @JoinColumn(name = "article", updatable = false, insertable = false)
    private ArticleEntity article;

    @ManyToOne
    @JoinColumn(name = "category", updatable = false, insertable = false)
    private CategoryEntity category;
}
