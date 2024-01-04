package app.commentservice.request;

public record ParentCommentRequest(long post_id, long user_id, String content) {
}
