package dasturlash.uz.service.artilce;

import dasturlash.uz.dto.SectionDTO;
import dasturlash.uz.entity.article.ArticleSectionEntity;
import dasturlash.uz.repository.article.ArticleSectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleSectionService {

    private final ArticleSectionRepository articleSectionRepository;


    public void merge(Long articleId, List<SectionDTO> dtoList) {
        List<Long> newList = dtoList.stream().map(SectionDTO::getId).toList();

        List<Long> oldList = articleSectionRepository.getCategoryIdListByArticleId(articleId);
        newList.stream().filter(n -> !oldList.contains(n)).forEach(pe -> create(articleId, pe)); // create
        oldList.stream().filter(old -> !newList.contains(old)).forEach(pe -> articleSectionRepository.deleteByArticleIdAndSectionId(articleId, pe));
    }

    public void create(Long articleId, Long categoryId) {
        ArticleSectionEntity entity = new ArticleSectionEntity();
        entity.setArticleId(articleId);
        entity.setSectionId(categoryId);
        articleSectionRepository.save(entity);
    }
}
