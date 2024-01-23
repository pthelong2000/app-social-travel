package app.commentservice.exception;

public class CommentServiceCustomException extends RuntimeException {
    public CommentServiceCustomException(String message){
        super(message);
    }
}
