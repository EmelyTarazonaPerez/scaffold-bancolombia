package co.com.bancolombia.mongo.helper;

import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.mongo.FranchiseData;
import co.com.bancolombia.mongo.FranchiseMongoRepository;
import co.com.bancolombia.mongo.FranchiseRepositoryAdapter;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.data.domain.Example;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AdapterOperationsTest {

    @Mock
    private FranchiseMongoRepository repository;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private CircuitBreaker circuitBreaker;

    private FranchiseRepositoryAdapter adapter;

    private Franchise franchise;
    private FranchiseData franchiseData;
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);

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

        when(objectMapper.map(any(FranchiseData.class), any())).thenReturn(franchise);
        when(circuitBreaker.tryAcquirePermission()).thenReturn(true);
        when(circuitBreaker.getCurrentTimestamp()).thenReturn(System.nanoTime());

        adapter = new FranchiseRepositoryAdapter(repository, objectMapper, circuitBreaker);
    }

    @AfterEach
    void tearDown() throws Exception {
        if (closeable != null) {
            closeable.close();
        }
    }

    @Test
    void testSave() {
        when(objectMapper.map(any(Franchise.class), any())).thenReturn(franchiseData);
        when(repository.save(any(FranchiseData.class))).thenReturn(Mono.just(franchiseData));

        StepVerifier.create(adapter.save(franchise))
                .expectNext(franchise)
                .verifyComplete();
    }

    @Test
    void testSaveAll() {
        Flux<Franchise> franchises = Flux.just(franchise);

        when(objectMapper.map(any(Franchise.class), any())).thenReturn(franchiseData);
        when(repository.saveAll(any(Flux.class))).thenReturn(Flux.just(franchiseData));

        StepVerifier.create(adapter.saveAll(franchises))
                .expectNext(franchise)
                .verifyComplete();
    }

    @Test
    void testFindById() {
        when(repository.findById("franchise-1")).thenReturn(Mono.just(franchiseData));

        StepVerifier.create(adapter.findById("franchise-1"))
                .expectNext(franchise)
                .verifyComplete();
    }

    @Test
    void testFindByExample() {
        when(objectMapper.map(any(Franchise.class), any())).thenReturn(franchiseData);
        when(repository.findAll(any(Example.class))).thenReturn(Flux.just(franchiseData));

        StepVerifier.create(adapter.findByExample(franchise))
                .expectNext(franchise)
                .verifyComplete();
    }

    @Test
    void testFindAll() {
        when(repository.findAll()).thenReturn(Flux.just(franchiseData));

        StepVerifier.create(adapter.findAll())
                .expectNext(franchise)
                .verifyComplete();
    }

    @Test
    void testDeleteById() {
        when(repository.deleteById("franchise-1")).thenReturn(Mono.empty());

        StepVerifier.create(adapter.deleteById("franchise-1"))
                .verifyComplete();
    }
}
