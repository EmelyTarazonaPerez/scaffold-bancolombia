package co.com.bancolombia.api;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Error response with user-friendly message")
public record ErrorResponse(
        @Schema(description = "Error code", example = "RESOURCE_NOT_FOUND")
        String code,
        @Schema(description = "User-friendly error message", example = "The requested resource was not found")
        String message,
        @Schema(description = "HTTP status code", example = "404")
        int status,
        @Schema(description = "Timestamp when error occurred")
        LocalDateTime timestamp
) {
    public ErrorResponse(String code, String message, int status) {
        this(code, message, status, LocalDateTime.now());
    }
}

