package co.com.bancolombia.usecase.product;

import co.com.bancolombia.model.exception.Exceptions;
import co.com.bancolombia.model.exception.ResourceNotFoundException;
import co.com.bancolombia.model.product.Product;
import co.com.bancolombia.model.franchise.gateways.FranchiseRepository;
import reactor.core.publisher.Mono;

public class GetMaxStockProductUseCase {
    private final FranchiseRepository franchiseRepository;

    public GetMaxStockProductUseCase(FranchiseRepository franchiseRepository) {
        this.franchiseRepository = franchiseRepository;
    }

    public Mono<Product> execute(String franchiseId) {
        return franchiseRepository.findById(franchiseId)
                .switchIfEmpty(Mono.error(Exceptions.franchiseNotFound()))
                .flatMap(franchise -> {
                    Product maxStockProduct = franchise.getBranches()
                            .stream()
                            .flatMap(branch -> branch.getProducts().stream())
                            .max((p1, p2) -> Integer.compare(p1.getStock(), p2.getStock()))
                            .orElseThrow(Exceptions::productNotFound);

                    return Mono.just(maxStockProduct);
                });
    }
}
