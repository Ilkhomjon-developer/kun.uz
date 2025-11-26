package dasturlash.uz.controller;

import dasturlash.uz.dto.ProfileDTO;
import dasturlash.uz.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @PostMapping("/create")
    public ResponseEntity<ProfileDTO> create(@Valid @RequestBody ProfileDTO dto){

        return ResponseEntity.ok(profileService.create(dto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProfileDTO> update(@PathVariable("id") Integer id,@Valid @RequestBody ProfileDTO dto){
        return ResponseEntity.ok(profileService.update(id, dto));
    }

    @PutMapping("/updateAny/{id}")
    public ResponseEntity<ProfileDTO> updateAny(@PathVariable("id") Integer id, @Valid @RequestBody ProfileDTO dto){
        return ResponseEntity.ok(profileService.updateAny(id, dto));
    }

    @PostMapping("/pagination")
    public ResponseEntity<PageImpl<ProfileDTO>> pagination(@RequestParam(value = "page")int page,
                                                           @RequestParam(value = "size")int size){
        return ResponseEntity.ok(profileService.pagination(page, size));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id){
        return ResponseEntity.ok(profileService.delete(id));
    }

    @PutMapping("/updatePassword/{id}/{password}")
    public ResponseEntity<Boolean> updatePassword(@PathVariable("id") Integer id, @PathVariable("password") String password){
        return ResponseEntity.ok(profileService.updatePassword(id, password));
    }

}
