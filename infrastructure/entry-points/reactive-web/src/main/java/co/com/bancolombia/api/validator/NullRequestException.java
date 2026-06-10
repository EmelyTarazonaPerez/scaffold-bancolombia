package co.com.bancolombia.api.validator;


public class NullRequestException extends ValidationException {
    public NullRequestException(String message) {
        super(message);
    }

    public NullRequestException() {
        super("The request cannot be null");
    }
}

