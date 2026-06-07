package co.com.bancolombia.api;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request to update product stock")
public record UpdateProductStockRequest(
        @Schema(description = "New stock value for the product", example = "50")
        Integer newStock
) {}

