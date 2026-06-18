package co.com.bancolombia.usecase.product;

import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.franchise.gateways.IFranchiseRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetMaxStockProductUseCaseTest {

    @InjectMocks
    private GetMaxStockProductUseCase getMaxStockProductUseCase;
    @Mock
    private IFranchiseRepository IFranchiseRepository;
    String franchiseId = "1";

    @BeforeEach
    void setUp() {
        getMaxStockProductUseCase = new GetMaxStockProductUseCase(IFranchiseRepository);
    }

    @Test
    @DisplayName("Test GetMaxStockProductUseCase successfully")
    void testGetMaxStockProductUseCase() {
        // Aquí puedes agregar pruebas para el método execute del GetMaxStockProductUseCase
        Product product = new Product("2", "Old Name", 10);
        Franchise franchise = new Franchise(franchiseId, "Franchise 1", null);
        Branch branch = new Branch("1", "Branch 1", null);
        branch.setProducts(java.util.List.of(product));
        franchise.setBranches(java.util.List.of(branch));

        when(IFranchiseRepository.findById(franchiseId)).thenReturn(Mono.just(franchise));
        when(IFranchiseRepository.findMaxStockProductByBranch(franchiseId)).thenReturn(Mono.just(franchise));

        StepVerifier.create(getMaxStockProductUseCase.execute(franchiseId))
                .assertNext(result -> {
                    // Aquí puedes agregar aserciones para verificar el resultado
                    assertEquals("Franchise 1", result.getName());
                    assertEquals(branch.getProducts(), result.getBranches().get(0).getProducts());

                })
                .verifyComplete();
    }
}
