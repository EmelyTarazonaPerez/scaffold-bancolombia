package co.com.bancolombia.api;

import co.com.bancolombia.model.product.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

@Schema(description = "Product in nested request")
public record ProductInBranchRequest(
        @Schema(description = "Product name", example = "agua")
        String name,
        @Schema(description = "Product stock", example = "5")
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


