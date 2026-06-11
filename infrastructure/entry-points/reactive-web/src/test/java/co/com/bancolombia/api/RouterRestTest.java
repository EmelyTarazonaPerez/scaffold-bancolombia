package co.com.bancolombia.api;

import co.com.bancolombia.api.validator.RequestValidator;
import co.com.bancolombia.model.branch.Branch;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Router REST Integration Tests")
class RouterRestTest {

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

    @Mock
    private RequestValidator requestValidator;

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
                requestValidator,
                exceptionHandler
        );

        RouterRest routerRest = new RouterRest();
        webTestClient = WebTestClient
                .bindToRouterFunction(routerRest.routerFunction(franchiseHandler))
                .build();
    }

    private void configureRequestValidatorMock() {
        when(requestValidator.validate(any())).thenAnswer(inv -> Mono.just(inv.getArgument(0)));
    }

    @Test
    @DisplayName("Should create franchise via POST endpoint")
    void testCreateFranchise() {
        configureRequestValidatorMock();

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
                    assertThat(response).isNotNull();
                    assertThat(response.getName()).isEqualTo("Test Franchise");
                });
    }

    @Test
    @DisplayName("Should update franchise name via PUT endpoint")
    void testUpdateFranchiseName() {
        configureRequestValidatorMock();

        Franchise franchise = Franchise.builder()
                .id("franchise-1")
                .name("Updated Franchise")
                .branches(new ArrayList<>())
                .build();

        when(updateFranchiseNameUseCase.execute(anyString(), anyString()))
                .thenReturn(Mono.just(franchise));

        webTestClient.put()
                .uri("/api/franchises/franchise-1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"newName\":\"Updated Franchise\"}")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Franchise.class)
                .value(response -> {
                    assertThat(response).isNotNull();
                    assertThat(response.getName()).isEqualTo("Updated Franchise");
                });
    }

    @Test
    @DisplayName("Should get max stock product via GET endpoint")
    void testGetMaxStockProduct() {
        Franchise franchise = Franchise.builder()
                .id("franchise-1")
                .name("Test Franchise")
                .branches(List.of(Branch.builder()
                        .id("branch-1")
                        .name("Branch 1")
                        .build()))
                .build();

        when(getMaxStockProductUseCase.execute(anyString()))
                .thenReturn(Mono.just(franchise));

        webTestClient.get()
                .uri("/api/franchises/franchise-1/products/max-stock")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Franchise.class)
                .value(response -> {
                    assertThat(response.getId()).isEqualTo("franchise-1");
                    assertThat(response.getBranches()).isNotEmpty();
                });
    }

}
