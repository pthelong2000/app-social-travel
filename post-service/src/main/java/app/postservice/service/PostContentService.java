package app.postservice.service;

import app.postservice.dto.request.PostContentRequest;

public interface PostContentService {

    void savePostContent(PostContentRequest postContentRequest);
}
