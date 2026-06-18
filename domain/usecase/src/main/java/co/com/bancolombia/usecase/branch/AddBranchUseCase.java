package co.com.bancolombia.usecase.branch;

import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.model.exception.Exceptions;
import co.com.bancolombia.model.franchise.gateways.IFranchiseRepository;
import reactor.core.publisher.Mono;

public class AddBranchUseCase {

    private final IFranchiseRepository franchiseRepository;

    public AddBranchUseCase(IFranchiseRepository franchiseRepository) {
        this.franchiseRepository = franchiseRepository;
    }

    public Mono<Branch> execute(String franchiseId, Branch branch) {
        return franchiseRepository.findById(franchiseId)
                .switchIfEmpty(Mono.error(Exceptions.franchiseNotFound()))
                .flatMap(franchise -> {
                    franchise.getBranches().add(branch);

                    return franchiseRepository.save(franchise)
                            .thenReturn(branch);
                });
    }
}
