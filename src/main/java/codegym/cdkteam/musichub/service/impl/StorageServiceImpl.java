package codegym.cdkteam.musichub.service.impl;

import codegym.cdkteam.musichub.service.StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
@Service
public class StorageServiceImpl implements StorageService {
  @Value("${application.address}")
  public String applicationAddress;

  @Override
  public String uploadFile(MultipartFile file, Path location) {
    if (file.isEmpty()) {
      throw new RuntimeException("FAIL!");
    }
    try {
      String nameFile = StorageService.renameFile(file.getOriginalFilename());
      Files.copy(file.getInputStream(), location.resolve(nameFile));
      String linkFile =  applicationAddress + "/" + location.toString() + "/" + nameFile;
      return linkFile;
    } catch (Exception e) {
      throw new RuntimeException("FAIL!");
    }
  }

  @Override
  public Resource loadFile(String fileName, Path location) {
    try {
      Path file = location.resolve(fileName);
      Resource resource = new UrlResource(file.toUri());
      if (resource.exists() || resource.isReadable()) {
        return resource;
      } else {
        return null;
      }
    } catch (MalformedURLException e) {
      throw new RuntimeException("FAIL!");
    }
  }
  @Override
  public void init() {
    try {
      Files.createDirectory(Paths.get("files"));
      Files.createDirectory(StorageService.SONG_LOCATION);
      Files.createDirectory(StorageService.IMAGE_LOCATION);
    } catch (IOException e) {
      throw new RuntimeException("Could not initialize storage!");
    }
  }
}
