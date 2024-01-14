package app.commentservice.service.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "image-service")
public interface FileService {
    @PostMapping("/upload")
    String uploadFile(@RequestParam("file") MultipartFile file);
}
