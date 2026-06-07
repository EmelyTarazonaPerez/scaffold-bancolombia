package co.com.bancolombia.model.franchise.gateways;

import co.com.bancolombia.model.franchise.Franchise;
import reactor.core.publisher.Mono;

public interface FranchiseRepository {
    Mono<Franchise> save(Franchise franchise);

    Mono<Franchise> findById(String id);

    Mono<Void> deleteById(String id);

    Mono<Boolean> existsById(String id);
}
