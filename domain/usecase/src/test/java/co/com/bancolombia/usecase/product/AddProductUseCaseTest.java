package co.com.bancolombia.usecase.product;

import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.model.exception.Exceptions;
import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.franchise.gateways.FranchiseRepository;
import co.com.bancolombia.model.product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AddProductUseCaseTest {

    @InjectMocks
    private AddProductUseCase addProductUseCase;

    @Mock
    private FranchiseRepository franchiseRepository;
    String franchiseId = "1";

    @BeforeEach
    void setUp() {
        addProductUseCase = new AddProductUseCase(franchiseRepository);
    }

    @Test
    @DisplayName("Test AddProductUseCase successfully")
    void testAddProductUseCase() {
        Product productOld = new Product("2", "Old Name", 10);
        Product productNew = new Product("3", "Name", 10);
        Franchise franchise = new Franchise(franchiseId, "Franchise 1", null);
        Branch branch = new Branch("1", "Branch 1", null);
        branch.setProducts(new ArrayList<>(List.of(productOld)));
        franchise.setBranches(new ArrayList<>(List.of(branch)));

        when(franchiseRepository.findById(franchiseId)).thenReturn(Mono.just(franchise));
        when(franchiseRepository.save(franchise)).thenReturn(Mono.just(franchise));

        StepVerifier.create(addProductUseCase.execute(franchiseId, "1", productNew))
                .assertNext(addedProduct -> {
                    assertEquals("3", addedProduct.getId());
                    assertEquals("Name", addedProduct.getName());
                    assertEquals(10, addedProduct.getStock());
                    assertEquals(2, branch.getProducts().size());
                })
                .verifyComplete();
    }

    @Test
    @DisplayName("Test AddProductUseCase with non-existing franchise")
    void testAddProductUseCaseFranchiseNotFound() {
        Product productNew = new Product("3", "Name", 10);
        when(franchiseRepository.findById(franchiseId)).thenReturn(Mono.empty());
        StepVerifier.create(addProductUseCase.execute(franchiseId, "1", productNew))
                .expectError(Exceptions.franchiseNotFound().getClass())
                .verify();
    }
}
