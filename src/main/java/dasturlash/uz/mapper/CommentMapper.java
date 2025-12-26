package dasturlash.uz.mapper;

import java.time.LocalDateTime;

public interface CommentMapper {
    Long getId();

    String getContent();

    LocalDateTime getCreatedDate();

    LocalDateTime getUpdatedDate();

    Long getProfileId();

    String getProfileName();

    String getProfileSurname();

    String getProfilePhotoId();

    Long getLikeCount();

    Long getDislikeCount();

}
