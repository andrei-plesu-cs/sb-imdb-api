package com.andrei.plesoianu.imdbcloneapi.services.storage;

import com.andrei.plesoianu.imdbcloneapi.enums.StorageType;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class StorageServiceImpl implements StorageService {
    @Value("${app.storage.basefilename}")
    private String baseFilePath;

    @Override
    public String store(StorageType type, MultipartFile file) throws IOException {
        var extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String fileId = UUID.randomUUID().toString();
        String fileName = fileId + "." + extension;

        // Save the file
        Path basePath = Path.of(baseFilePath).resolve(type.toFileName());
        Files.createDirectories(basePath);
        Files.copy(file.getInputStream(), basePath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

        return fileName;
    }
}
