package co.com.bancolombia.usecase.product;

import co.com.bancolombia.model.product.gateways.ProductRepository;
import reactor.core.publisher.Mono;

public class DeleteProductUseCase {
    private final ProductRepository productRepository;

    public DeleteProductUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Mono<Void> execute(String productId) {
        return productRepository.existsById(productId)
                .flatMap(exists -> {
                    if (!exists) {
                        return Mono.error(new IllegalArgumentException("Product not found"));
                    }
                    return productRepository.deleteById(productId);
                });
    }
}
