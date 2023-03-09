package com.garage.acedetails.service;

import java.nio.file.Path;
import java.util.stream.Stream;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import com.garage.acedetails.entity.Image;

public interface ImageService {
  public void init();
  public String saveToDirectory(MultipartFile file);
  public Resource load(String fileName);
  public Stream<Path> loadAll();
  public Image saveToDatabase(Image image);
}
