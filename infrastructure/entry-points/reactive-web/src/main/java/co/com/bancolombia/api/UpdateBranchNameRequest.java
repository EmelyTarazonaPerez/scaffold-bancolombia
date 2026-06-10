package co.com.bancolombia.api;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request to update a branch name")
public record UpdateBranchNameRequest(
        @Schema(description = "New name for the branch", example = "Downtown Branch")
        @NotNull(message = "Branch name cannot be null")
        @NotEmpty(message = "Branch name cannot be empty")
        String newName
) {}

