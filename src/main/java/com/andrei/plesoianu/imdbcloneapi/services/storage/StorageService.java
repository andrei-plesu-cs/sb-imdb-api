package com.andrei.plesoianu.imdbcloneapi.services.storage;

import com.andrei.plesoianu.imdbcloneapi.enums.StorageType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService {
    String store(StorageType type, MultipartFile file) throws IOException;
}
