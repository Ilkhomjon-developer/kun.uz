package dasturlash.uz.controller;

import dasturlash.uz.dto.article.ArticleCreateDTO;
import dasturlash.uz.dto.article.ArticleDTO;
import dasturlash.uz.dto.article.ArticleFilterDTO;
import dasturlash.uz.enums.AppLanguageEnum;
import dasturlash.uz.enums.ArticleStatus;
import dasturlash.uz.mapper.ArticleShortInfoMapper;
import dasturlash.uz.service.artilce.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @PreAuthorize("hasRole('MODERATOR')")
    @PostMapping("/create")
    public ResponseEntity<ArticleDTO> create(@RequestBody ArticleCreateDTO dto){
        return ResponseEntity.ok(articleService.create(dto));
    }

    @PreAuthorize("hasRole('MODERATOR')")
    @PutMapping("/update/{articleId}")
    public ResponseEntity<ArticleDTO> update(@PathVariable Long articleId, @RequestBody ArticleCreateDTO dto){
        return ResponseEntity.ok(articleService.update(articleId, dto));
    }

    @PreAuthorize("hasRole('MODERATOR')")
    @DeleteMapping("/delete/{articleId}")
    public ResponseEntity<String> delete(@PathVariable Long articleId){
      return   ResponseEntity.ok(articleService.delete(articleId));
    }

    @PreAuthorize("hasRole('PUBLISHER')")
    @PutMapping("/change-Status/{articleId}")
    public ResponseEntity<String> changeStatus(@PathVariable("articleId") Long articleId, @RequestBody ArticleStatus status){
        return ResponseEntity.ok(articleService.changeStatus(articleId, status));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/getBySectionId/{sectionId}/{limit}")
    public ResponseEntity<List<ArticleDTO>> getBySectionId(@PathVariable Long sectionId, @PathVariable int limit){
        return ResponseEntity.ok(articleService.getBySectionId(sectionId, limit));
    }


    @GetMapping("/getLastN")
    public ResponseEntity<List<ArticleDTO>> getLastN(@RequestBody List<Long> articleIds){
        return ResponseEntity.ok(articleService.getLastN(articleIds));
    }

    @GetMapping("/getLastNArticlesByCategoryId/{categoryId}/{limit}")
    public ResponseEntity<List<ArticleDTO>> getLastNArticlesByCategoryId(@PathVariable Long categoryId, @PathVariable int limit){
        return ResponseEntity.ok(articleService.getLastNArticlesByCategoryId(categoryId, limit));
    }

    @GetMapping("/getLastNByRegionId/{regionId}/{limit}")
    public ResponseEntity<List<ArticleDTO>> getLastNByRegionId(@PathVariable  Long regionId,@PathVariable int limit){
        return ResponseEntity.ok(articleService.getLastNByRegionId(regionId, limit));
    }

    @GetMapping("/getNMostReadArticles/{articleId}")
    public ResponseEntity<List<ArticleDTO>> getNMostReadArticles(@PathVariable Long articleId){
        return ResponseEntity.ok(articleService.getNMostReadArticles(articleId));
    }

    @PutMapping("/increaseArticleViewCountByArticleId/{articleId}")
    public ResponseEntity<Long> increaseArticleViewCountByArticleId(@PathVariable Long articleId){
        return ResponseEntity.ok(articleService.increaseArticleViewCountByArticleId(articleId));
    }

    @PutMapping("/increaseArticleSharedCountByArticleId/{articleId}")
    public ResponseEntity<Long> increaseArticleSharedCountByArticleId(@PathVariable Long articleId){
        return ResponseEntity.ok(articleService.increaseArticleSharedCountByArticleId(articleId));
    }

    @GetMapping("/get-by-lang/{id}")
    public ResponseEntity<ArticleDTO> getByIdAndLang(@PathVariable Long id, @RequestHeader(name = "Accept-Language", defaultValue ="uz") AppLanguageEnum lang){
        return ResponseEntity.ok(articleService.getByIdAndLang(id, lang));
    }

    @GetMapping("/get-last-n-article")
    public ResponseEntity<List<ArticleDTO>> getLastNArticle(@RequestParam("limit") Integer limit){
        return ResponseEntity.ok(articleService.getLastNArticle(limit));
    }

    @GetMapping("/get-by-tag-name")
    public ResponseEntity<List<ArticleDTO>> getNArticlesByTagName(@RequestParam("tagName") String tagName, @RequestParam("limit") Integer limit){
        return ResponseEntity.ok(articleService.getNArticlesByTagName(tagName, limit));
    }

    @PostMapping("/filter")
    public ResponseEntity<Page<ArticleFilterDTO>> filter(@RequestBody ArticleFilterDTO filter, @RequestParam("page") int page, @RequestParam("size") int size){
        return ResponseEntity.ok(articleService.filter(filter, page, size));
    }

    @PostMapping("/filterForModerator")
    public ResponseEntity<Page<ArticleFilterDTO>> filterForModerator(@RequestBody ArticleFilterDTO filter, @RequestParam("page") int page, @RequestParam("size") int size) {
        return ResponseEntity.ok(articleService.filterForModerator(filter, page, size));
    }
    @PostMapping("/filterForPublisher")
    public ResponseEntity<Page<ArticleFilterDTO>> filterForPublisher(@RequestBody ArticleFilterDTO filter, @RequestParam("page") int page, @RequestParam("size") int size){
        return ResponseEntity.ok(articleService.filterForPublisher(filter, page, size));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ArticleShortInfoMapper>> search(@RequestParam("title") String title, @RequestParam("page") int page, @RequestParam("size") int size){
        return ResponseEntity.ok(articleService.search(title, page, size));
    }
}
