package co.com.bancolombia.usecase.product;

import co.com.bancolombia.model.exception.Exceptions;
import co.com.bancolombia.model.exception.ResourceNotFoundException;
import co.com.bancolombia.model.product.Product;
import co.com.bancolombia.model.franchise.gateways.FranchiseRepository;
import co.com.bancolombia.usecase.utils.Utis;
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
                    Product productToUpdate = Utis.findProduct(franchise, branchId, productId);
                    productToUpdate.setName(newName);
                    return franchiseRepository.save(franchise)
                            .thenReturn(productToUpdate);
                });
    }
}
