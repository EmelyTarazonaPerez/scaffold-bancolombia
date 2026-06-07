package co.com.bancolombia.usecase.product;

import co.com.bancolombia.model.exception.Exceptions;
import co.com.bancolombia.model.exception.ResourceNotFoundException;
import co.com.bancolombia.model.franchise.gateways.FranchiseRepository;
import reactor.core.publisher.Mono;

public class DeleteProductUseCase {
    private final FranchiseRepository franchiseRepository;

    public DeleteProductUseCase(FranchiseRepository franchiseRepository) {
        this.franchiseRepository = franchiseRepository;
    }

    public Mono<Void> execute(String franchiseId, String branchId, String productId) {
        return franchiseRepository.findById(franchiseId)
                .switchIfEmpty(Mono.error(Exceptions.franchiseNotFound()))
                .flatMap(franchise -> {
                    boolean branchFound = franchise.getBranches()
                            .stream()
                            .filter(b -> b.getId().equals(branchId))
                            .peek(branch -> branch.getProducts()
                                    .removeIf(p -> p.getId().equals(productId)))
                            .findFirst()
                            .isPresent();

                    if (!branchFound) {
                        return Mono.error(Exceptions.branchNotFound());
                    }

                    return franchiseRepository.save(franchise).then();
                });
    }
}
