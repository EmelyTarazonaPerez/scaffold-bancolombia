package co.com.bancolombia.api;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request to update a franchise name")
public record UpdateFranchiseNameRequest(
        @Schema(description = "New name for the franchise", example = "Bancolombia South Franchise")
        String newName
) {}


