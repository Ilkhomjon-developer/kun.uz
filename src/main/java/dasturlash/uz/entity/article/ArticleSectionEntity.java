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
public class ArticleSectionEntity extends BaseLongEntity {

    @Column(name = "section_id")
    private Long sectionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id", insertable = false, updatable = false)
    private SectionEntity section;



    @Column(name = "article_id")
    private Long articleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", insertable = false, updatable = false)
    private ArticleEntity article;
}
