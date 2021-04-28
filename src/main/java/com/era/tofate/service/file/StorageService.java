package com.era.tofate.service.file;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    String store(MultipartFile file);

    boolean delete(String path);

}
