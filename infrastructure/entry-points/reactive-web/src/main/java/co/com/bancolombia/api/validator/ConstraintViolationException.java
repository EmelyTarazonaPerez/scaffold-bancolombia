package co.com.bancolombia.api.validator;

import lombok.Getter;

import java.util.Set;

/**
 * Excepción lanzada cuando hay violaciones de restricciones en la validación
 */
@Getter
public class ConstraintViolationException extends ValidationException {

    public ConstraintViolationException(String message, Set<String> violations) {
        super(message, violations);
    }

    public ConstraintViolationException(Set<String> violations) {
        super("Validation errors found", violations);
    }
}

