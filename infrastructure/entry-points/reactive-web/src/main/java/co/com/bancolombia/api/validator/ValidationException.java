package co.com.bancolombia.api.validator;

import lombok.Getter;

import java.util.Set;

@Getter
public class ValidationException extends RuntimeException {
    private final Set<String> violations;

    public ValidationException(String message, Set<String> violations) {
        super(message);
        this.violations = violations;
    }

    public ValidationException(String message) {
        super(message);
        this.violations = Set.of();
    }
}

