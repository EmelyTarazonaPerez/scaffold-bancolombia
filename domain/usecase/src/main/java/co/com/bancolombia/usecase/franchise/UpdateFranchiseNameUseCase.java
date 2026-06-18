package co.com.bancolombia.usecase.franchise;

import co.com.bancolombia.model.exception.Exceptions;
import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.franchise.gateways.IFranchiseRepository;
import reactor.core.publisher.Mono;

public class UpdateFranchiseNameUseCase {

    private final IFranchiseRepository IFranchiseRepository;

    public UpdateFranchiseNameUseCase(IFranchiseRepository IFranchiseRepository) {
        this.IFranchiseRepository = IFranchiseRepository;
    }

    public Mono<Franchise> execute(String franchiseId, String newName) {
        return IFranchiseRepository.findById(franchiseId)
                .flatMap(franchise -> {
                    franchise.setName(newName);
                    return IFranchiseRepository.save(franchise);
                })
                .switchIfEmpty(Mono.error(Exceptions.franchiseNotFound()));
    }
}
