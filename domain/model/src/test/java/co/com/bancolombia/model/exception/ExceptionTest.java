package co.com.bancolombia.model.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Exception Tests")
class ExceptionTest {

    @Test
    @DisplayName("Should create ResourceNotFoundException with correct message")
    void testResourceNotFoundException() {
        String resourceType = "Franchise";
        String resourceId = "123";

        ResourceNotFoundException exception = new ResourceNotFoundException(resourceType, resourceId);

        assertThat(exception.getErrorCode()).isEqualTo("RESOURCE_NOT_FOUND");
        assertThat(exception.getMessage()).contains(resourceType).contains(resourceId);
        assertThat(exception.getResourceId()).isEqualTo(resourceId);
    }

    @Test
    @DisplayName("Should create InvalidInputException with correct message")
    void testInvalidInputException() {
        String fieldName = "name";
        String reason = "cannot be empty";

        InvalidInputException exception = new InvalidInputException(fieldName, reason);

        assertThat(exception.getErrorCode()).isEqualTo("INVALID_INPUT");
        assertThat(exception.getMessage()).contains(fieldName).contains(reason);
    }

    @Test
    @DisplayName("Should create InvalidInputException with custom message")
    void testInvalidInputExceptionCustomMessage() {
        String message = "Custom error message";

        InvalidInputException exception = new InvalidInputException(message);

        assertThat(exception.getErrorCode()).isEqualTo("INVALID_INPUT");
        assertThat(exception.getMessage()).isEqualTo(message);
    }

    @Test
    @DisplayName("Should create BusinessRuleException with correct message")
    void testBusinessRuleException() {
        String rule = "Stock cannot be negative";

        BusinessRuleException exception = new BusinessRuleException(rule);

        assertThat(exception.getErrorCode()).isEqualTo("BUSINESS_RULE_VIOLATION");
        assertThat(exception.getMessage()).contains(rule);
    }

    @Test
    @DisplayName("Should create BusinessRuleException with custom code")
    void testBusinessRuleExceptionCustomCode() {
        String code = "DUPLICATE_NAME";
        String message = "Product name already exists";

        BusinessRuleException exception = new BusinessRuleException(code, message);

        assertThat(exception.getErrorCode()).isEqualTo(code);
        assertThat(exception.getMessage()).isEqualTo(message);
    }

    @Test
    @DisplayName("Should support exception chaining with cause")
    void testExceptionChaining() {
        Throwable cause = new RuntimeException("Original error");
        ResourceNotFoundException exception = new ResourceNotFoundException("Product", "999");

        assertThat(exception).hasNoCause();
        assertThat(exception).isInstanceOf(DomainException.class);
        assertThat(exception).isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("Should extend DomainException")
    void testExceptionHierarchy() {
        DomainException resourceNotFound = new ResourceNotFoundException("Franchise", "1");
        DomainException invalidInput = new InvalidInputException("name", "invalid");
        DomainException businessRule = new BusinessRuleException("Rule violated");

        assertThat(resourceNotFound).isInstanceOf(DomainException.class);
        assertThat(invalidInput).isInstanceOf(DomainException.class);
        assertThat(businessRule).isInstanceOf(DomainException.class);
    }
}

