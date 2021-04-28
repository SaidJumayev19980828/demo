package com.era.tofate.service.file;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class StorageServiceImpl implements StorageService{
    @Value( "${file-folder.url}" )
    private final String fileFolderUrl;

    public StorageServiceImpl(@Value( "${file-folder.url}" ) String fileFolderUrl) {
        this.fileFolderUrl = fileFolderUrl;
    }

    @Override
    public String store(MultipartFile file) {
        if (file.isEmpty()) {
            return null;
        }

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(fileFolderUrl + file.getOriginalFilename());
            if (Files.exists(path)){
                path = Paths.get(fileFolderUrl + UUID.randomUUID().toString().substring(0,10) + "-" + file.getOriginalFilename());
            }
            Files.write(path, bytes);
            return file.getOriginalFilename();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(String path) {
        Path pathExisting = Paths.get(path);
        if (Files.exists(pathExisting)) {
            try {
                Files.delete(pathExisting);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
