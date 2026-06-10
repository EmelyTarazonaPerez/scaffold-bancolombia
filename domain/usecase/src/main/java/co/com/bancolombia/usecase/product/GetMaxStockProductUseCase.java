package co.com.bancolombia.usecase.product;

import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.model.branch.gateways.BranchRepository;
import co.com.bancolombia.model.exception.Exceptions;
import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.product.Product;
import co.com.bancolombia.model.franchise.gateways.FranchiseRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;

public class GetMaxStockProductUseCase {
    private final FranchiseRepository franchiseRepository;
    public GetMaxStockProductUseCase(FranchiseRepository franchiseRepository) {
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
