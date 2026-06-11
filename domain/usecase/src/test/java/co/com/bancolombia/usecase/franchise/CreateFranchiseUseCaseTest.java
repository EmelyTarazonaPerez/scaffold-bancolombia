package co.com.bancolombia.usecase.franchise;

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

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateFranchiseUseCaseTest {

    @Mock
    FranchiseRepository franchiseRepository;
    @InjectMocks
    CreateFranchiseUseCase CreateFranchiseUseCase;

    String franchiseId = "1";

    @BeforeEach
    void setUp() {
        CreateFranchiseUseCase = new CreateFranchiseUseCase(franchiseRepository);
    }

    @Test
    @DisplayName("Test  CreateFranchiseUseCase successfully ")
    public void TestCreateFranchiseUseCase() {
        Product productOld = new Product("2", "Old Name", 10);
        Product productNew = new Product("3", "Name", 10);
        Franchise franchise = new Franchise(franchiseId, "Franchise 1", null);
        Branch branch = new Branch("1", "Branch 1", null);
        branch.setProducts(new ArrayList<>(List.of(productOld)));
        franchise.setBranches(new ArrayList<>(List.of(branch)));

        when(franchiseRepository.save(franchise)).thenReturn(Mono.just(franchise));
        when(franchiseRepository.save(franchise)).thenReturn(reactor.core.publisher.Mono.just(franchise));

        StepVerifier.create(CreateFranchiseUseCase.execute(franchise))
                .assertNext(franchise1 -> {
                    assert franchise1.getId().equals(franchiseId);
                    assert franchise1.getName().equals("Franchise 1");
                    assert franchise1.getBranches().size() == 1;
                    Branch branch1 = franchise1.getBranches().get(0);
                    assert branch1.getId().equals("1");
                    assert branch1.getName().equals("Branch 1");
                    assert branch1.getProducts().size() == 1;
                    Product product = branch1.getProducts().get(0);
                    assert product.getId().equals("2");
                    assert product.getName().equals("Old Name");
                    assert product.getStock() == 10;
                }).verifyComplete();
    }

}
