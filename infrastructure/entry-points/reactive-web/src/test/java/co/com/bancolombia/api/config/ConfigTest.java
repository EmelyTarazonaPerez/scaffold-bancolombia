package co.com.bancolombia.api.config;

import co.com.bancolombia.api.ExceptionHandler;
import co.com.bancolombia.api.FranchiseHandler;
import co.com.bancolombia.api.RouterRest;
import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.usecase.branch.AddBranchUseCase;
import co.com.bancolombia.usecase.branch.UpdateBranchNameUseCase;
import co.com.bancolombia.usecase.franchise.CreateFranchiseUseCase;
import co.com.bancolombia.usecase.franchise.UpdateFranchiseNameUseCase;
import co.com.bancolombia.usecase.product.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Configuration Tests")
class ConfigTest {

    private WebTestClient webTestClient;

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

    @BeforeEach
    void setUp() {
        ExceptionHandler exceptionHandler = new ExceptionHandler();
        FranchiseHandler franchiseHandler = new FranchiseHandler(
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

        RouterRest routerRest = new RouterRest();

        // Configurar WebTestClient sin filtros adicionales para este test unitario
        webTestClient = WebTestClient
                .bindToRouterFunction(routerRest.routerFunction(franchiseHandler))
                .build();
    }

    @Test
    @DisplayName("Router should handle requests correctly")
    void routerShouldHandleRequestsCorrectly() {
        // Mock the use case to return a valid response
        Franchise franchise = Franchise.builder()
                .id("franchise-1")
                .name("Test Franchise")
                .branches(new ArrayList<>())
                .build();

        when(createFranchiseUseCase.execute(any(Franchise.class)))
                .thenReturn(Mono.just(franchise));

        webTestClient.post()
                .uri("/api/franchises")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"name\":\"Test Franchise\",\"branches\":[]}")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Franchise.class)
                .value(response -> {
                    assert response != null;
                    assert response.getName().equals("Test Franchise");
                });
    }

}