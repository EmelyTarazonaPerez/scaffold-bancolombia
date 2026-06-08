package co.com.bancolombia.api;

import co.com.bancolombia.model.product.Product;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Request to add a new product to a branch")
public record AddProductRequest(
        @Schema(description = "Product name", example = "Premium Credit Card")
        String name,
        @Schema(description = "Initial product stock", example = "100")
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

