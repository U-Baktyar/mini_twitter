package twitter.exception;

public class UnknowCommandException extends Exception {

    public UnknowCommandException(){}

    public UnknowCommandException(String message) {
        super(message);
    }
}
