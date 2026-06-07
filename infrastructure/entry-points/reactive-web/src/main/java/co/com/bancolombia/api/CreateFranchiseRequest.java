package co.com.bancolombia.api;

import co.com.bancolombia.model.franchise.Franchise;

public record CreateFranchiseRequest(
        String name
) {
    public Franchise toFranchise() {
        return Franchise.builder()
                .name(name)
                .build();
    }
}