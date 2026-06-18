package co.com.bancolombia.usecase.product;

import co.com.bancolombia.model.exception.Exceptions;
import co.com.bancolombia.model.franchise.gateways.IFranchiseRepository;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

public class DeleteProductUseCase {

    private final IFranchiseRepository IFranchiseRepository;

    public DeleteProductUseCase(IFranchiseRepository IFranchiseRepository) {
        this.IFranchiseRepository = IFranchiseRepository;
    }

    public Mono<Void> execute(String franchiseId, String branchId, String productId) {
        return IFranchiseRepository.findById(franchiseId)
                .switchIfEmpty(Mono.error(Exceptions.franchiseNotFound()))
                .flatMap(franchise -> {
                    var branch = franchise.getBranches()
                            .stream()
                            .filter(b -> b.getId().equals(branchId))
                            .findFirst()
                            .orElseThrow(Exceptions::branchNotFound);

                    boolean productExists = branch.getProducts()
                            .stream()
                            .anyMatch(p -> p.getId().equals(productId));

                    if (!productExists) {
                        return Mono.error(Exceptions.productNotFound());
                    }

                    var updatedProducts = branch.getProducts()
                            .stream()
                            .filter(p -> !p.getId().equals(productId))
                            .collect(Collectors.toList());

                    branch.setProducts(updatedProducts);
                    return IFranchiseRepository.save(franchise).then();
                });
    }
}
