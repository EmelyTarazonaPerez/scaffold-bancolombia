package co.com.bancolombia.api;

import co.com.bancolombia.model.branch.Branch;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request to add a new branch to a franchise")
public record AddBranchRequest(
        @Schema(description = "Branch name", example = "Downtown Branch")
        String name
) {
    public Branch toBranch() {
        return Branch.builder()
                .name(name)
                .build();
    }
}

