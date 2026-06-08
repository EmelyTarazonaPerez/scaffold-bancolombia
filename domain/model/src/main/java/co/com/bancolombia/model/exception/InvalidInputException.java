package co.com.bancolombia.model.exception;

/**
 * Exception thrown when invalid input is provided
 */
public class InvalidInputException extends DomainException {

    public InvalidInputException(String fieldName, String reason) {
        super("INVALID_INPUT",
              String.format("Invalid value for '%s': %s", fieldName, reason));
    }

    public InvalidInputException(String message) {
        super("INVALID_INPUT", message);
    }
}

