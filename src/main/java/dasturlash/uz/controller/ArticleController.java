package dasturlash.uz.controller;

import dasturlash.uz.dto.ArticleCreateDTO;
import dasturlash.uz.dto.ArticleDTO;
import dasturlash.uz.enums.ArticleStatus;
import dasturlash.uz.service.artilce.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
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

}
