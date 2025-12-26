package dasturlash.uz.controller;

import dasturlash.uz.dto.profile.ProfileDTO;
import dasturlash.uz.service.profile.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<ProfileDTO> create(@Valid @RequestBody ProfileDTO dto){

        return ResponseEntity.ok(profileService.create(dto));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<ProfileDTO> update(@PathVariable("id") Long id,@Valid @RequestBody ProfileDTO dto){
        return ResponseEntity.ok(profileService.update(id, dto));
    }

    @PutMapping("/updateAny/{id}")
    public ResponseEntity<ProfileDTO> updateAny(@PathVariable("id") Long id, @Valid @RequestBody ProfileDTO dto){
        return ResponseEntity.ok(profileService.updateAny(id, dto));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/pagination")
    public ResponseEntity<PageImpl<ProfileDTO>> pagination(@RequestParam(value = "page")int page,
                                                           @RequestParam(value = "size")int size){
        return ResponseEntity.ok(profileService.pagination(page, size));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id){
        return ResponseEntity.ok(profileService.delete(id));
    }

    @PutMapping("/updatePassword/{id}/{password}")
    public ResponseEntity<Boolean> updatePassword(@PathVariable("id") Long id, @PathVariable("password") String password){
        return ResponseEntity.ok(profileService.updatePassword(id, password));
    }

}
