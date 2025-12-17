package dasturlash.uz.mapper;

import java.time.LocalDateTime;

public interface ArticleShortInfoMapper {
    Long getId();

    String getTitle();

    String getDescription();

    String getImageId();

    LocalDateTime getPublishedDate();

    Long getModeratorId();

    String getModeratorName();

    String getModeratorSurname();

}
