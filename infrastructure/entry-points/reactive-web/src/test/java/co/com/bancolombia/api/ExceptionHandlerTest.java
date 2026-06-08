package co.com.bancolombia.api;

import co.com.bancolombia.model.exception.BusinessRuleException;
import co.com.bancolombia.model.exception.InvalidInputException;
import co.com.bancolombia.model.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.*;

@DisplayName("ExceptionHandler Tests")
class ExceptionHandlerTest {

    private ExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new ExceptionHandler();
    }

    @Test
    @DisplayName("Should handle ResourceNotFoundException")
    void testHandleResourceNotFoundException() {
        ResourceNotFoundException exception = new ResourceNotFoundException("Franchise", "123");

        exceptionHandler.handleException(exception)
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    @DisplayName("Should handle InvalidInputException")
    void testHandleInvalidInputException() {
        InvalidInputException exception = new InvalidInputException("name", "cannot be empty");

        exceptionHandler.handleException(exception)
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    @DisplayName("Should handle BusinessRuleException")
    void testHandleBusinessRuleException() {
        BusinessRuleException exception = new BusinessRuleException("Stock cannot be negative");

        exceptionHandler.handleException(exception)
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    @DisplayName("Should handle IllegalArgumentException")
    void testHandleIllegalArgumentException() {
        IllegalArgumentException exception = new IllegalArgumentException("Invalid input");

        exceptionHandler.handleException(exception)
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    @DisplayName("Should handle unexpected exception")
    void testHandleUnexpectedException() {
        RuntimeException exception = new RuntimeException("Unexpected error");

        exceptionHandler.handleException(exception)
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
    }
}


