package co.com.bancolombia.mongo;

import co.com.bancolombia.model.exception.ServiceUnavailableException;
import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.franchise.gateways.FranchiseRepository;
import co.com.bancolombia.mongo.helper.AdapterOperations;
import co.com.bancolombia.mongo.utils.Constans;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator;
import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Slf4j
@Repository
public class FranchiseRepositoryAdapter
        extends AdapterOperations<
        Franchise,
        FranchiseData,
        String,
        FranchiseMongoRepository>
        implements FranchiseRepository {

    private final CircuitBreaker circuitBreaker;

    public FranchiseRepositoryAdapter(
            FranchiseMongoRepository repository,
            ObjectMapper mapper,
            CircuitBreaker mongoCB) {

        super(
                repository,
                mapper,
                d -> mapper.map(d, Franchise.class)
        );
        this.circuitBreaker = mongoCB;
    }

    @Override
    public Mono<Franchise> findById(String id) {
        return super.findById(id)
                .transformDeferred(CircuitBreakerOperator.of(circuitBreaker))
                .onErrorMap(this::isInfrastructureError,ex -> handleError(ex, "find franchise by id: " + id));
    }

    public Mono<Franchise> findByName(String name) {
        FranchiseData example = new FranchiseData();
        example.setName(name);

        return repository.findOne(Example.of(example))
                .map(this::toEntity);
    }

    @Override
    public Mono<Franchise> save(Franchise entity) {
        return super.save(entity)
                .transformDeferred(CircuitBreakerOperator.of(circuitBreaker))
                .onErrorMap(this::isInfrastructureError,ex -> handleError(ex, "save franchise"));
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return super.deleteById(id)
                .transformDeferred(CircuitBreakerOperator.of(circuitBreaker))
                .onErrorMap(this::isInfrastructureError,
                        ex -> handleError(ex, "delete franchise by id: " + id));
    }

    @Override
    public Mono<Boolean> existsById(String id) {
        return repository.existsById(id)
                .transformDeferred(CircuitBreakerOperator.of(circuitBreaker))
                .onErrorMap(this::isInfrastructureError,
                        ex -> handleError(ex, "check franchise existence by id: " + id));
    }

    @Override
    public Mono<Franchise> findMaxStockProductByBranch(String franchiseId) {
        return repository.findMaxStockProductByBranch(franchiseId)
                .map(data -> {
                    Franchise franchise = mapper.map(data, Franchise.class);
                    franchise.setId(franchiseId);
                    return franchise;
                })
                .transformDeferred(CircuitBreakerOperator.of(circuitBreaker))
                .onErrorMap(this::isInfrastructureError,
                        ex -> handleError(ex,
                                "find max stock product by branch for franchise id: " + franchiseId));
    }

    private Throwable handleError(Throwable ex, String operation) {
        log.error("Error accessing MongoDB during operation '{}': {}", operation, ex.getMessage(), ex);
        return new ServiceUnavailableException(
                Constans.SERVICE_NAME,
                Constans.PERSISTENCE_SERVICE_UNAVAILABLE,
                ex
        );
    }

    private boolean isInfrastructureError(Throwable ex) {
        return ex instanceof org.springframework.dao.DataAccessException
                || ex instanceof com.mongodb.MongoException
                || ex instanceof java.util.concurrent.TimeoutException
                || ex instanceof io.github.resilience4j.circuitbreaker.CallNotPermittedException;
    }
}