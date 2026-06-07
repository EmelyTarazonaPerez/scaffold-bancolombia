package co.com.bancolombia.usecase.product;

import co.com.bancolombia.model.product.Product;
import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.model.branch.gateways.BranchRepository;
import co.com.bancolombia.model.product.gateways.ProductRepository;
import reactor.core.publisher.Mono;

public class AddProductUseCase {
    private final BranchRepository branchRepository;
    private final ProductRepository productRepository;

    public AddProductUseCase(BranchRepository branchRepository, ProductRepository productRepository) {
        this.branchRepository = branchRepository;
        this.productRepository = productRepository;
    }

    public Mono<Product> execute(String branchId, Product product) {
        return branchRepository.findById(branchId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Branch not found")))
                .flatMap(branch ->
                    productRepository.save(product)
                            .flatMap(savedProduct -> {
                                branch.getProducts().add(savedProduct);
                                return branchRepository.save(branch)
                                        .thenReturn(savedProduct);
                            })
                );
    }
}
