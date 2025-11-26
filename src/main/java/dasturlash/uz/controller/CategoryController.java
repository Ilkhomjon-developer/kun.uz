package dasturlash.uz.controller;

import dasturlash.uz.dto.CategoryDTO;
import dasturlash.uz.enums.AppLanguageEnum;
import dasturlash.uz.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<CategoryDTO> create(@Valid @RequestBody CategoryDTO dto){
        return ResponseEntity.ok(categoryService.create(dto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryDTO> update(@PathVariable("id") Integer id,@Valid CategoryDTO dto){
        return ResponseEntity.ok(categoryService.update(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id){

        return ResponseEntity.ok(categoryService.delete(id));
    }

    @GetMapping("getAll")
    public ResponseEntity<List<CategoryDTO>> getAll(){
        return ResponseEntity.ok(categoryService.getAll());
    }

    @GetMapping("/getAllByLang")
    public ResponseEntity<List<CategoryDTO>> getAllByLang(@RequestHeader(name = "Accept-Language", defaultValue = "uz") AppLanguageEnum lang){
        return ResponseEntity.ok(categoryService.getAllByLang(lang));
    }
}
