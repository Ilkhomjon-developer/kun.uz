package dasturlash.uz.service.artilce;

import dasturlash.uz.dto.article.*;
import dasturlash.uz.dto.profile.ProfileDTO;
import dasturlash.uz.entity.article.ArticleEntity;
import dasturlash.uz.enums.AppLanguageEnum;
import dasturlash.uz.enums.ArticleStatus;
import dasturlash.uz.exps.AppBadException;
import dasturlash.uz.mapper.ArticleShortInfoMapper;
import dasturlash.uz.repository.article.ArticleCustomRepository;
import dasturlash.uz.repository.article.ArticleRepository;
import dasturlash.uz.service.AttachService;
import dasturlash.uz.service.CategoryService;
import dasturlash.uz.service.RegionService;
import dasturlash.uz.service.SectionService;
import dasturlash.uz.util.SpringSecurityUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleCategoryService articleCategoryService;
    private final ArticleSectionService articleSectionService;
    private final ArticleCustomRepository articleCustomRepository;
    private final RegionService regionService;
    private final AttachService attachService;
    private final CategoryService categoryService;
    private final SectionService sectionService;


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
       Iterable<ArticleShortInfoMapper> entityList = articleRepository.getLastN(articleId);

       List<ArticleDTO> dtoList = new ArrayList<>();
       entityList.forEach(mapper -> dtoList.add(toDTO(mapper)));

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

    public ArticleDTO getByIdAndLang(Long id, AppLanguageEnum lang){

        ArticleEntity entity = get(id);

        ArticleDTO dto = new ArticleDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setContent(entity.getContent());
        dto.setSharedCount(entity.getSharedCount());
        dto.setReadTime(entity.getReadTime());
        dto.setViewCount(entity.getViewCount());
        dto.setPublishedDate(entity.getPublishedDate());

        dto.setRegion(regionService.getByIdAndLang(entity.getRegionId(),lang));
        dto.setSectionList(sectionService.getSectionListByArticleIdAndLang(entity.getId(), lang));
        dto.setCategoryList(categoryService.getCategoryListByArticleIdAndLang(entity.getId(), lang));

        return dto;
    }

    public List<ArticleDTO> getLastNArticle(Integer limit) {
        List<ArticleShortInfoMapper> resultList = articleRepository.getLastNArticle(limit); // 1
        List<ArticleDTO> responseList = new ArrayList<>();

        resultList.forEach(mapper -> {

            ArticleDTO articleDTO = toDTO(mapper);

            ProfileDTO moderator = new ProfileDTO();
            moderator.setId(mapper.getModeratorId());
            moderator.setName(mapper.getModeratorName());
            moderator.setSurname(mapper.getModeratorSurname());
            articleDTO.setModerator(moderator);

            responseList.add(articleDTO);
        });

        return responseList;
    }


    public List<ArticleDTO> getNArticlesByTagName(String tagName, int limit){

        List<ArticleEntity> articleEntities = articleRepository.getNArticlesByTagName(tagName, limit);
        List<ArticleDTO> dtoList = new ArrayList<>();

        articleEntities.forEach(mapper -> dtoList.add(toDTO(mapper)));
        return dtoList;
    }


    public Page<ArticleDTO> filter(ArticleFilterDTO filter, int page, int size, Boolean isModerator) { // 1, 100
        FilterResultDTO<Object[]> filterResult = articleCustomRepository.filter(filter, page, size, isModerator);
        List<ArticleDTO> articleList = new LinkedList<>();
        for (Object[] obj : filterResult.getContent()) {
            ArticleDTO article = new ArticleDTO();

            article.setId((Long) obj[0]);
            article.setTitle((String) obj[1]);
            article.setDescription((String) obj[2]);
            article.setPublishedDate((LocalDateTime) obj[3]);
            if (obj[4] != null) {
                article.setImage(attachService.openDTO((String) obj[4]));
            }
            articleList.add(article);
        }
        return new PageImpl<>(articleList, PageRequest.of(page, size), filterResult.getTotal());
    }


    public Page<ArticleDTO> filterAsAdmin(ArticleAdminFilterDTO filter, int page, int size) { // 1, 100
        FilterResultDTO<Object[]> filterResult = articleCustomRepository.filterAsAdmin(filter, page, size);
        List<ArticleDTO> articleList = new LinkedList<>();
        for (Object[] obj : filterResult.getContent()) {
            ArticleDTO article = new ArticleDTO();

            article.setId((Long) obj[0]);
            article.setTitle((String) obj[1]);
            article.setDescription((String) obj[2]);
            article.setPublishedDate((LocalDateTime) obj[3]);
            article.setPublishedDate((LocalDateTime) obj[4]);
            if (obj[5] != null) {
                article.setImage(attachService.openDTO((String) obj[5]));
            }
            if (obj[6] != null) {
                article.setStatus((ArticleStatus) obj[6]);
            }
            articleList.add(article);
        }

        return new PageImpl<>(articleList, PageRequest.of(page, size), filterResult.getTotal());
    }


    public Page<ArticleFilterDTO> search(ArticleFilterDTO dto, int page, int size){
        Pageable pageRequest = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<ArticleEntity> pageResult = articleRepository.findByTitle(dto.getTitle(), pageRequest);

        List<ArticleFilterDTO> filterDto = pageResult.getContent().stream()
                .map(entity -> {
                    ArticleFilterDTO articleFilterDTO = new ArticleFilterDTO();
                    articleFilterDTO.setTitle(entity.getTitle());
                    articleFilterDTO.setRegionId(entity.getRegionId());
                    articleFilterDTO.setCreatedDateFrom(entity.getPublishedDate());
                    return articleFilterDTO;
                }).collect(Collectors.toList());

        return new PageImpl<>(filterDto, pageRequest, pageResult.getTotalElements());
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
    private ArticleDTO toDTO(ArticleShortInfoMapper entity) {
        ArticleDTO dto = new ArticleDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setImageId(entity.getImageId());
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
