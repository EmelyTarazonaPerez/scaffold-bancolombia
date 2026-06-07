package co.com.bancolombia.mongo;

import co.com.bancolombia.model.exception.ServiceUnavailableException;
import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.franchise.gateways.FranchiseRepository;
import co.com.bancolombia.mongo.helper.AdapterOperations;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class FranchiseRepositoryAdapter
        extends AdapterOperations<
        Franchise,
        FranchiseData,
        String,
        FranchiseMongoRepository>
        implements FranchiseRepository {

    public FranchiseRepositoryAdapter(
            FranchiseMongoRepository repository,
            ObjectMapper mapper) {

        super(
                repository,
                mapper,
                d -> mapper.map(d, Franchise.class)
        );
    }

    @Override
    @CircuitBreaker(name = "mongoCB", fallbackMethod = "findByIdFallback")
    public Mono<Franchise> findById(String id) {
        return super.findById(id);
    }

    @Override
    @CircuitBreaker(name = "mongoCB", fallbackMethod = "saveFallback")
    public Mono<Franchise> save(Franchise entity) {
        return super.save(entity);
    }

    @Override
    @CircuitBreaker(name = "mongoCB", fallbackMethod = "deleteByIdFallback")
    public Mono<Void> deleteById(String id) {
        return super.deleteById(id);
    }

    @Override
    @CircuitBreaker(name = "mongoCB", fallbackMethod = "existsByIdFallback")
    public Mono<Boolean> existsById(String id) {
        return repository.existsById(id);
    }

    // Fallback Methods - Transform technical exceptions to business exceptions

    private Mono<Franchise> findByIdFallback(String id, Exception ex) {
        return Mono.error(new ServiceUnavailableException(
                "MongoDB",
                "MongoDB service is currently unavailable. Please try again later.",
                ex
        ));
    }

    private Mono<Franchise> saveFallback(Franchise entity, Exception ex) {
        return Mono.error(new ServiceUnavailableException(
                "MongoDB",
                "MongoDB service is currently unavailable. Unable to save franchise.",
                ex
        ));
    }

    private Mono<Void> deleteByIdFallback(String id, Exception ex) {
        return Mono.error(new ServiceUnavailableException(
                "MongoDB",
                "MongoDB service is currently unavailable. Unable to delete franchise.",
                ex
        ));
    }

    private Mono<Boolean> existsByIdFallback(String id, Exception ex) {
        return Mono.error(new ServiceUnavailableException(
                "MongoDB",
                "MongoDB service is currently unavailable. Unable to check franchise existence.",
                ex
        ));
    }
}