package co.com.bancolombia.mongo;

import co.com.bancolombia.model.exception.ServiceUnavailableException;
import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.franchise.gateways.FranchiseRepository;
import co.com.bancolombia.mongo.helper.AdapterOperations;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator;
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

    private final io.github.resilience4j.circuitbreaker.CircuitBreaker circuitBreaker;

    public FranchiseRepositoryAdapter(
            FranchiseMongoRepository repository,
            ObjectMapper mapper,
            CircuitBreakerRegistry circuitBreakerRegistry) {

        super(
                repository,
                mapper,
                d -> mapper.map(d, Franchise.class)
        );
        this.circuitBreaker = circuitBreakerRegistry.circuitBreaker("mongoCB");
    }

    @Override
    public Mono<Franchise> findById(String id) {
        // ⚠️ TEMPORAL: Forzar error para testing del Circuit Breaker
        //return Mono.<Franchise>error(new RuntimeException("TESTING: Simulated MongoDB failure"))
        //        .transformDeferred(CircuitBreakerOperator.of(circuitBreaker))
        //        .onErrorMap(this::transformException);

        // TODO: Descomentar después de testing
         return super.findById(id)
                 .transformDeferred(CircuitBreakerOperator.of(circuitBreaker))
                 .onErrorMap(this::transformException);
    }

    @Override
    public Mono<Franchise> save(Franchise entity) {
        return super.save(entity)
                .transformDeferred(CircuitBreakerOperator.of(circuitBreaker))
                .onErrorMap(this::transformException);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return super.deleteById(id)
                .transformDeferred(CircuitBreakerOperator.of(circuitBreaker))
                .onErrorMap(this::transformException);
    }

    @Override
    public Mono<Boolean> existsById(String id) {
        return repository.existsById(id)
                .transformDeferred(CircuitBreakerOperator.of(circuitBreaker))
                .onErrorMap(this::transformException);
    }

    // Transform technical exceptions to business exceptions
    private Throwable transformException(Throwable ex) {
        return new ServiceUnavailableException(
                "MongoDB",
                "MongoDB service is currently unavailable. Please try again later.",
                ex
        );
    }
}