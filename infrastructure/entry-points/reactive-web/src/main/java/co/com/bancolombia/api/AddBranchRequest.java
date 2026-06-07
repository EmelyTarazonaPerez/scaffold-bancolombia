package co.com.bancolombia.api;

import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.model.product.Product;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Schema(description = "Request to add a new branch to a franchise")
public record AddBranchRequest(
        @Schema(description = "Branch name", example = "Downtown Branch")
        String name
) {
    public Branch toBranch() {
        return Branch.builder()
                .id(UUID.randomUUID().toString())
                .name(name)
                .build();
    }
}

