package co.com.bancolombia.usecase.product;

import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.model.exception.Exceptions;
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

    public Flux<Product> execute(String franchiseId) {
        return franchiseRepository.findById(franchiseId)
                .switchIfEmpty(Mono.error(Exceptions.franchiseNotFound()))
                .flatMapMany(franchise -> {
                    // Obtener el producto con mayor stock de cada sucursal
                    return Flux.fromIterable(franchise.getBranches())
                            .filter(branch -> !branch.getProducts().isEmpty())
                            .map(branch -> branch.getProducts()
                                    .stream()
                                    .max(Comparator.comparingInt(Product::getStock))
                                    .orElseThrow(Exceptions::productNotFound)
                            );
                });
    }
}
