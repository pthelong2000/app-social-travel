package app.postservice.controller;

import app.postservice.request.PostContentRequest;
import app.postservice.service.PostContentService;
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

    private final PostContentService service;

    @PostMapping("/create")
    public ResponseEntity<Void> createPostArticle(@RequestBody PostContentRequest request){
        service.savePostContent(request);
        return ResponseEntity.ok().build();
    }
}
