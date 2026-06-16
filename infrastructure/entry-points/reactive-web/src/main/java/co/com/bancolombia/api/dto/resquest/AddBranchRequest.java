package co.com.bancolombia.api.dto.resquest;

import co.com.bancolombia.model.branch.Branch;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Schema(description = "Request to add a new branch to a franchise")
public record AddBranchRequest(
        @Schema(description = "Branch name", example = "Downtown Branch")
        @NotNull(message = "Branch name cannot be null")
        @NotEmpty(message = "Branch name cannot be empty")
        String name
) {
    public Branch toBranch() {
        return Branch.builder()
                .id(UUID.randomUUID().toString())
                .name(name)
                .build();
    }
}

