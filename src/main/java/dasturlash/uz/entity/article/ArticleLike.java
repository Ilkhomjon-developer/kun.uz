package dasturlash.uz.entity.article;

import dasturlash.uz.entity.profile.ProfileEntity;
import dasturlash.uz.entity.profile.ProfileRoleEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "article_like")
@Getter
@Setter
public class ArticleLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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


    @Column(name = "created_date")
    @CreationTimestamp
    private LocalDateTime createdDate;
}
