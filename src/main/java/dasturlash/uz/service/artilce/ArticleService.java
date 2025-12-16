package dasturlash.uz.service.artilce;

import dasturlash.uz.dto.ArticleCreateDTO;
import dasturlash.uz.dto.ArticleDTO;
import dasturlash.uz.entity.article.ArticleEntity;
import dasturlash.uz.enums.ArticleStatus;
import dasturlash.uz.exps.AppBadException;
import dasturlash.uz.repository.article.ArticleRepository;
import dasturlash.uz.service.AttachService;
import dasturlash.uz.util.SpringSecurityUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleCategoryService articleCategoryService;
    private final ArticleSectionService articleSectionService;
    private final AttachService attachService;


    public ArticleDTO create(ArticleCreateDTO dto){

        ArticleEntity entity = new ArticleEntity();
        toEntity(dto, entity);

        entity.setStatus(ArticleStatus.NOT_PUBLISHED);
        entity.setModeratorId(SpringSecurityUtil.currentProfileId());
        articleRepository.save(entity);

        articleCategoryService.merge(entity.getId(), dto.getCategoryList());
        articleSectionService.merge(entity.getId(), dto.getSectionList());


        return toDTO(entity);
    }

    public ArticleDTO update(Long articleId, ArticleCreateDTO createDTO) {
        ArticleEntity entity = get(articleId);
        toEntity(createDTO, entity);

        articleRepository.save(entity);

        articleCategoryService.merge(entity.getId(), createDTO.getCategoryList());

        articleSectionService.merge(entity.getId(), createDTO.getSectionList());

        return toDTO(entity);
    }

    public String delete(Long articleId) {

        int rows = articleRepository.delete(articleId);

        if(rows > 0){
            return "Article deleted";
        }else {
            return "Something went wrong";
        }
    }

    public String changeStatus(Long articleId, ArticleStatus status) {

       int rows = articleRepository.changeStatus(articleId,status);

       if(rows > 0){
           return "Article status changed";
       }else {
           return "Something went wrong";
       }
    }

    public List<ArticleDTO> getBySectionId(Long sectionId, int limit) {

        Iterable<ArticleEntity> resultList = articleRepository.findBySectionId(sectionId, limit);
        ArrayList<ArticleDTO> dto = new ArrayList<>();
        resultList.forEach(mapper -> dto.add(toDTO(mapper)));
        return dto;
    }

    public List<ArticleDTO> getLastN(List<Long> articleId) {
       Iterable<ArticleEntity> entityList = articleRepository.getLastN(articleId);

       List<ArticleDTO> dtoList = new ArrayList<>();
       for (ArticleEntity entity : entityList) {
           dtoList.add(toDTO(entity));
       }
       return dtoList;
    }

    public List<ArticleDTO> getLastNArticlesByCategoryId(Long categoryId,int limit){
       Iterable<ArticleEntity> entityList = articleRepository.getLastNByCategoryId(categoryId, limit);

       List<ArticleDTO> dtoList = new ArrayList<>();
       for (ArticleEntity entity : entityList) {
           dtoList.add(toDTO(entity));
       }
       return dtoList;
    }

    public List<ArticleDTO> getLastNByRegionId(Long regionId, int limit){
       List<ArticleEntity> entityList = articleRepository.getLastNByRegionId(regionId, limit);

       List<ArticleDTO> dtoList = new ArrayList<>();
       for (ArticleEntity entity : entityList) {
           dtoList.add(toDTO(entity));
       }
       return dtoList;
    }

    public List<ArticleDTO> getNMostReadArticles(Long articleId){
        List<ArticleEntity> entityList = articleRepository.getNMostReadArticles(articleId);
        List<ArticleDTO> dtoList = new ArrayList<>();
        for (ArticleEntity entity : entityList) {
            dtoList.add(toDTO(entity));
        }
        return dtoList;
    }

    public Long increaseArticleViewCountByArticleId(Long articleId){
      return articleRepository.increaseArticleViewCountByArticleId(articleId);
    }

    public Long increaseArticleSharedCountByArticleId(Long articleId){
        return articleRepository.increaseArticleSharedCountByArticleId(articleId);
    }



    private ArticleDTO toDTO(ArticleEntity entity) {
        ArticleDTO dto = new ArticleDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setContent(entity.getContent());
        dto.setSharedCount(entity.getSharedCount());
        dto.setReadTime(entity.getReadTime());
        dto.setViewCount(entity.getViewCount());
        dto.setStatus(entity.getStatus());
        dto.setImageId(entity.getImageId());
        dto.setRegionId(entity.getRegionId());
        dto.setPublishedDate(entity.getPublishedDate());
        return dto;
    }
    private void toEntity(ArticleCreateDTO dto, ArticleEntity entity) {
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setContent(dto.getContent());
        entity.setImageId(dto.getImageId());
        entity.setReadTime(dto.getReadTime());
        entity.setRegionId(dto.getRegionId());
    }
    private ArticleEntity get(Long id) {
        return articleRepository.findById(id).orElseThrow(() -> new AppBadException("Article not found"));
    }


}
