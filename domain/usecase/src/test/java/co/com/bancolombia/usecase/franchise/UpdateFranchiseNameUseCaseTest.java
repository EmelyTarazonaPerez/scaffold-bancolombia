package co.com.bancolombia.usecase.franchise;

import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.franchise.gateways.FranchiseRepository;
import co.com.bancolombia.model.product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UpdateFranchiseNameUseCaseTest {

    @Mock
    FranchiseRepository franchiseRepository;
    @InjectMocks
    UpdateFranchiseNameUseCase updateFranchiseNameUseCase;

    String franchiseId = "1";

    @BeforeEach
    void setUp() {
        updateFranchiseNameUseCase = new UpdateFranchiseNameUseCase(franchiseRepository);
    }

    @Test
    void testUpdateFranchiseNameUseCase() {
        // Aquí puedes agregar pruebas para el método execute del UpdateFranchiseNameUseCase
        Product productOld = new Product("2", "Old Name", 10);
        Product productNew = new Product("3", "Name", 10);
        Franchise franchise = new Franchise(franchiseId, "Franchise 1", null);
        Branch branch = new Branch("1", "Branch 1", null);
        branch.setProducts(new ArrayList<>(List.of(productOld)));
        franchise.setBranches(new ArrayList<>(List.of(branch)));

        when(franchiseRepository.findById(franchiseId)).thenReturn(reactor.core.publisher.Mono.just(franchise));
        when(franchiseRepository.save(franchise)).thenReturn(reactor.core.publisher.Mono.just(franchise));

        StepVerifier.create(updateFranchiseNameUseCase.execute(franchiseId, "New Name"))
                .assertNext(franchise1 -> {
                    assert franchise1.getName().equals("New Name");
                }).verifyComplete();
    }
}
