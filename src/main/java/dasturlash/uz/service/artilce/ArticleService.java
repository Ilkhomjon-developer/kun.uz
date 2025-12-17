package dasturlash.uz.service.artilce;

import dasturlash.uz.dto.article.ArticleCreateDTO;
import dasturlash.uz.dto.article.ArticleDTO;
import dasturlash.uz.dto.article.ArticleFilterDTO;
import dasturlash.uz.dto.profile.ProfileDTO;
import dasturlash.uz.entity.article.ArticleEntity;
import dasturlash.uz.enums.AppLanguageEnum;
import dasturlash.uz.enums.ArticleStatus;
import dasturlash.uz.exps.AppBadException;
import dasturlash.uz.mapper.ArticleShortInfoMapper;
import dasturlash.uz.repository.article.ArticleRepository;
import dasturlash.uz.service.CategoryService;
import dasturlash.uz.service.RegionService;
import dasturlash.uz.service.SectionService;
import dasturlash.uz.util.SpringSecurityUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleCategoryService articleCategoryService;
    private final ArticleSectionService articleSectionService;
    private final RegionService regionService;
    private final EntityManager entityManager;

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

        dto.setRegion(regionService.getByIdAndLang(entity.getId(),lang));
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

    public Page<ArticleFilterDTO> filter(ArticleFilterDTO filter, int page, int size){

        StringBuilder selectQueryBuilder = new StringBuilder("SELECT s FROM ArticleEntity s ");
        StringBuilder countQueryBuilder = new StringBuilder("SELECT count(s) FROM ArticleEntity s ");
        StringBuilder builder = new StringBuilder(" where s.status = 'PUBLISHED' and s.visible = true ");

        Map<String, Object> params = new HashMap<>();

        if (filter.getTitle() != null) { // condition by id
            builder.append(" and s.title=:title ");
            params.put("title", filter.getTitle());
        }
        if (filter.getRegionId() != null) { // condition by name
           builder.append(" and s.regionId=:regionId ");
           params.put("regionId", filter.getRegionId());

        }
        if (filter.getSectionId() != null) { // condition by surname with like
            builder.append(" and s.sectionId=:sectionId ");
            params.put("sectionId", filter.getSectionId());
        }
        if(filter.getCategoryId() != null){
            builder.append(" and s.categoryId=:categoryId ");
            params.put("categoryId", filter.getCategoryId());
        }
        if(filter.getPublishedDateFrom() != null && filter.getPublishedDateTo() != null){

            builder.append(" and s.publishedDate between :publishedDateFrom and :publishedDateTo ");
            params.put("publishedDateFrom", filter.getPublishedDateFrom());
            params.put("publishedDateTo", filter.getPublishedDateTo());
        }
        if (filter.getPublishedDateFrom() != null){
            builder.append(" and s.publishedDate >= :publishedDateFrom ");
            params.put("publishedDateFrom", filter.getPublishedDateFrom());
        }
        if (filter.getPublishedDateTo() != null){
            builder.append(" and s.publishedDate <= :publishedDateTo ");
            params.put("publishedDateTo", filter.getPublishedDateTo());
        }

        Query selectQuery = entityManager.createQuery(selectQueryBuilder.toString());
        selectQuery.setFirstResult((page) * size);
        selectQuery.setMaxResults(size);
        params.forEach(selectQuery::setParameter);

        List<ArticleFilterDTO> profileEntityList = selectQuery.getResultList();

        Query countQuery = entityManager.createQuery(countQueryBuilder.toString());
        params.forEach(countQuery::setParameter);

        Long totalElements = (Long) countQuery.getSingleResult();

        return new PageImpl<>(profileEntityList, PageRequest.of(page, size), totalElements);
    }

    public Page<ArticleFilterDTO> filterForModerator(ArticleFilterDTO filter, int page, int size){

        StringBuilder selectQueryBuilder = new StringBuilder("SELECT s FROM ArticleEntity s ");
        StringBuilder countQueryBuilder = new StringBuilder("SELECT count(s) FROM ArticleEntity s ");
        StringBuilder builder = new StringBuilder(" where s.visible = true ");

        Map<String, Object> params = new HashMap<>();
        Long currentModeratorId = SpringSecurityUtil.currentProfileId();

        if (currentModeratorId == null){
            throw new AppBadException("You are not logged in");
        }

        builder.append(" and s.moderatorId=:currentModeratorId ");
        params.put("currentModeratorId", currentModeratorId);


        if (filter.getTitle() != null) { // condition by id
            builder.append(" and s.title=:title ");
            params.put("title", filter.getTitle());
        }
        if (filter.getRegionId() != null) { // condition by name
           builder.append(" and s.regionId=:regionId ");
           params.put("regionId", filter.getRegionId());

        }
        if (filter.getSectionId() != null) { // condition by surname with like
            builder.append(" and s.sectionId=:sectionId ");
            params.put("sectionId", filter.getSectionId());
        }
        if(filter.getCategoryId() != null){
            builder.append(" and s.categoryId=:categoryId ");
            params.put("categoryId", filter.getCategoryId());
        }
        if(filter.getPublishedDateFrom() != null && filter.getPublishedDateTo() != null){

            builder.append(" and s.publishedDate between :publishedDateFrom and :publishedDateTo ");
            params.put("publishedDateFrom", filter.getPublishedDateFrom());
            params.put("publishedDateTo", filter.getPublishedDateTo());
        }
        if (filter.getPublishedDateFrom() != null){
            builder.append(" and s.publishedDate >= :publishedDateFrom ");
            params.put("publishedDateFrom", filter.getPublishedDateFrom());
        }
        if (filter.getPublishedDateTo() != null){
            builder.append(" and s.publishedDate <= :publishedDateTo ");
            params.put("publishedDateTo", filter.getPublishedDateTo());
        }

        selectQueryBuilder.append(builder);
        countQueryBuilder.append(builder);

        Query selectQuery = entityManager.createQuery(selectQueryBuilder.toString());
        selectQuery.setFirstResult((page) * size);
        selectQuery.setMaxResults(size);
        params.forEach(selectQuery::setParameter);

        List<ArticleFilterDTO> profileEntityList = selectQuery.getResultList();

        Query countQuery = entityManager.createQuery(countQueryBuilder.toString());
        params.forEach(countQuery::setParameter);

        Long totalElements = (Long) countQuery.getSingleResult();

        return new PageImpl<>(profileEntityList, PageRequest.of(page, size), totalElements);
    }

    public Page<ArticleFilterDTO> filterForPublisher(ArticleFilterDTO filter, int page, int size){

        StringBuilder selectQueryBuilder = new StringBuilder("SELECT s FROM ArticleEntity s ");
        StringBuilder countQueryBuilder = new StringBuilder("SELECT count(s) FROM ArticleEntity s ");
        StringBuilder builder = new StringBuilder(" where s.visible = true ");

        Map<String, Object> params = new HashMap<>();
        Long publisherId = SpringSecurityUtil.currentProfileId();

        if (publisherId == null){
            throw new AppBadException("You are not logged in");
        }

        builder.append(" and s.publisherId=:publisherId ");
        params.put("publisherId", publisherId);


        if (filter.getTitle() != null) { // condition by id
            builder.append(" and s.title=:title ");
            params.put("title", filter.getTitle());
        }
        if (filter.getRegionId() != null) { // condition by name
           builder.append(" and s.regionId=:regionId ");
           params.put("regionId", filter.getRegionId());

        }
        if (filter.getSectionId() != null) { // condition by surname with like
            builder.append(" and s.sectionId=:sectionId ");
            params.put("sectionId", filter.getSectionId());
        }
        if(filter.getCategoryId() != null){
            builder.append(" and s.categoryId=:categoryId ");
            params.put("categoryId", filter.getCategoryId());
        }
        if(filter.getPublishedDateFrom() != null && filter.getPublishedDateTo() != null){

            builder.append(" and s.publishedDate between :publishedDateFrom and :publishedDateTo ");
            params.put("publishedDateFrom", filter.getPublishedDateFrom());
            params.put("publishedDateTo", filter.getPublishedDateTo());
        }
        if (filter.getPublishedDateFrom() != null){
            builder.append(" and s.publishedDate >= :publishedDateFrom ");
            params.put("publishedDateFrom", filter.getPublishedDateFrom());
        }
        if (filter.getPublishedDateTo() != null){
            builder.append(" and s.publishedDate <= :publishedDateTo ");
            params.put("publishedDateTo", filter.getPublishedDateTo());
        }

        selectQueryBuilder.append(builder);
        countQueryBuilder.append(builder);

        Query selectQuery = entityManager.createQuery(selectQueryBuilder.toString());
        selectQuery.setFirstResult((page) * size);
        selectQuery.setMaxResults(size);
        params.forEach(selectQuery::setParameter);

        List<ArticleFilterDTO> profileEntityList = selectQuery.getResultList();

        Query countQuery = entityManager.createQuery(countQueryBuilder.toString());
        params.forEach(countQuery::setParameter);

        Long totalElements = (Long) countQuery.getSingleResult();

        return new PageImpl<>(profileEntityList, PageRequest.of(page, size), totalElements);
    }

    public Page<ArticleShortInfoMapper> search(String title, int page, int size){
        Pageable pageRequest = PageRequest.of(page, size, Sort.by("createdDate").descending());
        return articleRepository.findByTitle(title, pageRequest);
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
