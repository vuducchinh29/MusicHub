package codegym.cdkteam.musichub.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface StorageService {
  String uploadFile(MultipartFile file, Path location);
  Resource loadFile(String fileName, Path location);
}
