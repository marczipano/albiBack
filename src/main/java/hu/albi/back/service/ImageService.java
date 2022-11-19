package hu.albi.back.service;

import hu.albi.back.model.FileInfo;
import hu.albi.back.repo.ImageRepository;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {
    private final Path root = Paths.get("uploads");

    private static ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        ImageService.imageRepository = imageRepository;
    }


    public void init() {
        if (Files.notExists(root)) {
            try {
                Files.createDirectory(root);
            } catch (IOException e) {
                throw new RuntimeException("Could not initialize folder for upload!");
            }
        }
    }


    public String save(MultipartFile file) {
        try {
            String filename = file.getOriginalFilename();
            String extension = "";
            int i = filename.lastIndexOf('.');
            if (i > 0) {
                extension = filename.substring(i + 1);
            }
            String newname = UUID.randomUUID().toString() + "." + extension;
            Files.copy(file.getInputStream(), this.root.resolve(newname));
            return newname;
        } catch (Exception e) {
            throw new RuntimeException("Sikertelen mentés. Hiba: " + e.getMessage());
        }
    }


    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }


    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }


    public List<FileInfo> loadAll() {
        return imageRepository.findAll();
    }

    public Integer getUnusedCount() {
        return imageRepository.getUnusedCount();
    }

    public void deleteUnused() {
        // delete files that are in repository but not connected to sublets
        List<FileInfo> files = imageRepository.getUnusedFiles();
        for (FileInfo fi : files
        ) {
            File fileToDelete = new File(String.valueOf(this.root.resolve(fi.getName())));
            if (fileToDelete.delete()) {
                System.out.println("Deleted the file from file system: " + fileToDelete.getName());
            } else {
                System.out.println("File not found in file system: " + fileToDelete.getName());
            }
            imageRepository.deleteById(fi.getId());
        }

        // delete files that are in the filesystem but not used
        File folder = new File(String.valueOf(this.root));
        File[] listOfFiles = folder.listFiles();

        assert listOfFiles != null;
        for (File file : listOfFiles) {
            if (file.isFile()) {
                List<FileInfo> present = imageRepository.findByName(file.getName());
                if (present.size() < 1) {
                    if (file.delete()) {
                        System.out.println("Deleted the file from file system: " + file.getName());
                    } else {
                        System.out.println("File not found in file system: " + file.getName());
                    }
                }

            }
        }

    }

}
