package dasturlash.uz.entity.article;

import dasturlash.uz.entity.SectionEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "article_section")
@Getter
@Setter
public class ArticleSection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "article_id")
    private Integer articleId;

    @Column(name = "section_id")
    private Integer sectionId;

    @ManyToOne
    @JoinColumn(name = "article_id", updatable = false, insertable = false)
    private ArticleEntity article;

    @ManyToOne
    @JoinColumn(name = "section_id", updatable = false, insertable = false)
    private SectionEntity section;
}
