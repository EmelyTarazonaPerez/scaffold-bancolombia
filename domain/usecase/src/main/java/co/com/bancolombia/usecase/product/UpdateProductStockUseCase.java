package co.com.bancolombia.usecase.product;

import co.com.bancolombia.model.exception.BusinessRuleException;
import co.com.bancolombia.model.exception.Exceptions;
import co.com.bancolombia.model.product.Product;
import co.com.bancolombia.model.franchise.gateways.FranchiseRepository;
import co.com.bancolombia.usecase.utils.Utis;
import reactor.core.publisher.Mono;

public class UpdateProductStockUseCase {
    private final FranchiseRepository franchiseRepository;

    public UpdateProductStockUseCase(FranchiseRepository franchiseRepository) {
        this.franchiseRepository = franchiseRepository;
    }

    public Mono<Product> execute(String franchiseId, String branchId, String productId, Integer newStock) {
        if (newStock < 0) {
            return Mono.error(new BusinessRuleException("Stock cannot be negative"));
        }

        return franchiseRepository.findById(franchiseId)
                .switchIfEmpty(Mono.error(Exceptions.franchiseNotFound()))
                .flatMap(franchise -> {
                    Product productToUpdate = Utis.findProduct(franchise, branchId, productId);
                    productToUpdate.setStock(newStock);
                    return franchiseRepository.save(franchise)
                            .thenReturn(productToUpdate);
                });
    }
}
