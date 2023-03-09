package com.garage.acedetails.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import com.garage.acedetails.constants.ApplicationConstants;
import com.garage.acedetails.entity.Image;
import com.garage.acedetails.repository.ImageRepository;

import com.garage.acedetails.service.ImageService;


@Service
public class ImageServiceImpl implements ImageService {
  private final static Path root = Paths.get(ApplicationConstants.IMAGE_DIRECTORY);

  @Autowired
  private ImageRepository imageRepository;

  @Override
  public void init() {
    try {
      Files.createDirectory(root);
    } catch (IOException e) {
      // TODO throw exception
      e.printStackTrace();
    }
  }

  @Override
  public String saveToDirectory(MultipartFile file) {
    String fileName = "";
    fileName = UUID.randomUUID().toString() + StringUtils.cleanPath(file.getOriginalFilename());
    System.out.println(fileName);
    // Files.copy(file.getInputStream(), this.root.resolve(fileName),
    // StandardCopyOption.REPLACE_EXISTING);
    SaveImageThread thread = new SaveImageThread(file, fileName);
    thread.start();
    return System.getProperty("user.dir") + "\\" + ApplicationConstants.IMAGE_DIRECTORY + "\\" + fileName;
  }

  @Override
  public Resource load(String fileName) {
    try {
      Path file = root.resolve(fileName);
      Resource resource = new UrlResource(file.toUri());
      if (resource.exists() || resource.isReadable()) {
        return resource;
      } else {
        throw new RuntimeException("Could not read the file");
      }
    } catch (MalformedURLException e) {
      throw new RuntimeException("Error: " + e.getMessage());
    }
  }

  @Override
  public Stream<Path> loadAll() {
    try {
      return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
    } catch (IOException e) {
      throw new RuntimeException("Could not load the files!");
    }
  }

  @Override
  public Image saveToDatabase(Image image) {
    return imageRepository.save(image);
  }

  public static class SaveImageThread extends Thread {
    private String fileName;
    private MultipartFile file;

    public SaveImageThread(MultipartFile file, String fileName) {
      this.file = file;
      this.fileName = fileName;
    }

    @Override
    public void run() {
      super.run();
      try {
        Files.copy(file.getInputStream(), root.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
