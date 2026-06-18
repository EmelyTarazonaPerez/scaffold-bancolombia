package co.com.bancolombia.usecase.product;

import co.com.bancolombia.model.exception.Exceptions;
import co.com.bancolombia.model.product.Product;
import co.com.bancolombia.model.franchise.gateways.IFranchiseRepository;
import co.com.bancolombia.usecase.utils.Utils;
import reactor.core.publisher.Mono;

public class UpdateProductNameUseCase {

    private final IFranchiseRepository franchiseRepository;

    public UpdateProductNameUseCase(IFranchiseRepository franchiseRepository) {
        this.franchiseRepository = franchiseRepository;
    }

    public Mono<Product> execute(String franchiseId, String branchId, String productId, String newName) {
        return franchiseRepository.findById(franchiseId)
                .switchIfEmpty(Mono.error(Exceptions.franchiseNotFound()))
                .flatMap(franchise -> {
                    Product productToUpdate = Utils.findProduct(franchise, branchId, productId);
                    productToUpdate.setName(newName);
                    return franchiseRepository.save(franchise)
                            .thenReturn(productToUpdate);
                });
    }
}
