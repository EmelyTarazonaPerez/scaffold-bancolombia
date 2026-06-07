package co.com.bancolombia.usecase.product;

import co.com.bancolombia.model.product.Product;
import co.com.bancolombia.model.product.gateways.ProductRepository;
import reactor.core.publisher.Mono;

public class GetMaxStockProductUseCase {
    private final ProductRepository productRepository;

    public GetMaxStockProductUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Mono<Product> execute() {
        return productRepository.findMaxStockProduct()
                .switchIfEmpty(Mono.error(new IllegalArgumentException("No products found")));
    }
}
