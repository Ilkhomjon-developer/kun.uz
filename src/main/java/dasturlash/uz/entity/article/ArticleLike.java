package dasturlash.uz.entity.article;

import dasturlash.uz.base.BaseLongEntity;
import dasturlash.uz.entity.profile.ProfileEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "article_like")
@Getter
@Setter
public class ArticleLike extends BaseLongEntity {

    @Column(name = "profile_id")
    private Integer profileId;

    @Column(name = "article_id")
    private Integer articleId;

    @ManyToOne
    @JoinColumn(name = "profile", updatable = false, insertable = false)
    private ProfileEntity profile;

    @ManyToOne
    @JoinColumn(name = "article", updatable = false, insertable = false)
    private ArticleEntity article;

}
