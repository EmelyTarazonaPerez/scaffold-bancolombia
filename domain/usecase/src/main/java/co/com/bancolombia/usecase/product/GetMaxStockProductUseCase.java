package co.com.bancolombia.usecase.product;

import co.com.bancolombia.model.exception.Exceptions;
import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.franchise.gateways.IFranchiseRepository;
import reactor.core.publisher.Mono;

public class GetMaxStockProductUseCase {

    private final IFranchiseRepository franchiseRepository;
    public GetMaxStockProductUseCase(IFranchiseRepository franchiseRepository) {
        this.franchiseRepository = franchiseRepository;
    }

    public Mono<Franchise> execute(String franchiseId) {
        return franchiseRepository.findById(franchiseId)
                .switchIfEmpty(Mono.error(Exceptions.franchiseNotFound()))
                .flatMap(franchise -> {
                    if (franchise.getBranches() == null || franchise.getBranches().isEmpty()) {
                        return Mono.error(Exceptions.branchNotFound());
                    }

                    return franchiseRepository.findMaxStockProductByBranch(franchiseId);
                });
    }
}
