package dasturlash.uz.service;

import dasturlash.uz.dto.ArticleInfoDTO;
import dasturlash.uz.entity.article.ArticleEntity;
import dasturlash.uz.repository.ArticleRepository;
import dasturlash.uz.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    private final CategoryService categoryService;

    private final CategoryRepository categoryRepository;

    public ArticleInfoDTO create(ArticleInfoDTO dto){

        ArticleEntity entity = new ArticleEntity();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setContent(dto.getContent());
        entity.setImageId(entity.getImageId());
        entity.setRegionId(dto.getRegionId());
        entity.setModeratorId(dto.getModeratorId());
        entity.setCategories(categoryService.getCategoryList(dto.getCategoryList()));

return null;
    }



}
