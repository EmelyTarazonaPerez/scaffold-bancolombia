package co.com.bancolombia.usecase.product;

import co.com.bancolombia.model.product.Product;
import co.com.bancolombia.model.product.gateways.ProductRepository;
import reactor.core.publisher.Mono;

public class UpdateProductNameUseCase {
    private final ProductRepository productRepository;

    public UpdateProductNameUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Mono<Product> execute(String productId, String newName) {
        return productRepository.findById(productId)
                .flatMap(product -> {
                    product.setName(newName);
                    return productRepository.save(product);
                })
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Product not found")));
    }
}
