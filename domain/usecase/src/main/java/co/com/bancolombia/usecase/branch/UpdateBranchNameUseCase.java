package co.com.bancolombia.usecase.branch;

import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.model.franchise.gateways.FranchiseRepository;
import reactor.core.publisher.Mono;

public class UpdateBranchNameUseCase {
    private final FranchiseRepository franchiseRepository;

    public UpdateBranchNameUseCase(FranchiseRepository franchiseRepository) {
        this.franchiseRepository = franchiseRepository;
    }

    public Mono<Branch> execute(String franchiseId, String branchId, String newName) {
        return franchiseRepository.findById(franchiseId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Franchise not found")))
                .flatMap(franchise -> {
                    Branch branchToUpdate = franchise.getBranches()
                            .stream()
                            .filter(b -> b.getId().equals(branchId))
                            .findFirst()
                            .orElseThrow(() -> new IllegalArgumentException("Branch not found"));

                    branchToUpdate.setName(newName);
                    return franchiseRepository.save(franchise)
                            .thenReturn(branchToUpdate);
                });
    }
}
