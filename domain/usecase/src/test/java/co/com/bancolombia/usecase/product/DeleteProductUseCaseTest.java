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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeleteProductUseCaseTest {

    @InjectMocks
    private DeleteProductUseCase deleteProductUseCase;

    @Mock
    private IFranchiseRepository IFranchiseRepository;

    String franchiseId = "1";

    @BeforeEach
    void setUp() {
        deleteProductUseCase = new DeleteProductUseCase(IFranchiseRepository);
    }

    @Test
    @DisplayName("Test DeleteProductUseCase successfully")
    void testDeleteProductUseCase() {
        Product productOld = new Product("2", "Old Name", 10);
        Franchise franchise = new Franchise(franchiseId, "Franchise 1", null);
        Branch branch = new Branch("1", "Branch 1", null);
        branch.setProducts(new ArrayList<>(List.of(productOld)));
        franchise.setBranches(new ArrayList<>(List.of(branch)));

        when(IFranchiseRepository.findById(franchiseId)).thenReturn(Mono.just(franchise));
        when(IFranchiseRepository.save(any(Franchise.class))).thenReturn(Mono.just(franchise));

        StepVerifier.create(deleteProductUseCase.execute(franchiseId, "1", "2"))
                .verifyComplete();

        assertTrue(branch.getProducts().isEmpty());
    }


}
