package app.postservice.controller;

import app.postservice.dto.request.CreatePostArticleRequest;
import app.postservice.service.PostArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post_article")
public class PostArticleController {

    private final PostArticleService service;

    @PostMapping("/create")
    public ResponseEntity<Void> createPostArticle(@RequestBody CreatePostArticleRequest request){
        service.savePostContent(request);
        return ResponseEntity.ok().build();
    }

}
