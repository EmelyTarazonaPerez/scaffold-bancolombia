package co.com.bancolombia.usecase.product;

import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.model.exception.Exceptions;
import co.com.bancolombia.model.exception.ResourceNotFoundException;
import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.franchise.gateways.FranchiseRepository;
import co.com.bancolombia.model.product.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("UpdateProductNameUseCase Test")
class UpdateProductNameUseCaseTest {

    @InjectMocks
    UpdateProductNameUseCase updateProductNameUseCase;

    @Mock
    FranchiseRepository franchiseRepository;

    String franchiseId = "franchise1";

    @BeforeEach
    void setUp() {
        updateProductNameUseCase = new UpdateProductNameUseCase(franchiseRepository);
    }

    @Test
    @DisplayName("Test change product name successfully")
    void testChangeNameProduct() {
        Product product = new Product("2", "Old Name", 10);
        Franchise franchise = new Franchise(franchiseId, "Franchise 1", null);
        Branch branch = new Branch("1", "Branch 1", null);
        branch.setProducts(java.util.List.of(product));
        franchise.setBranches(java.util.List.of(branch));

        when(franchiseRepository.findById(franchiseId)).thenReturn(Mono.just(franchise));
        when(franchiseRepository.save(franchise)).thenReturn(Mono.just(franchise));

        StepVerifier.create(updateProductNameUseCase
                        .execute(franchiseId, "1", "2", "New Name"))
                .assertNext(updatedProduct -> {assertEquals("New Name", product.getName());})
                .verifyComplete();
    }

    @Test
    @DisplayName("Test change product name - Franchise not found")
    public void testChangeNameProductFranchiseNotFound() {
        when(franchiseRepository.findById(franchiseId)).thenReturn(Mono.empty());

        StepVerifier.create(updateProductNameUseCase
                        .execute(franchiseId, "1", "2", "New Name"))
                .expectError(ResourceNotFoundException.class)
                .verify();
    }

}
