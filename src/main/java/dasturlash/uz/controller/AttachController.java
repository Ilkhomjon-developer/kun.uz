package dasturlash.uz.controller;

import dasturlash.uz.dto.AttachDTO;
import dasturlash.uz.service.AttachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/attach")
public class AttachController {

    @Autowired
    private AttachService attachService;

    @PostMapping(value="/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AttachDTO> upload(@RequestParam("file") MultipartFile file){
        return ResponseEntity.ok(attachService.uploadFile(file));
    }

    @GetMapping("/open/{id}")
    public ResponseEntity<Resource> open(@PathVariable("id") String id){
        return attachService.open(id);
    }

}
