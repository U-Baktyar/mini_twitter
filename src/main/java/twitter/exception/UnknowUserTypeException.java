package twitter.exception;

public class UnknowUserTypeException extends Exception {
    public UnknowUserTypeException() {}
    public UnknowUserTypeException(String message) {
        super(message);
    }
}
