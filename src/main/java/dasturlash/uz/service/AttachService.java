package dasturlash.uz.service;

import dasturlash.uz.dto.AttachDTO;
import dasturlash.uz.entity.AttachEntity;
import dasturlash.uz.repository.AttachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class AttachService {

    @Autowired
    private AttachRepository attachRepository;

    @Value("${attach.dir}")
    private String attachDir;

    public AttachDTO uploadFile(MultipartFile file) {

        try {

            String pathFolder = getYmDString();
            String key = UUID.randomUUID().toString();
            String extension = Objects.requireNonNull(file.getOriginalFilename())
                    .substring(file.getOriginalFilename().lastIndexOf(".") + 1);

            File folder = new File(attachDir + pathFolder);

            if (!folder.exists()) {

                boolean mkdirs = folder.mkdirs();
            }

            byte[] bytes = file.getBytes();

            Path path = Paths.get(attachDir + pathFolder + "/" + key + "." + extension);

            Files.write(path, bytes);

            AttachEntity entity = new AttachEntity();
            entity.setId(key + "." + extension);
            entity.setPath(pathFolder);
            entity.setSize(file.getSize());
            entity.setExtension(extension);
            entity.setOriginalName(file.getOriginalFilename());
            attachRepository.save(entity);

            AttachDTO dto = new AttachDTO();
            dto.setId(entity.getId());
            dto.setOriginName(entity.getOriginalName());


            return dto;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public ResponseEntity<Resource> download(String id) {
        try {
            AttachEntity entity = getEntity(id);
            Path filePath = Paths.get(attachDir + entity.getPath() + "/" + id).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + entity.getOriginalName() + "\"")
                        .body(resource);
            } else {
                throw new RuntimeException("File not found");
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not read file");
        }
    }

    public ResponseEntity<Resource> open(String id) {

        AttachEntity entity = getEntity(id);

        Path filePath = Paths.get(attachDir + entity.getPath() + "/" + id).normalize();
        Resource resource = null;

        try {

            resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {

                throw new RuntimeException("File not found");
            }

            String contentType = Files.probeContentType(filePath);

            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(resource);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    private String getYmDString() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        return year + "/" + month + "/" + day;
    }

    private AttachEntity getEntity(String id) {

        Optional<AttachEntity> optional = attachRepository.findById(id);

        if (optional.isEmpty()) {
            throw new RuntimeException("File not found");
        }
        return optional.get();
    }
}
