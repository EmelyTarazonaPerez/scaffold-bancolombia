package co.com.bancolombia.api;

import co.com.bancolombia.model.franchise.Franchise;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request to create a new franchise")
public record CreateFranchiseRequest(
        @Schema(description = "Franchise name", example = "Bancolombia Center Franchise")
        String name
) {
    public Franchise toFranchise() {
        return Franchise.builder()
                .name(name)
                .build();
    }
}