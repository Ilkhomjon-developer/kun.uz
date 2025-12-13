package dasturlash.uz.controller;

import dasturlash.uz.dto.CategoryDTO;
import dasturlash.uz.enums.AppLanguageEnum;
import dasturlash.uz.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<CategoryDTO> create(@Valid @RequestBody CategoryDTO dto){
        return ResponseEntity.ok(categoryService.create(dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryDTO> update(@PathVariable("id") Long id,@Valid @RequestBody CategoryDTO dto){
        return ResponseEntity.ok(categoryService.update(id, dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id){

        return ResponseEntity.ok(categoryService.delete(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("getAll")
    public ResponseEntity<List<CategoryDTO>> getAll(){
        return ResponseEntity.ok(categoryService.getAll());
    }

    @GetMapping("/getAllByLang")
    public ResponseEntity<List<CategoryDTO>> getAllByLang(@RequestHeader(name = "Accept-Language", defaultValue = "uz") AppLanguageEnum lang){
        return ResponseEntity.ok(categoryService.getAllByLang(lang));
    }
}
