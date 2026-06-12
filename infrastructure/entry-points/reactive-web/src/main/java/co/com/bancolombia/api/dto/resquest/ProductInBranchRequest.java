package co.com.bancolombia.api.dto.resquest;

import co.com.bancolombia.model.product.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

@Schema(description = "Product in nested request")
public record ProductInBranchRequest(
        @Schema(description = "Product name", example = "agua")
        @NotNull(message = "Product name cannot be null")
        @NotEmpty(message = "Product name cannot be empty")
        String name,
        @Schema(description = "Product stock", example = "5")
        @NotNull(message = "Stock cannot be null")
        @Positive(message = "Stock must be a positive number")
        Integer stock
) {
    public Product toProduct() {
        return Product.builder()
                .id(UUID.randomUUID().toString())
                .name(name)
                .stock(stock)
                .build();
    }
}


