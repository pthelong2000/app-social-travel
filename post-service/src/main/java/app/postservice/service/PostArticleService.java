package app.postservice.service;

import app.postservice.dto.request.CreatePostArticleRequest;
import app.postservice.dto.request.UpdatePostArticleRequest;

public interface PostArticleService {

    void savePostContent(CreatePostArticleRequest CreatePostArticleRequest);

    void updatePostContent(UpdatePostArticleRequest request);
}
