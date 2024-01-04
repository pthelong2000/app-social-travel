package app.commentservice.entity;

import java.time.LocalDateTime;

public class ChildComment {
    private long id;
    private long parentCommentId;
    private long userId;
    private String content;
    private boolean isFavorite;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
