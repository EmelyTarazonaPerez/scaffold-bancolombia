package co.com.bancolombia.api;

import co.com.bancolombia.api.dto.resquest.*;
import co.com.bancolombia.api.dto.response.FranchiseResponse;
import co.com.bancolombia.api.dto.response.BranchResponse;
import co.com.bancolombia.api.dto.response.ProductResponse;
import co.com.bancolombia.usecase.franchise.CreateFranchiseUseCase;
import co.com.bancolombia.usecase.franchise.UpdateFranchiseNameUseCase;
import co.com.bancolombia.usecase.branch.AddBranchUseCase;
import co.com.bancolombia.usecase.branch.UpdateBranchNameUseCase;
import co.com.bancolombia.usecase.product.AddProductUseCase;
import co.com.bancolombia.usecase.product.DeleteProductUseCase;
import co.com.bancolombia.usecase.product.UpdateProductStockUseCase;
import co.com.bancolombia.usecase.product.UpdateProductNameUseCase;
import co.com.bancolombia.usecase.product.GetMaxStockProductUseCase;
import co.com.bancolombia.api.validator.RequestValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import static co.com.bancolombia.api.utils.Constans.*;

/**
 * FranchiseHandler contiene la lógica de procesamiento de requests para los endpoints de Franchise.
 *
 * NOTA IMPORTANTE: La documentación OpenAPI/Swagger para estos métodos se define en RouterRest.java
 * usando @RouterOperations y @RouterOperation. Esto mantiene la aplicación WebFlux funcional
 * con una única fuente de verdad para la documentación de API.
 *
 * Este handler se enfoca exclusivamente en:
 * - Validación de requests
 * - Orquestación de use cases
 * - Transformación de respuestas
 * - Manejo de excepciones
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FranchiseHandler {

    private final CreateFranchiseUseCase createFranchiseUseCase;
    private final UpdateFranchiseNameUseCase updateFranchiseNameUseCase;
    private final AddBranchUseCase addBranchUseCase;
    private final UpdateBranchNameUseCase updateBranchNameUseCase;
    private final AddProductUseCase addProductUseCase;
    private final DeleteProductUseCase deleteProductUseCase;
    private final UpdateProductStockUseCase updateProductStockUseCase;
    private final UpdateProductNameUseCase updateProductNameUseCase;
    private final GetMaxStockProductUseCase getMaxStockProductUseCase;

    private final RequestValidator requestValidator;
    private final ExceptionHandler exceptionHandler;

    public Mono<ServerResponse> createFranchise(ServerRequest request) {
        log.info("Received request to create franchise");
        return request.bodyToMono(CreateFranchiseRequest.class)
                .flatMap(requestValidator::validate)
                .flatMap(dto -> createFranchiseUseCase.execute(dto.toFranchise()))
                .doOnNext(model -> log.info("Franchise created successfully with ID: {}", model.getId()))
                .map(FranchiseResponse::fromDomain)
                .flatMap(franchise -> ServerResponse.ok().bodyValue(franchise))
                .onErrorResume(exceptionHandler::handleException);
    }

    public Mono<ServerResponse> updateFranchiseName(ServerRequest request) {
        String franchiseId = request.pathVariable(PARAM_FRANCHISE_ID);
        log.info("Received request to update franchise name for ID: {}", franchiseId);
        return request.bodyToMono(UpdateFranchiseNameRequest.class)
                .flatMap(requestValidator::validate)
                .flatMap(dto -> updateFranchiseNameUseCase.execute(franchiseId, dto.newName()))
                .doOnNext(franchise -> log.info("Franchise name updated successfully"))
                .map(FranchiseResponse::fromDomain)
                .flatMap(franchise ->
                        ServerResponse.ok().bodyValue(franchise)
                )
                .onErrorResume(exceptionHandler::handleException);
    }

    public Mono<ServerResponse> addBranch(ServerRequest request) {
        String franchiseId = request.pathVariable(PARAM_FRANCHISE_ID);
        log.info("Received request to add branch to franchise ID: {}", franchiseId);
        return request.bodyToMono(AddBranchRequest.class)
                .flatMap(requestValidator::validate)
                .flatMap(dto -> addBranchUseCase.execute(franchiseId, dto.toBranch()))
                .doOnNext(branch -> log.info("Branch added successfully"))
                .map(BranchResponse::fromDomain)
                .flatMap(branch ->
                        ServerResponse.ok().bodyValue(branch)
                )
                .onErrorResume(exceptionHandler::handleException);
    }

    public Mono<ServerResponse> updateBranchName(ServerRequest request) {
        String franchiseId = request.pathVariable(PARAM_FRANCHISE_ID);
        String branchId = request.pathVariable(PARAM_BRANCH_ID);
        log.info("Received request to update branch name - FranchiseID: {}, BranchID: {}", franchiseId, branchId);
        return request.bodyToMono(UpdateBranchNameRequest.class)
                .flatMap(requestValidator::validate)
                .flatMap(dto ->
                        updateBranchNameUseCase.execute(franchiseId, branchId, dto.newName())
                )
                .doOnNext(branch -> log.info("Branch name updated successfully"))
                .map(BranchResponse::fromDomain)
                .flatMap(branch ->
                        ServerResponse.ok().bodyValue(branch)
                )
                .onErrorResume(exceptionHandler::handleException);
    }

    public Mono<ServerResponse> addProduct(ServerRequest request) {
        String franchiseId = request.pathVariable(PARAM_FRANCHISE_ID);
        String branchId = request.pathVariable(PARAM_BRANCH_ID);
        log.info("Received request to add product - FranchiseID: {}, BranchID: {}", franchiseId, branchId);
        return request.bodyToMono(AddProductRequest.class)
                .flatMap(requestValidator::validate)
                .flatMap(dto ->
                        addProductUseCase.execute(franchiseId, branchId, dto.toProduct())
                )
                .doOnNext(product -> log.info("Product added successfully"))
                .map(ProductResponse::fromDomain)
                .flatMap(product ->
                        ServerResponse.ok().bodyValue(product)
                )
                .onErrorResume(exceptionHandler::handleException);
    }

    public Mono<ServerResponse> deleteProduct(ServerRequest request) {
        String franchiseId = request.pathVariable(PARAM_FRANCHISE_ID);
        String branchId = request.pathVariable(PARAM_BRANCH_ID);
        String productId = request.pathVariable(PARAM_PRODUCT_ID);
        log.info("Received request to delete product");
        return deleteProductUseCase.execute(franchiseId, branchId, productId)
                .doOnSuccess(v -> log.info("Product deleted successfully"))
                .then(ServerResponse.noContent().build())
                .onErrorResume(exceptionHandler::handleException);
    }

    public Mono<ServerResponse> updateProductStock(ServerRequest request) {
        String franchiseId = request.pathVariable(PARAM_FRANCHISE_ID);
        String branchId = request.pathVariable(PARAM_BRANCH_ID);
        String productId = request.pathVariable(PARAM_PRODUCT_ID);
        log.info("Received request to update product stock ");
        return request.bodyToMono(UpdateProductStockRequest.class)
                .flatMap(requestValidator::validate)
                .flatMap(dto ->
                        updateProductStockUseCase.execute(franchiseId, branchId, productId, dto.newStock())
                )
                .doOnNext(product -> log.info("Product stock updated successfully"))
                .map(ProductResponse::fromDomain)
                .flatMap(product ->
                        ServerResponse.ok().bodyValue(product)
                )
                .onErrorResume(exceptionHandler::handleException);
    }

    public Mono<ServerResponse> updateProductName(ServerRequest request) {
        String franchiseId = request.pathVariable(PARAM_FRANCHISE_ID);
        String branchId = request.pathVariable(PARAM_BRANCH_ID);
        String productId = request.pathVariable(PARAM_PRODUCT_ID);
        log.info("Received request to update product name ");
        return request.bodyToMono(UpdateProductNameRequest.class)
                .flatMap(requestValidator::validate)
                .flatMap(dto ->
                        updateProductNameUseCase.execute(franchiseId, branchId, productId, dto.newName())
                )
                .doOnNext(product -> log.info("Product name updated successfully"))
                .map(ProductResponse::fromDomain)
                .flatMap(product ->
                        ServerResponse.ok().bodyValue(product)
                )
                .onErrorResume(exceptionHandler::handleException);
    }

    public Mono<ServerResponse> getMaxStockProduct(ServerRequest request) {
        String franchiseId = request.pathVariable(PARAM_FRANCHISE_ID);
        log.info("Received request to get max stock product - FranchiseID: {}", franchiseId);
        return getMaxStockProductUseCase.execute(franchiseId)
                .map(FranchiseResponse::fromDomain)
                .flatMap(resp -> ServerResponse.ok().bodyValue(resp))
                .onErrorResume(exceptionHandler::handleException);
    }
}
