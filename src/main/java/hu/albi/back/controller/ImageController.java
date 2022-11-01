package hu.albi.back.controller;

import java.util.List;
import java.util.stream.Collectors;

import hu.albi.back.model.FileInfo;
import hu.albi.back.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
public class ImageController {
    @Autowired
    ImageService imageService;

    @PostMapping("/upload")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String filename = imageService.save(file);
            return new ResponseEntity<>(filename, HttpStatus.OK);
        } catch (Exception e) {
            String message = "Nem sikerült feltölteni a fájlt: " + file.getOriginalFilename() + "!";
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    @GetMapping("/files")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<FileInfo>> getListFiles() {
        List<FileInfo> fileInfos = imageService.loadAll();
        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = imageService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping("/files/unused")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Integer> getUnusedFilesCount() {
        Integer count = imageService.getUnusedCount();
        return ResponseEntity.status(HttpStatus.OK).body(count);
    }

    @DeleteMapping("/files/unused")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteUnusedFiles() {
        imageService.deleteUnused();
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }
}