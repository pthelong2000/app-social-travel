package app.chatsservice.exception;

public class ChatServiceCustomException extends RuntimeException {

    public ChatServiceCustomException(String message) {
        super(message);
    }
}
