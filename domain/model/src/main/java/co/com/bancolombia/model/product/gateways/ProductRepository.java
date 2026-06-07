package co.com.bancolombia.model.product.gateways;

import co.com.bancolombia.model.product.Product;
import reactor.core.publisher.Mono;

public interface ProductRepository {
    Mono<Product> save(Product product);

    Mono<Product> findById(String id);

    Mono<Void> deleteById(String id);

    Mono<Boolean> existsById(String id);

    Mono<Product> findMaxStockProduct();
}
