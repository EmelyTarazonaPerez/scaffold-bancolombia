package co.com.bancolombia.api;

import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.model.product.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Schema(description = "Branch in nested request")
public record BranchInFranchiseRequest(
        @Schema(description = "Branch name", example = "Cucuta")
        String name,
        @Schema(description = "Products in branch")
        List<ProductInBranchRequest> product
) {
    public Branch toBranch() {
        List<Product> products = product != null && !product.isEmpty()
                ? product.stream().map(ProductInBranchRequest::toProduct).collect(Collectors.toList())
                : new java.util.ArrayList<>();

        return Branch.builder()
                .id(UUID.randomUUID().toString())
                .name(name)
                .products(products)
                .build();
    }
}


