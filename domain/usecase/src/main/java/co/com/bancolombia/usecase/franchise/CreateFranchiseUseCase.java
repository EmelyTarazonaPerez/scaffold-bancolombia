package co.com.bancolombia.usecase.franchise;

import co.com.bancolombia.model.exception.InvalidInputException;
import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.franchise.gateways.FranchiseRepository;
import reactor.core.publisher.Mono;

public class CreateFranchiseUseCase {
    private final FranchiseRepository franchiseRepository;

    public CreateFranchiseUseCase(FranchiseRepository franchiseRepository) {
        this.franchiseRepository = franchiseRepository;
    }

    public Mono<Franchise> execute(Franchise franchise) {
        if (franchise == null) {
            throw new InvalidInputException(
                    "INVALID_REQUEST",
                    "Franchise cannot be null"
            );
        }
        return franchiseRepository.save(franchise);
    }
}
