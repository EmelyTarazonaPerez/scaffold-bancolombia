package co.com.bancolombia.usecase.franchise;

import co.com.bancolombia.model.exception.InvalidInputException;
import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.franchise.gateways.FranchiseRepository;
import reactor.core.publisher.Mono;

import static co.com.bancolombia.usecase.utils.Utis.*;



public class CreateFranchiseUseCase {
    private final FranchiseRepository franchiseRepository;

    public CreateFranchiseUseCase(FranchiseRepository franchiseRepository) {
        this.franchiseRepository = franchiseRepository;
    }

    public Mono<Franchise> execute(Franchise franchise) {
        validateFranchiseName(franchise.getName());
        return franchiseRepository.findByName(franchise.getName())
                .hasElement()
                .flatMap(exists -> exists
                        ? Mono.error(new InvalidInputException(
                        DUPLICATE_FRANCHISE_NAME,
                        ERROR_SAME_NAME))
                        : franchiseRepository.save(franchise));
    }

    private void validateFranchiseName(String name) {
        if (name == null || name.isBlank()) {
            throw new InvalidInputException(
                    INVALID_FRANCHISE_NAME,
                    ERROR_NAME_REQUIRED
            );
        }
    }
}

