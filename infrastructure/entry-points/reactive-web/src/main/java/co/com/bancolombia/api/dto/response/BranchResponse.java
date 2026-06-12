package co.com.bancolombia.api.dto.response;

import co.com.bancolombia.model.branch.Branch;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Schema(description = "Branch response DTO")
public record BranchResponse(
        @Schema(description = "Branch unique identifier", example = "550e8400-e29b-41d4-a716-446655440000")
        String id,
        @Schema(description = "Branch name", example = "Downtown Branch")
        String name,
        @Schema(description = "Products in the branch")
        List<ProductResponse> products
) {
    public static BranchResponse fromDomain(Branch branch) {
        List<ProductResponse> products = branch.getProducts() != null && !branch.getProducts().isEmpty()
                ? branch.getProducts().stream()
                .map(ProductResponse::fromDomain)
                .collect(Collectors.toList())
                : new ArrayList<>();

        return BranchResponse.builder()
                .id(branch.getId())
                .name(branch.getName())
                .products(products)
                .build();
    }
}

