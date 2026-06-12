package co.com.bancolombia.api.dto.resquest;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request to update a franchise name")
public record UpdateFranchiseNameRequest(
        @Schema(description = "New name for the franchise", example = "Bancolombia South Franchise")
        @NotNull(message = "Franchise name cannot be null")
        @NotEmpty(message = "Franchise name cannot be empty")
        String newName
) {}


