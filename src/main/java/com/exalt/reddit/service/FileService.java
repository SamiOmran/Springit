package com.exalt.reddit.service;

import com.exalt.reddit.exception.MyFileNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {
    private static final String UPLOAD_DIR = "G:\\Training in companies\\Exalt\\upload\\";
    private static final Logger logger = LoggerFactory.getLogger(FileService.class);

    public Resource loadFileAsResource(String fileName) {
        try {
            Path path = Paths.get(UPLOAD_DIR);
            Resource resource = new UrlResource(path.toUri());

            if (resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File now Found" + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found" + fileName, ex);
        }

    }
}
