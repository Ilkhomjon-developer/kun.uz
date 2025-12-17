package dasturlash.uz.entity.article;

import dasturlash.uz.base.BaseLanguageEntity;
import dasturlash.uz.base.BaseLongEntity;
import dasturlash.uz.entity.AttachEntity;
import dasturlash.uz.entity.CategoryEntity;
import dasturlash.uz.entity.RegionEntity;
import dasturlash.uz.entity.profile.ProfileEntity;
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
public class ArticleEntity extends BaseLanguageEntity {

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "content")
    private String content;

    @Column(name = "shared_count")
    private Integer sharedCount =0;

    @Column(name = "image_id")
    private String imageId;

    @Column(name = "region_id")
    private Long regionId;

    @Column(name = "moderator_id")
    private Long moderatorId;

    @Column(name = "publisher_id")
    private Long publisherId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id", insertable = false, updatable = false)
    private AttachEntity image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", insertable = false, updatable = false)
    private RegionEntity region;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id", insertable = false, updatable = false)
    private ProfileEntity publisher;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moderator_id", insertable = false, updatable = false)
    private ProfileEntity moderator;


    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ArticleStatus status;

    @Column(name = "tag_name")
    private String tagName;

    @Column(name = "read_time")
    private Integer readTime;

    @Column(name = "visible")
    private Boolean visible = Boolean.TRUE;

    @Column(name = "view_count")
    private Long viewCount = 0L;


    @Column(name = "published_date")
    private LocalDateTime publishedDate;


}
