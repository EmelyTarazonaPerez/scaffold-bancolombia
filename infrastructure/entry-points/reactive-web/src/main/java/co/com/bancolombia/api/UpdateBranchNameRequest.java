package co.com.bancolombia.api;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request to update a branch name")
public record UpdateBranchNameRequest(
        @Schema(description = "New name for the branch", example = "Midtown Branch")
        String newName
) {}

