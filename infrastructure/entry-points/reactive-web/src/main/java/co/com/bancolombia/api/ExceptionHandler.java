package co.com.bancolombia.api;

import co.com.bancolombia.api.validator.ConstraintViolationException;
import co.com.bancolombia.api.validator.NullRequestException;
import co.com.bancolombia.api.validator.ValidationException;
import co.com.bancolombia.model.exception.BusinessRuleException;
import co.com.bancolombia.model.exception.DomainException;
import co.com.bancolombia.model.exception.InvalidInputException;
import co.com.bancolombia.model.exception.ResourceNotFoundException;
import co.com.bancolombia.model.exception.ServiceUnavailableException;
import co.com.bancolombia.api.utils.Constans;
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

        if (throwable instanceof ConstraintViolationException constraintViolation) {
            return handleConstraintViolation(constraintViolation);
        } else if (throwable instanceof NullRequestException nullRequest) {
            return handleNullRequest(nullRequest);
        } else if (throwable instanceof ValidationException validation) {
            return handleValidationException(validation);
        } else if (throwable instanceof ServiceUnavailableException serviceUnavailable) {
            return handleServiceUnavailable(serviceUnavailable);
        } else if (throwable instanceof ResourceNotFoundException notFound) {
            return handleResourceNotFound(notFound);
        } else if (throwable instanceof InvalidInputException invalid) {
            return handleInvalidInput(invalid);
        } else if (throwable instanceof BusinessRuleException business) {
            return handleBusinessRule(business);
        } else if (throwable instanceof IllegalArgumentException illegal) {
            return handleIllegalArgument(illegal);
        } else if (throwable instanceof DomainException domain) {
            return handleDomainException(domain);
        } else {
            return handleUnexpectedError(throwable);
        }
    }

    private Mono<ServerResponse> handleConstraintViolation(ConstraintViolationException ex) {
        log.warn("Constraint violation detected: {}", ex.getViolations());
        ErrorResponse error = new ErrorResponse(
                Constans.ERROR_CODE_VALIDATION_ERROR,
                Constans.VALIDATION_ERRORS_MESSAGE,
                HttpStatus.BAD_REQUEST.value(),
                ex.getViolations()
        );
        return ServerResponse
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(error);
    }

    private Mono<ServerResponse> handleNullRequest(NullRequestException ex) {
        log.warn("Null request detected");
        ErrorResponse error = new ErrorResponse(
                Constans.ERROR_CODE_NULL_REQUEST,
                Constans.NULL_REQUEST_MESSAGE,
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
                Constans.ERROR_CODE_VALIDATION_ERROR,
                ex.getMessage() != null ? ex.getMessage() : Constans.VALIDATION_ERROR_MESSAGE,
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
                Constans.REQUESTED_RESOURCE_NOT_FOUND,
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
                Constans.PROVIDED_INPUT_INVALID,
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
                Constans.OPERATION_VIOLATES_BUSINESS_RULE,
                HttpStatus.BAD_REQUEST.value()
        );
        return ServerResponse
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(error);
    }

    private Mono<ServerResponse> handleIllegalArgument(IllegalArgumentException ex) {
        ErrorResponse error = new ErrorResponse(
                Constans.ERROR_CODE_INVALID_INPUT,
                Constans.PROVIDED_INPUT_INVALID,
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
                Constans.ERROR_PROCESSING_REQUEST,
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

