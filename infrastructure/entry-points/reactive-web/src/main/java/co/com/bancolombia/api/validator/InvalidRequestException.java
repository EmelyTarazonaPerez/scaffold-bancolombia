package co.com.bancolombia.api.validator;

/**
 * Excepción lanzada cuando la validación falla de forma general
 */
public class InvalidRequestException extends ValidationException {
    public InvalidRequestException(String message) {
        super(message);
    }

    public InvalidRequestException(String message, Exception cause) {
        super(message);
        initCause(cause);
    }
}

