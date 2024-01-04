package app.commentservice.entity;

import java.time.LocalDateTime;

public class ParentComment {
    private long id;
    private long postId;
    private long userId;
    private String content;
    private boolean isFavorite;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
