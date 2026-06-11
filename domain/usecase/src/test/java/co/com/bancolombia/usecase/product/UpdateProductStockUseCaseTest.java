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

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UpdateProductStockUseCaseTest {

    @InjectMocks
    private UpdateProductStockUseCase updateProductStockUseCase;

    @Mock
    private FranchiseRepository franchiseRepository;
    String franchiseId = "1";

    @BeforeEach
    void setUp() {
        updateProductStockUseCase = new UpdateProductStockUseCase(franchiseRepository);
    }

    @Test
    @DisplayName("Test UpdateProductStockUseCase successfully")
    void testUpdateProductStockUseCase() {
        // Aquí puedes agregar pruebas para el método execute del UpdateProductStockUseCase
        Product product = new Product("2", "Old Name", 10);
        Franchise franchise = new Franchise(franchiseId, "Franchise 1", null);
        Branch branch = new Branch("1", "Branch 1", null);
        branch.setProducts(java.util.List.of(product));
        franchise.setBranches(java.util.List.of(branch));

        when(franchiseRepository.findById(franchiseId)).thenReturn(Mono.just(franchise));
        when(franchiseRepository.save(franchise)).thenReturn(Mono.just(franchise));

        StepVerifier.create(updateProductStockUseCase.execute(franchiseId, "1", "2", 20))
                .assertNext(updatedProduct -> {
                    assert updatedProduct.getId().equals("2");
                    assert updatedProduct.getStock() == 20;
                })
                .verifyComplete();
    }

    @Test
    @DisplayName("Test UpdateProductStockUseCase with non-existing franchise")
    void testUpdateProductStockUseCaseFranchiseNotFound() {
        when(franchiseRepository.findById(franchiseId)).thenReturn(Mono.empty());
        StepVerifier.create(updateProductStockUseCase.execute(franchiseId, "1", "2", 20))
                .expectError(Exceptions.franchiseNotFound().getClass())
                .verify();
    }

}
