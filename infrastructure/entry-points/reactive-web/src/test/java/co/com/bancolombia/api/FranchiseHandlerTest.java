package co.com.bancolombia.api;

import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.product.Product;
import co.com.bancolombia.usecase.franchise.CreateFranchiseUseCase;
import co.com.bancolombia.usecase.franchise.UpdateFranchiseNameUseCase;
import co.com.bancolombia.usecase.branch.AddBranchUseCase;
import co.com.bancolombia.usecase.branch.UpdateBranchNameUseCase;
import co.com.bancolombia.usecase.product.AddProductUseCase;
import co.com.bancolombia.usecase.product.DeleteProductUseCase;
import co.com.bancolombia.usecase.product.UpdateProductStockUseCase;
import co.com.bancolombia.usecase.product.UpdateProductNameUseCase;
import co.com.bancolombia.usecase.product.GetMaxStockProductUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("FranchiseHandler Controller Tests")
class FranchiseHandlerTest {

    private FranchiseHandler franchiseHandler;

    @Mock
    private CreateFranchiseUseCase createFranchiseUseCase;

    @Mock
    private UpdateFranchiseNameUseCase updateFranchiseNameUseCase;

    @Mock
    private AddBranchUseCase addBranchUseCase;

    @Mock
    private UpdateBranchNameUseCase updateBranchNameUseCase;

    @Mock
    private AddProductUseCase addProductUseCase;

    @Mock
    private DeleteProductUseCase deleteProductUseCase;

    @Mock
    private UpdateProductStockUseCase updateProductStockUseCase;

    @Mock
    private UpdateProductNameUseCase updateProductNameUseCase;

    @Mock
    private GetMaxStockProductUseCase getMaxStockProductUseCase;

    @Mock
    private ExceptionHandler exceptionHandler;

    @Mock
    private ServerRequest serverRequest;

    @BeforeEach
    void setUp() {
        franchiseHandler = new FranchiseHandler(
                createFranchiseUseCase,
                updateFranchiseNameUseCase,
                addBranchUseCase,
                updateBranchNameUseCase,
                addProductUseCase,
                deleteProductUseCase,
                updateProductStockUseCase,
                updateProductNameUseCase,
                getMaxStockProductUseCase,
                exceptionHandler
        );
    }

    @Test
    @DisplayName("Should create franchise successfully")
    void testCreateFranchiseSuccess() {
        Franchise franchise = Franchise.builder()
                .id("franchise-1")
                .name("Bancolombia Center")
                .branches(new ArrayList<>())
                .build();

        when(createFranchiseUseCase.execute(any(Franchise.class)))
                .thenReturn(Mono.just(franchise));

        franchiseHandler.createFranchise(serverRequest)
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
    }

}




