package co.com.bancolombia.api.validator;


public class InvalidRequestException extends ValidationException {
    public InvalidRequestException(String message) {
        super(message);
    }

    public InvalidRequestException(String message, Exception cause) {
        super(message);
        initCause(cause);
    }
}

