package dasturlash.uz.service.artilce;

import dasturlash.uz.dto.CategoryDTO;
import dasturlash.uz.dto.SectionDTO;
import dasturlash.uz.entity.article.ArticleCategoryEntity;
import dasturlash.uz.entity.article.ArticleSectionEntity;
import dasturlash.uz.repository.article.ArticleCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleCategoryService {

    private final ArticleCategoryRepository articleCategoryRepository;

    public void merge(Long id,  List<CategoryDTO> categoryList) {

        List<Long> newList = categoryList.stream().map(CategoryDTO::getId).toList();

        List<Long> oldList = articleCategoryRepository.getCategoryIdListByArticleId(id);

        newList.stream().filter(n -> !oldList.contains(n)).forEach(p -> create(id, p));
        oldList.stream().filter(old -> !newList.contains(old)).forEach(p -> articleCategoryRepository.deleteByCategoryIdAndArticleId(id, p));

    }

    private void create(Long articleId, Long categoryId) {
        ArticleCategoryEntity entity = new ArticleCategoryEntity();
        entity.setArticleId(articleId);
        entity.setCategoryId(categoryId);
        articleCategoryRepository.save(entity);
    }
}
