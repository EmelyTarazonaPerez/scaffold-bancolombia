package co.com.bancolombia.api;

import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.model.franchise.Franchise;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Schema(description = "Request to create a new franchise with branches and products")
public record CreateFranchiseRequest(
        @Schema(description = "Franchise name", example = "Bancolombia - Centro")
        @NotEmpty(message = "Franchise name must not be empty")
        String name,
        @Schema(description = "Branches with products", example = "[]")
        List<BranchInFranchiseRequest> branches
) {
    public Franchise toFranchise() {
        List<Branch> branchList = branches != null && !branches.isEmpty()
                ? branches.stream()
                .map(BranchInFranchiseRequest::toBranch)
                .collect(Collectors.toList())
                : new ArrayList<>();

        return Franchise.builder()
                .name(name)
                .branches(branchList)
                .build();
    }
}