package app.commentservice.request;

public record ChildCommentRequest(long parentCommentId, long userId, String comment) {
}
