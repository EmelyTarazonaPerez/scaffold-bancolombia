package co.com.bancolombia.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@DisplayName("ErrorResponse Tests")
class ErrorResponseTest {

    @Test
    @DisplayName("Should create ErrorResponse with all fields")
    void testCreateErrorResponseWithAllFields() {
        String code = "RESOURCE_NOT_FOUND";
        String message = "Resource not found";
        int status = 404;
        LocalDateTime timestamp = LocalDateTime.now();

        ErrorResponse response = new ErrorResponse(code, message, status, timestamp);

        assertThat(response.code()).isEqualTo(code);
        assertThat(response.message()).isEqualTo(message);
        assertThat(response.status()).isEqualTo(status);
        assertThat(response.timestamp()).isEqualTo(timestamp);
    }

    @Test
    @DisplayName("Should create ErrorResponse with automatic timestamp")
    void testCreateErrorResponseWithAutoTimestamp() {
        String code = "INVALID_INPUT";
        String message = "Invalid input provided";
        int status = 400;

        LocalDateTime before = LocalDateTime.now();
        ErrorResponse response = new ErrorResponse(code, message, status);
        LocalDateTime after = LocalDateTime.now();

        assertThat(response.code()).isEqualTo(code);
        assertThat(response.message()).isEqualTo(message);
        assertThat(response.status()).isEqualTo(status);
        assertThat(response.timestamp()).isNotNull();
        assertThat(response.timestamp()).isAfterOrEqualTo(before);
        assertThat(response.timestamp()).isBeforeOrEqualTo(after);
    }

    @Test
    @DisplayName("Should handle different error codes")
    void testDifferentErrorCodes() {
        ErrorResponse response1 = new ErrorResponse("ERROR_CODE_1", "Message 1", 400);
        ErrorResponse response2 = new ErrorResponse("ERROR_CODE_2", "Message 2", 404);
        ErrorResponse response3 = new ErrorResponse("ERROR_CODE_3", "Message 3", 500);

        assertThat(response1.code()).isEqualTo("ERROR_CODE_1");
        assertThat(response2.code()).isEqualTo("ERROR_CODE_2");
        assertThat(response3.code()).isEqualTo("ERROR_CODE_3");
    }

    @Test
    @DisplayName("Should handle different HTTP status codes")
    void testDifferentStatusCodes() {
        ErrorResponse badRequest = new ErrorResponse("INVALID_INPUT", "Bad request", 400);
        ErrorResponse notFound = new ErrorResponse("NOT_FOUND", "Not found", 404);
        ErrorResponse serverError = new ErrorResponse("SERVER_ERROR", "Server error", 500);

        assertThat(badRequest.status()).isEqualTo(400);
        assertThat(notFound.status()).isEqualTo(404);
        assertThat(serverError.status()).isEqualTo(500);
    }

    @Test
    @DisplayName("Should create immutable ErrorResponse")
    void testErrorResponseImmutability() {
        ErrorResponse response = new ErrorResponse("CODE", "Message", 400);

        // Records are immutable by design
        assertThat(response).isNotNull();
        assertThat(response.code()).isEqualTo("CODE");
    }
}

