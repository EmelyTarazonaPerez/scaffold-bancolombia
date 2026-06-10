package co.com.bancolombia.api;

import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.model.product.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Schema(description = "Branch in nested request")
public record BranchInFranchiseRequest(
        @Schema(description = "Branch name", example = "Cucuta")
        @NotNull(message = "Branch name cannot be null")
        @NotEmpty(message = "Branch name cannot be empty")
        String name,
        @Schema(description = "Products in branch")
        @NotNull(message = "Products list cannot be null")
        @Valid
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


