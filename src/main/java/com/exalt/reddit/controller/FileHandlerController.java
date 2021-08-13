package com.exalt.reddit.controller;

import com.exalt.reddit.dto.ResponseResult;
import com.exalt.reddit.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Controller
public class FileHandlerController {

    private static final String UPLOAD_DIR = "G:\\Training in companies\\Exalt\\upload\\";
    private static final Logger logger = LoggerFactory.getLogger(FileHandlerController.class);
    private static final int FAIL_STATUS = -1;
    private static final int SUCCESS_STATUS = 1;

    private final FileService fileService;

    public FileHandlerController(FileService fileService) {
        this.fileService = fileService;
    }

    // get the page
    @GetMapping(path = "/index")
    public String hello() {
        return "file_handling";
    }

    // upload the file
    @PostMapping(path = "/index/upload", produces = {"application/json"})
    @ResponseBody
    public ResponseResult uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        ResponseResult responseResult = new ResponseResult();

        try {
            file.transferTo(new File(UPLOAD_DIR + fileName));
        } catch (Exception e) {
            responseResult.setMessage(e.getMessage());
            responseResult.setStatus(FAIL_STATUS);
            return responseResult;
        }

        responseResult.setMessage("The file has been uploaded successfully.");
        responseResult.setStatus(SUCCESS_STATUS);

        return responseResult;
    }

    // download files
    @GetMapping(path = "/index/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam String fileName) {
        File file = new File(UPLOAD_DIR + fileName);

        if(!file.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Resource resource = fileService.loadFileAsResource(file.getAbsolutePath());
        String contentType = null;

        try {
            contentType = Files.probeContentType(Paths.get(file.getPath()));
        } catch (IOException ex) {
            logger.error("EX:: ", ex);
            logger.info("Could not determine file type.");
        }
        contentType = (contentType == null) ? "application/octet-stream" : contentType;

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.setContentType(MediaType.parseMediaType(contentType));

        logger.info("Success downloading " + fileName);
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .body(resource);
    }

}