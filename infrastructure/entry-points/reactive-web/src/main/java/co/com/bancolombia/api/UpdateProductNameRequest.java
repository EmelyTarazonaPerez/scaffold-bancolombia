package co.com.bancolombia.api;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request to update a product name")
public record UpdateProductNameRequest(
        @Schema(description = "New name for the product", example = "Platinum Credit Card")
        String newName
) {}


