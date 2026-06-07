package co.com.bancolombia.usecase.product;

import co.com.bancolombia.model.product.Product;
import co.com.bancolombia.model.product.gateways.ProductRepository;
import reactor.core.publisher.Mono;

public class UpdateProductStockUseCase {
    private final ProductRepository productRepository;

    public UpdateProductStockUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Mono<Product> execute(String productId, Integer newStock) {
        if (newStock < 0) {
            return Mono.error(new IllegalArgumentException("Stock cannot be negative"));
        }

        return productRepository.findById(productId)
                .flatMap(product -> {
                    product.setStock(newStock);
                    return productRepository.save(product);
                })
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Product not found")));
    }
}
