package app.postservice.service;

import app.postservice.entity.Post_Article;
import app.postservice.request.PostContentRequest;

import java.io.IOException;

public interface PostContentService {

    void savePostContent(PostContentRequest postContentRequest) throws IOException;
}
