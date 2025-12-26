package dasturlash.uz.service.artilce;

import dasturlash.uz.dto.article.ArticleLikeCreateDTO;
import dasturlash.uz.entity.article.ArticleLikeEntity;
import dasturlash.uz.repository.article.ArticleLikeRepository;
import dasturlash.uz.repository.article.ArticleRepository;
import dasturlash.uz.util.SpringSecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleLikeService {

    private final ArticleLikeRepository articleLikeRepository;


    public void create(ArticleLikeCreateDTO dto) {
        Long currentProfileId = SpringSecurityUtil.currentProfileId();
        Optional<ArticleLikeEntity> optional = articleLikeRepository.findByArticleIdAndProfileIdAndVisibleTrue(dto.getArticleId(), currentProfileId);
        if (optional.isPresent()) { // if exists update
            ArticleLikeEntity entity = optional.get();
            entity.setEmotion(dto.getEmotion());
            articleLikeRepository.save(entity);
            // update like and dislike count (or use trigger)
        } else { // not exists
            ArticleLikeEntity entity = new ArticleLikeEntity();
            entity.setArticleId(dto.getArticleId());
            entity.setProfileId(currentProfileId);
            entity.setCreatedDate(LocalDateTime.now());
            entity.setEmotion(dto.getEmotion());
            entity.setVisible(true);
            // create
            articleLikeRepository.save(entity);
            // 2. increase article like count.
            //  articleRepository.incrementLikeCountAndGetLastLikeCount(dto.getArticleId());
            //  ArticleLike -> Insert/Update/Delete  ->  (Trigger)  -> function
        }
    }

    public boolean remove(Long articleId) {
        Long currentProfileId = SpringSecurityUtil.currentProfileId();
        int effectedResult = articleLikeRepository.deleteByArticleIdAndProfileId(articleId, currentProfileId);
        // detect emotion type and deacrease like or dislike count (or use trigger)
        return effectedResult != 0;
    }

}
