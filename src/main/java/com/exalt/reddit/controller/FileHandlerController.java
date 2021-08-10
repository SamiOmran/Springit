package com.exalt.reddit.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import static java.nio.file.Paths.get;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

@Controller
public class FileHandlerController {

    private final Logger logger = LoggerFactory.getLogger(FileHandlerController.class);

    // get the page
    @GetMapping(path = "/index")
    public String hello() {
        return "file_handling";
    }

    // upload the file
    @PostMapping(path = "/index/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();

        try {
            file.transferTo(new File("G:\\Training in companies\\Exalt\\upload\\" + fileName));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok("Success uploading file");
    }



}