package co.com.bancolombia.usecase.product;

import co.com.bancolombia.model.exception.Exceptions;
import co.com.bancolombia.model.exception.ResourceNotFoundException;
import co.com.bancolombia.model.product.Product;
import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.model.franchise.gateways.FranchiseRepository;
import reactor.core.publisher.Mono;

public class AddProductUseCase {
    private final FranchiseRepository franchiseRepository;

    public AddProductUseCase(FranchiseRepository franchiseRepository) {
        this.franchiseRepository = franchiseRepository;
    }

    public Mono<Product> execute(String franchiseId, String branchId, Product product) {
        return franchiseRepository.findById(franchiseId)
                .switchIfEmpty(Mono.error( Exceptions.franchiseNotFound()))
                .flatMap(franchise -> {
                    Branch branch = franchise.getBranches()
                            .stream()
                            .filter(b -> b.getId().equals(branchId))
                            .findFirst()
                            .orElseThrow(Exceptions::branchNotFound);

                    branch.getProducts().add(product);
                    return franchiseRepository.save(franchise)
                            .thenReturn(product);
                });
    }
}
