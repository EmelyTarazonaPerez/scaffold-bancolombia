package co.com.bancolombia.api.dto.response;

import co.com.bancolombia.model.franchise.Franchise;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Schema(description = "Franchise response DTO")
public record FranchiseResponse(
        @Schema(description = "Franchise unique identifier", example = "550e8400-e29b-41d4-a716-446655440000")
        String id,
        @Schema(description = "Franchise name", example = "Bancolombia - Centro")
        String name,
        @Schema(description = "Branches in the franchise")
        List<BranchResponse> branches
) {
    public static FranchiseResponse fromDomain(Franchise franchise) {
        List<BranchResponse> branches = franchise.getBranches() != null && !franchise.getBranches().isEmpty()
                ? franchise.getBranches().stream()
                .map(BranchResponse::fromDomain)
                .collect(Collectors.toList())
                : new ArrayList<>();

        return FranchiseResponse.builder()
                .id(franchise.getId())
                .name(franchise.getName())
                .branches(branches)
                .build();
    }
}

