package codegym.cdkteam.musichub.controller.REST;

import codegym.cdkteam.musichub.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public class UploadRESTController {
  @Autowired
  private StorageService storageService;

  @PostMapping(value = "/upload-song", consumes = {"multipart/form-data"})
  public ResponseEntity uploadSong(@RequestParam MultipartFile file) {
    try {
      String link = storageService.uploadFile(file, StorageService.SONG_LOCATION);
      return ResponseEntity.status(HttpStatus.OK).body(link);
    } catch (Exception e) {
      String message = "FAIL to upload " + file.getOriginalFilename();
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
    }
  }

  @PostMapping(value = "/upload-image", consumes = {"multipart/form-data"})
  public ResponseEntity uploadImage(@RequestParam MultipartFile file) {
    try {
      String link = storageService.uploadFile(file, StorageService.IMAGE_LOCATION);
      return ResponseEntity.status(HttpStatus.OK).body(link);
    } catch (Exception e) {
      String message = "FAIL to upload " + file.getOriginalFilename();
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
    }
  }

  @GetMapping("/files/song/{filename:.+}")
  public ResponseEntity<Resource> getSong(@PathVariable String filename) {
    Resource file = storageService.loadFile(filename, StorageService.SONG_LOCATION);
    return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
            .body(file);
  }

  @GetMapping("/files/image/{filename:.+}")
  public ResponseEntity<Resource> getImage(@PathVariable String filename) {
    Resource file = storageService.loadFile(filename, StorageService.IMAGE_LOCATION);
    return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
            .body(file);
  }
}
