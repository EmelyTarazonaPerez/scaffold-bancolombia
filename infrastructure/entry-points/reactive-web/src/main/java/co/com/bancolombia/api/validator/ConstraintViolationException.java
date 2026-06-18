package co.com.bancolombia.api.validator;

import lombok.Getter;

import java.util.Set;

@Getter
public class ConstraintViolationException extends ValidationException {
    public ConstraintViolationException(Set<String> violations) {
        super("Validation errors found", violations);
    }
}

