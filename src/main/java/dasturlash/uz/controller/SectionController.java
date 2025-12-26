package dasturlash.uz.controller;

import dasturlash.uz.dto.SectionDTO;
import dasturlash.uz.enums.AppLanguageEnum;
import dasturlash.uz.service.SectionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/section")
@RequiredArgsConstructor
public class SectionController {

    private final SectionService sectionService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<SectionDTO> create(@Valid @RequestBody SectionDTO dto){

        return ResponseEntity.ok(sectionService.create(dto));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<SectionDTO> update(@PathVariable("id") Long id,@Valid @RequestBody SectionDTO dto){
        return ResponseEntity.ok(sectionService.update(id, dto));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id){
        return ResponseEntity.ok(sectionService.delete(id));
    }


    @GetMapping("/getAllByLang")
    public ResponseEntity<List<SectionDTO>> getAll(@RequestHeader(name = "Accept-Language", defaultValue ="uz") AppLanguageEnum lang){
        return ResponseEntity.ok(sectionService.getAllByLang(lang));
    }
}
