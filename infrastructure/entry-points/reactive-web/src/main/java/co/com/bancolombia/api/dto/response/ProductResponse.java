package co.com.bancolombia.api.dto.response;

import co.com.bancolombia.model.product.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "Product response DTO")
public record ProductResponse(
        @Schema(description = "Product unique identifier", example = "550e8400-e29b-41d4-a716-446655440000")
        String id,
        @Schema(description = "Product name", example = "Premium Credit Card")
        String name,
        @Schema(description = "Product stock quantity", example = "100")
        Integer stock
) {

    public static ProductResponse fromDomain(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .stock(product.getStock())
                .build();
    }
}

