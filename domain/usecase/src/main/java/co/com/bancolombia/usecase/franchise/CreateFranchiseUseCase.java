package co.com.bancolombia.usecase.franchise;

import co.com.bancolombia.model.exception.InvalidInputException;
import co.com.bancolombia.model.exception.ServiceUnavailableException;
import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.franchise.gateways.FranchiseRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public class CreateFranchiseUseCase {
    private final FranchiseRepository franchiseRepository;

    public CreateFranchiseUseCase(FranchiseRepository franchiseRepository) {
        this.franchiseRepository = franchiseRepository;
    }

    public Mono<Franchise> execute(Franchise franchise) {
        franchise.setId(UUID.randomUUID().toString());
        return Mono.just(franchise)
                .filter(item -> item.getName() != null && !item.getName().isBlank())
                .switchIfEmpty(Mono.error(new InvalidInputException(
                        "INVALID_FRANCHISE_NAME",
                        "Franchise name is required"
                )))
                .flatMap(franchiseRepository::save);
    }
}
