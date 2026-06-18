package co.com.bancolombia.mongo.helper;

import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.mongo.FranchiseData;
import co.com.bancolombia.mongo.FranchiseMongoRepository;
import co.com.bancolombia.mongo.IFranchiseRepositoryAdapter;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.data.domain.Example;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdapterOperationsTest {

    @Mock
    private FranchiseMongoRepository repository;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private CircuitBreaker circuitBreaker;

    private IFranchiseRepositoryAdapter adapter;

    private Franchise franchise;
    private FranchiseData franchiseData;

    @BeforeEach
    void setUp() {
        franchise = Franchise.builder()
                .id("franchise-1")
                .name("Test Franchise")
                .branches(new ArrayList<>())
                .build();

        franchiseData = FranchiseData.builder()
                .id("franchise-1")
                .name("Test Franchise")
                .branches(new ArrayList<>())
                .build();

        lenient().when(circuitBreaker.tryAcquirePermission()).thenReturn(true);
        lenient().when(circuitBreaker.getCurrentTimestamp()).thenReturn(System.nanoTime());

        adapter = new IFranchiseRepositoryAdapter(repository, objectMapper, circuitBreaker);
    }

    @Test
    void shouldSaveFranchiseSuccessfully() {
        when(objectMapper.map(any(Franchise.class), any())).thenReturn(franchiseData);
        when(objectMapper.map(any(FranchiseData.class), any())).thenReturn(franchise);
        when(repository.save(any(FranchiseData.class))).thenReturn(Mono.just(franchiseData));

        StepVerifier.create(adapter.save(franchise))
                .expectNext(franchise)
                .verifyComplete();
    }

    @Test
    void shouldFindFranchiseById() {
        when(objectMapper.map(any(FranchiseData.class), any())).thenReturn(franchise);
        when(repository.findById("franchise-1")).thenReturn(Mono.just(franchiseData));

        StepVerifier.create(adapter.findById("franchise-1"))
                .expectNext(franchise)
                .verifyComplete();
    }

    @Test
    void shouldFindFranchiseByExample() {
        when(objectMapper.map(any(Franchise.class), any())).thenReturn(franchiseData);
        when(objectMapper.map(any(FranchiseData.class), any())).thenReturn(franchise);
        when(repository.findAll(any(Example.class))).thenReturn(Flux.just(franchiseData));

        StepVerifier.create(adapter.findByExample(franchise))
                .expectNext(franchise)
                .verifyComplete();
    }

    @Test
    void shouldFindAllFranchises() {
        when(objectMapper.map(any(FranchiseData.class), any())).thenReturn(franchise);
        when(repository.findAll()).thenReturn(Flux.just(franchiseData));

        StepVerifier.create(adapter.findAll())
                .expectNext(franchise)
                .verifyComplete();
    }

    @Test
    void shouldDeleteFranchiseById() {
        when(repository.deleteById("franchise-1")).thenReturn(Mono.empty());

        StepVerifier.create(adapter.deleteById("franchise-1"))
                .verifyComplete();
    }
}