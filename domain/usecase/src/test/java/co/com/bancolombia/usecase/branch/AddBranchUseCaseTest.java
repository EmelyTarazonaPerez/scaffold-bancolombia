package co.com.bancolombia.usecase.branch;

import co.com.bancolombia.model.branch.Branch;
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
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
@ExtendWith(MockitoExtension.class)
public class AddBranchUseCaseTest {

    @Mock
    FranchiseRepository franchiseRepository;

    @InjectMocks
    AddBranchUseCase addBranchUseCase;

    String franchiseId  = "1";

    @BeforeEach
    void setUp() {
        addBranchUseCase = new AddBranchUseCase(franchiseRepository);
    }

    @Test
    @DisplayName("Test  AddBranchUseCase successfully ")
    void testAddBranchUseCase() {
        // Aquí puedes agregar pruebas para el método execute del AddBranchUseCase
        Product product = new Product("2", "Old Name", 10);
        Franchise franchise = new Franchise(franchiseId, "Franchise 1", null);
        Branch branch = new Branch("1", "Branch 1", null);
        branch.setProducts(new ArrayList<>(List.of(product)));
        franchise.setBranches(new ArrayList<>(List.of(branch)));

        when(franchiseRepository.findById(franchiseId)).thenReturn(reactor.core.publisher.Mono.just(franchise));
        when(franchiseRepository.save(franchise)).thenReturn(reactor.core.publisher.Mono.just(franchise));

        StepVerifier.create(addBranchUseCase.execute(franchiseId, branch))
                .assertNext(addedBranch  -> {
                    assertEquals("1", addedBranch.getId());
                    assertEquals("Branch 1", addedBranch.getName());
                    assertEquals(1, addedBranch.getProducts().size());
                    assertEquals("2", addedBranch.getProducts().get(0).getId());

                }).verifyComplete();
    }
}
