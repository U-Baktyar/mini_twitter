package twitter.exception;

public class TwitterCommandException extends RuntimeException {
    public TwitterCommandException() {}
    public TwitterCommandException(String message) {
        super(message);
    }

}
