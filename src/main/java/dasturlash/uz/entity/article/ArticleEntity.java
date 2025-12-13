package dasturlash.uz.entity.article;

import dasturlash.uz.enums.ArticleStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "article")
@Getter
@Setter
public class ArticleEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

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


    @Column(name = "created_date")
    @CreationTimestamp
    private LocalDateTime createdDate;

}
