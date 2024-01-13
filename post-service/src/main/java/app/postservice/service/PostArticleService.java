package app.postservice.service;

import app.postservice.dto.request.CreatePostArticleRequest;
import app.postservice.dto.request.UpdatePostArticleRequest;
import app.postservice.entity.PostArticle;

public interface PostArticleService {

    PostArticle findPostArticleById(Long id);

    void savePostContent(CreatePostArticleRequest CreatePostArticleRequest);

    void updatePostContent(UpdatePostArticleRequest request);
}
