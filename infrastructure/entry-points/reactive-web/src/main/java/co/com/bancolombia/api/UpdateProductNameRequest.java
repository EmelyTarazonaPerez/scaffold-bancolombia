package co.com.bancolombia.api;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request to update a product name")
public record UpdateProductNameRequest(
        @Schema(description = "New name for the product", example = "Premium Credit Card")
        @NotNull(message = "Product name cannot be null")
        @NotEmpty(message = "Product name cannot be empty")
        String newName
) {}


