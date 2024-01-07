package com.example.fileservice.controller;

import com.example.fileservice.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/file")
public class FileUploadController {

    private final FileService fileService;

    @PostMapping("/upload")
    private String uploadFile(@RequestParam("file") MultipartFile file) {

        return fileService.uploadFile(file);
    }
}
