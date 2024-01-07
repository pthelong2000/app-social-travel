package app.postservice.controller;

import app.postservice.dto.request.UpdatePostArticleRequest;
import app.postservice.service.PostArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/update_article")
public class UpdatePostArticleController {

    private final PostArticleService service;

    @PutMapping("/update")
    public ResponseEntity<Void> updatePostArticle(@RequestBody UpdatePostArticleRequest request){
        service.updatePostContent(request);
        return ResponseEntity.noContent().build();
    }
}
