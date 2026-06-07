package co.com.bancolombia.usecase.branch;

import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.model.branch.gateways.BranchRepository;
import reactor.core.publisher.Mono;

public class UpdateBranchNameUseCase {
    private final BranchRepository branchRepository;

    public UpdateBranchNameUseCase(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    public Mono<Branch> execute(String branchId, String newName) {
        return branchRepository.findById(branchId)
                .flatMap(branch -> {
                    branch.setName(newName);
                    return branchRepository.save(branch);
                })
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Branch not found")));
    }
}
