package co.com.bancolombia.api.validator;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class RequestValidator {

    private final Validator validator;

    public <T> Mono<T> validate(T request) {
        if (request == null) {
            System.out.println("Request is null");
            return Mono.error(new NullRequestException());
        }

        Set<ConstraintViolation<T>> violations = validator.validate(request);

        if (!violations.isEmpty()) {
            Set<String> violationMessages = violations.stream()
                    .map(this::formatViolation)
                    .collect(Collectors.toSet());

            return Mono.error(new ConstraintViolationException(violationMessages));
        }
        System.out.println("Request is valid: " + request);
        return Mono.just(request);
    }

    private String formatViolation(ConstraintViolation<?> violation) {
        return violation.getPropertyPath() + ": " + violation.getMessage();
    }
}
