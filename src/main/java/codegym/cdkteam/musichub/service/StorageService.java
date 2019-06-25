package codegym.cdkteam.musichub.service;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;

public interface StorageService {
  Path SONG_LOCATION = Paths.get("files/song");
  Path IMAGE_LOCATION = Paths.get("files/image");
  String uploadFile(MultipartFile file, Path location);
  Resource loadFile(String fileName, Path location);
  static String renameFile(String fileName){
    return FilenameUtils.getBaseName(fileName)+"-"+System.nanoTime()+"."+FilenameUtils.getExtension(fileName);
  }
}
