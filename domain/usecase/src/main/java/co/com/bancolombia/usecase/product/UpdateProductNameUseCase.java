package co.com.bancolombia.usecase.product;

import co.com.bancolombia.model.exception.Exceptions;
import co.com.bancolombia.model.exception.ResourceNotFoundException;
import co.com.bancolombia.model.product.Product;
import co.com.bancolombia.model.franchise.gateways.FranchiseRepository;
import reactor.core.publisher.Mono;

public class UpdateProductNameUseCase {
    private final FranchiseRepository franchiseRepository;

    public UpdateProductNameUseCase(FranchiseRepository franchiseRepository) {
        this.franchiseRepository = franchiseRepository;
    }

    public Mono<Product> execute(String franchiseId, String branchId, String productId, String newName) {
        return franchiseRepository.findById(franchiseId)
                .switchIfEmpty(Mono.error(Exceptions.franchiseNotFound()))
                .flatMap(franchise -> {
                    Product productToUpdate = franchise.getBranches()
                            .stream()
                            .filter(b -> b.getId().equals(branchId))
                            .flatMap(branch -> branch.getProducts().stream())
                            .filter(p -> p.getId().equals(productId))
                            .findFirst()
                            .orElseThrow(Exceptions::productNotFound);

                    productToUpdate.setName(newName);
                    return franchiseRepository.save(franchise)
                            .thenReturn(productToUpdate);
                });
    }
}
