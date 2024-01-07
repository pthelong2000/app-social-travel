package com.example.fileservice.controller;

import com.example.fileservice.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/file")
public class FileDownloadController {

    private final FileService fileService;

    @PostMapping("/download")
    private ResponseEntity.BodyBuilder downloadFile(@RequestParam("url") String url, @RequestParam("destination") String destination) {
        fileService.downloadFile(url, destination);
        return ResponseEntity.ok();
    }
}