package codegym.cdkteam.musichub.service.impl;

import codegym.cdkteam.musichub.service.StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;

public class StorageServiceImpl implements StorageService {
  @Value("${application.address}")
  public String applecationAddress;

  @Override
  public String uploadFile(MultipartFile file, Path location) {
    if (file.isEmpty()) {
      throw new RuntimeException("FAIL!");
    }
    try {
      String nameFile = StorageService.renameFile(file.getOriginalFilename());
      Files.copy(file.getInputStream(), location.resolve(nameFile));
      String linkFile =  applecationAddress + "/" + location.toString() + "/" + nameFile;
      return linkFile;
    } catch (Exception e) {
      throw new RuntimeException("FAIL!");
    }
  }

  @Override
  public Resource loadFile(String fileName, Path location) {
    return null;
  }
}
