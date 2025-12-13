package dasturlash.uz.entity.article;

import dasturlash.uz.base.BaseLongEntity;
import dasturlash.uz.entity.CategoryEntity;
import dasturlash.uz.enums.ArticleStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "article")
@Getter
@Setter
public class ArticleEntity extends BaseLongEntity {

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "content")
    private String content;

    @Column(name = "shared_count")
    private Integer sharedCount;

    @Column(name = "image_id")
    private String imageId;

    @Column(name = "region_id")
    private Integer regionId;

    @Column(name = "moderator_id")
    private Integer moderatorId;

    @Column(name = "publisher_id")
    private Integer publisherId;


    @OneToMany(mappedBy = "articles", fetch = FetchType.LAZY)
    private List<CategoryEntity> categories;


    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ArticleStatus status;

    @Column(name = "read_time")
    private String readTime;

    @Column(name = "visible")
    private Boolean visible = Boolean.TRUE;

    @Column(name = "view_count")
    private Long viewCount = 0L;


    @Column(name = "published_date")
    private LocalDateTime publishedDate;


}
