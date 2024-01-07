package com.example.fileservice.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String uploadFile(MultipartFile file);

    void downloadFile(String url, String destination);
}
