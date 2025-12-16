package dasturlash.uz.mapper;

import dasturlash.uz.enums.ArticleStatus;

public interface ArticleMapper {

    Long getId();
    String getTitle();
    String getDescription();
    String getContent();
    Integer getReadTime();
    Long getRegionId();
    Long getModeratorId();
    Long getPublisherId();
    ArticleStatus getStatus();
    String getImageId();
    String getCategoryKey();
    String getSectionKey();


}
