package co.com.bancolombia.api.dto.resquest;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(description = "Request to update product stock")
public record UpdateProductStockRequest(
        @Schema(description = "New stock value for the product", example = "50")
        @NotNull(message = "Stock cannot be null")
        @Positive(message = "Stock must be a positive number")
        Integer newStock
) {}

