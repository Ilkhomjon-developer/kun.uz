package dasturlash.uz.entity.article;

import dasturlash.uz.base.BaseLongEntity;
import dasturlash.uz.entity.SectionEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "article_section")
@Getter
@Setter
public class ArticleSection extends BaseLongEntity {

    @Column(name = "article_id")
    private Integer articleId;

    @Column(name = "section_id")
    private Integer sectionId;

    @ManyToOne
    @JoinColumn(name = "article", updatable = false, insertable = false)
    private ArticleEntity article;

    @ManyToOne
    @JoinColumn(name = "section", updatable = false, insertable = false)
    private SectionEntity section;
}
