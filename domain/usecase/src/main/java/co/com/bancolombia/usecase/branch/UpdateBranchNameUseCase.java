package co.com.bancolombia.usecase.branch;

import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.model.exception.Exceptions;
import co.com.bancolombia.model.franchise.gateways.IFranchiseRepository;
import reactor.core.publisher.Mono;

public class UpdateBranchNameUseCase {
    private final IFranchiseRepository franchiseRepository;

    public UpdateBranchNameUseCase(IFranchiseRepository franchiseRepository) {
        this.franchiseRepository = franchiseRepository;
    }

    public Mono<Branch> execute(String franchiseId, String branchId, String newName) {
        return franchiseRepository.findById(franchiseId)
                .switchIfEmpty(Mono.error(Exceptions.franchiseNotFound()))
                .flatMap(franchise -> {
                    Branch branchToUpdate = franchise.getBranches()
                            .stream()
                            .filter(b -> b.getId().equals(branchId))
                            .findFirst()
                            .orElseThrow(Exceptions::branchNotFound);

                    branchToUpdate.setName(newName);
                    return franchiseRepository.save(franchise)
                            .thenReturn(branchToUpdate);
                });
    }
}
