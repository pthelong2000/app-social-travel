package com.example.fileservice.service.Ipml;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.fileservice.exception.custom.HandlerFileCustomException;
import com.example.fileservice.service.FileService;
import com.example.fileservice.utils.Constants;
import com.example.fileservice.utils.message.MessageUtils;
import com.example.fileservice.utils.method.RequestValidator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceIpm implements FileService {

    private final Cloudinary cloudinary;
    private final RequestValidator requestValidator;

    @Override
    public String uploadFile(MultipartFile fileUpload) {
        try {
            validateUploadFile(fileUpload);
        } catch (HandlerFileCustomException e) {
            log.info(e.getMessage());
        }
        String fileUrl = "";
        try {
            String fileExtension = getFileExtension(Objects.requireNonNull(fileUpload.getOriginalFilename()));
            FileType fileType = determineFileType(fileExtension);

            if (fileType != null) {
                Map uploadOptions = ObjectUtils.asMap(
                        "resource_type", getFileResourceType(fileType)
                );

                Map uploadResult = cloudinary.uploader().upload(fileUpload.getBytes(), uploadOptions);
                fileUrl = (String) uploadResult.get("secure_url");
            }
        } catch (IOException e) {
            log.info(Constants.UPLOAD_FILE_FAILED);
        }
        return fileUrl;
    }

    @Override
    public void downloadFile(String fileUrl, String destinationPath) {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fileUrl))
                .build();

        try {
            HttpResponse<InputStream> response = httpClient.send(request, HttpResponse.BodyHandlers.ofInputStream());
            InputStream in = response.body();

            Path destination = Path.of(destinationPath);
            Files.copy(in, destination, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException | InterruptedException e) {
            log.info(MessageUtils.getMessage(Constants.DOWNLOAD_FILE_FAILED));
        }

    }

    private void validateUploadFile(MultipartFile fileUpload) {
        Map<String, List<String>> errors = requestValidator.validateRequest(fileUpload);

        String fileExtension = getFileExtension(Objects.requireNonNull(fileUpload.getOriginalFilename()));
        long fileSize = fileUpload.getSize();

        FileType fileType = determineFileType(fileExtension);
        if (Objects.isNull(fileType)) {
            errors.put("file", List.of(MessageUtils.getMessage(Constants.UPLOAD_FILE_FAILED)));
            return;
        }

        if (fileSize > fileType.getMaxSize()) {
            switch (fileType) {
                case IMAGE -> errors.put("image", List.of(MessageUtils.getMessage(Constants.IMAGE_SIZE_INVALID)));
                case VIDEO -> errors.put("video", List.of(MessageUtils.getMessage(Constants.VIDEO_SIZE_INVALID)));
                default -> errors.put("document", List.of(MessageUtils.getMessage(Constants.DOCUMENT_SIZE_INVALID)));
            }
        }
        if (!errors.isEmpty()) {
            throw new HandlerFileCustomException("FILE_UPLOAD_FAILED", errors.toString());
        }
    }

    private String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf(".");
        if (dotIndex > 0 && dotIndex < filename.length() - 1) {
            return filename.substring(dotIndex + 1).toLowerCase();
        }
        return "";
    }

    private FileType determineFileType(String fileExtension) {
        List<String> imageExtensions = Arrays.asList("jpg", "jpeg", "png", "gif", "bmp", "svg", "webp", "tiff");
        List<String> videoExtensions = Arrays.asList("mp4", "mov", "avi", "flv", "wmv", "webm");

        if (imageExtensions.contains(fileExtension)) {
            return FileType.IMAGE;
        } else if (videoExtensions.contains(fileExtension)) {
            return FileType.VIDEO;
        } else {
            return FileType.DOCUMENT;
        }
    }

    private String getFileResourceType(FileType fileType) {
        return switch (fileType) {
            case IMAGE -> "image";
            case VIDEO -> "video";
            default -> "auto";
        };
    }

    @Getter
    public enum FileType {
        DOCUMENT(512 * 1024), // 512KB
        IMAGE(512 * 1024), // 512KB
        VIDEO(512 * 1024); // 512 KB

        private final long maxSize;

        FileType(long maxSize) {
            this.maxSize = maxSize;
        }
    }
}