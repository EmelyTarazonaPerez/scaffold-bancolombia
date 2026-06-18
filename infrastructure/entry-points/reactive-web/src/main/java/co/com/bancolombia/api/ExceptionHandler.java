package co.com.bancolombia.api;

import co.com.bancolombia.api.validator.ConstraintViolationException;
import co.com.bancolombia.api.validator.NullRequestException;
import co.com.bancolombia.api.validator.ValidationException;
import co.com.bancolombia.model.exception.BusinessRuleException;
import co.com.bancolombia.model.exception.DomainException;
import co.com.bancolombia.model.exception.InvalidInputException;
import co.com.bancolombia.model.exception.ResourceNotFoundException;
import co.com.bancolombia.model.exception.ServiceUnavailableException;
import co.com.bancolombia.api.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class ExceptionHandler {

    public Mono<ServerResponse> handleException(Throwable throwable) {
        log.error("Exception occurred", throwable);

        return switch (throwable) {
            case ConstraintViolationException constraintViolation -> handleConstraintViolation(constraintViolation);
            case NullRequestException nullRequest -> handleNullRequest(nullRequest);
            case ValidationException validation -> handleValidationException(validation);
            case ServiceUnavailableException serviceUnavailable -> handleServiceUnavailable(serviceUnavailable);
            case ResourceNotFoundException notFound -> handleResourceNotFound(notFound);
            case InvalidInputException invalid -> handleInvalidInput(invalid);
            case BusinessRuleException business -> handleBusinessRule(business);
            case IllegalArgumentException illegal -> handleIllegalArgument(illegal);
            case DomainException domain -> handleDomainException(domain);
            default -> handleUnexpectedError(throwable);
        };
    }

    private Mono<ServerResponse> handleConstraintViolation(ConstraintViolationException ex) {
        log.warn("Constraint violation detected: {}", ex.getViolations());
        ErrorResponse error = new ErrorResponse(
                Constants.ERROR_CODE_VALIDATION_ERROR,
                Constants.VALIDATION_ERRORS_MESSAGE,
                HttpStatus.BAD_REQUEST.value(),
                ex.getViolations()
        );
        return ServerResponse
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(error);
    }

    private Mono<ServerResponse> handleNullRequest(NullRequestException ex) {
        log.warn("Null request detected" , ex.getViolations());
        ErrorResponse error = new ErrorResponse(
                Constants.ERROR_CODE_NULL_REQUEST,
                Constants.NULL_REQUEST_MESSAGE,
                HttpStatus.BAD_REQUEST.value()
        );
        return ServerResponse
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(error);
    }

    private Mono<ServerResponse> handleValidationException(ValidationException ex) {
        log.warn("Validation exception: {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse(
                Constants.ERROR_CODE_VALIDATION_ERROR,
                ex.getMessage() != null ? ex.getMessage() : Constants.VALIDATION_ERROR_MESSAGE,
                HttpStatus.BAD_REQUEST.value(),
                ex.getViolations()
        );
        return ServerResponse
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(error);
    }

    private Mono<ServerResponse> handleServiceUnavailable(ServiceUnavailableException ex) {
        log.warn("Service unavailable: {} - Circuit breaker may be open", ex.getServiceName());
        ErrorResponse error = new ErrorResponse(
                ex.getErrorCode(),
                ex.getMessage(),
                HttpStatus.SERVICE_UNAVAILABLE.value()
        );
        return ServerResponse
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(error);
    }

    private Mono<ServerResponse> handleResourceNotFound(ResourceNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
                ex.getErrorCode(),
                Constants.REQUESTED_RESOURCE_NOT_FOUND,
                HttpStatus.NOT_FOUND.value()
        );
        return ServerResponse
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(error);
    }

    private Mono<ServerResponse> handleInvalidInput(InvalidInputException ex) {
        ErrorResponse error = new ErrorResponse(
                ex.getErrorCode(),
                Constants.PROVIDED_INPUT_INVALID,
                HttpStatus.BAD_REQUEST.value()
        );
        return ServerResponse
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(error);
    }

    private Mono<ServerResponse> handleBusinessRule(BusinessRuleException ex) {
        ErrorResponse error = new ErrorResponse(
                ex.getErrorCode(),
                Constants.OPERATION_VIOLATES_BUSINESS_RULE,
                HttpStatus.BAD_REQUEST.value()
        );
        return ServerResponse
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(error);
    }

    private Mono<ServerResponse> handleIllegalArgument(IllegalArgumentException ex) {
        log.warn("Illegal argument: {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse(
                Constants.ERROR_CODE_INVALID_INPUT,
                Constants.PROVIDED_INPUT_INVALID,
                HttpStatus.BAD_REQUEST.value()
        );
        return ServerResponse
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(error);
    }

    private Mono<ServerResponse> handleDomainException(DomainException ex) {
        ErrorResponse error = new ErrorResponse(
                ex.getErrorCode(),
                Constants.ERROR_PROCESSING_REQUEST,
                HttpStatus.BAD_REQUEST.value()
        );
        return ServerResponse
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(error);
    }

    private Mono<ServerResponse> handleUnexpectedError(Throwable ex) {
        log.error("Unexpected error occurred", ex);
        ErrorResponse error = new ErrorResponse(
                "INTERNAL_ERROR",
                "An unexpected error occurred. Please try again later",
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        return ServerResponse
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(error);
    }
}

