package co.com.bancolombia.api;

import co.com.bancolombia.usecase.franchise.CreateFranchiseUseCase;
import co.com.bancolombia.usecase.franchise.UpdateFranchiseNameUseCase;
import co.com.bancolombia.usecase.branch.AddBranchUseCase;
import co.com.bancolombia.usecase.branch.UpdateBranchNameUseCase;
import co.com.bancolombia.usecase.product.AddProductUseCase;
import co.com.bancolombia.usecase.product.DeleteProductUseCase;
import co.com.bancolombia.usecase.product.UpdateProductStockUseCase;
import co.com.bancolombia.usecase.product.UpdateProductNameUseCase;
import co.com.bancolombia.usecase.product.GetMaxStockProductUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

@Component
@RequiredArgsConstructor
@Tag(name = "Franchise Management", description = "APIs for managing franchises, branches and products")
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
    private final ExceptionHandler exceptionHandler;

    @Operation(summary = "Create a new franchise",
            description = "Creates a new franchise with the provided name")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Franchise created successfully",
                    content = @Content(schema = @Schema(implementation = Object.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    public Mono<ServerResponse> createFranchise(ServerRequest request) {
        return request.bodyToMono(CreateFranchiseRequest.class)
                .flatMap(dto ->
                        createFranchiseUseCase.execute(dto.toFranchise())
                )
                .flatMap(franchise ->
                        ServerResponse.ok().bodyValue(franchise)
                )
                .onErrorResume(exceptionHandler::handleException);
    }

    @Operation(summary = "Update franchise name",
            description = "Updates the name of an existing franchise")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Name updated successfully"),
            @ApiResponse(responseCode = "404", description = "Franchise not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    public Mono<ServerResponse> updateFranchiseName(
            @Parameter(description = "Franchise ID") ServerRequest request) {
        String franchiseId = request.pathVariable("franchiseId");
        return request.bodyToMono(UpdateFranchiseNameRequest.class)
                .flatMap(dto ->
                        updateFranchiseNameUseCase.execute(franchiseId, dto.newName())
                )
                .flatMap(franchise ->
                        ServerResponse.ok().bodyValue(franchise)
                )
                .onErrorResume(exceptionHandler::handleException);
    }

    @Operation(summary = "Add branch to franchise",
            description = "Adds a new branch to an existing franchise")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Branch added successfully"),
            @ApiResponse(responseCode = "404", description = "Franchise not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    public Mono<ServerResponse> addBranch(ServerRequest request) {
        String franchiseId = request.pathVariable("franchiseId");
        return request.bodyToMono(AddBranchRequest.class)
                .flatMap(dto ->
                        addBranchUseCase.execute(franchiseId, dto.toBranch())
                )
                .flatMap(branch ->
                        ServerResponse.ok().bodyValue(branch)
                )
                .onErrorResume(exceptionHandler::handleException);
    }

    @Operation(summary = "Update branch name",
            description = "Updates the name of an existing branch")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Name updated successfully"),
            @ApiResponse(responseCode = "404", description = "Franchise or branch not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    public Mono<ServerResponse> updateBranchName(ServerRequest request) {
        String franchiseId = request.pathVariable("franchiseId");
        String branchId = request.pathVariable("branchId");
        return request.bodyToMono(UpdateBranchNameRequest.class)
                .flatMap(dto ->
                        updateBranchNameUseCase.execute(franchiseId, branchId, dto.newName())
                )
                .flatMap(branch ->
                        ServerResponse.ok().bodyValue(branch)
                )
                .onErrorResume(exceptionHandler::handleException);
    }

    @Operation(summary = "Add product to branch",
            description = "Adds a new product to an existing branch")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product added successfully"),
            @ApiResponse(responseCode = "404", description = "Franchise or branch not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    public Mono<ServerResponse> addProduct(ServerRequest request) {
        String franchiseId = request.pathVariable("franchiseId");
        String branchId = request.pathVariable("branchId");
        return request.bodyToMono(AddProductRequest.class)
                .flatMap(dto ->
                        addProductUseCase.execute(franchiseId, branchId, dto.toProduct())
                )
                .flatMap(product ->
                        ServerResponse.ok().bodyValue(product)
                )
                .onErrorResume(exceptionHandler::handleException);
    }

    @Operation(summary = "Delete product",
            description = "Deletes a product from a branch")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    public Mono<ServerResponse> deleteProduct(ServerRequest request) {
        String franchiseId = request.pathVariable("franchiseId");
        String branchId = request.pathVariable("branchId");
        String productId = request.pathVariable("productId");
        return deleteProductUseCase.execute(franchiseId, branchId, productId)
                .flatMap(v ->
                        ServerResponse.noContent().build()
                )
                .onErrorResume(exceptionHandler::handleException);
    }

    @Operation(summary = "Update product stock",
            description = "Updates the stock quantity of a product")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Stock updated successfully"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "400", description = "Invalid stock value or invalid request")
    })
    public Mono<ServerResponse> updateProductStock(ServerRequest request) {
        String franchiseId = request.pathVariable("franchiseId");
        String branchId = request.pathVariable("branchId");
        String productId = request.pathVariable("productId");
        return request.bodyToMono(UpdateProductStockRequest.class)
                .flatMap(dto ->
                        updateProductStockUseCase.execute(franchiseId, branchId, productId, dto.newStock())
                )
                .flatMap(product ->
                        ServerResponse.ok().bodyValue(product)
                )
                .onErrorResume(exceptionHandler::handleException);
    }

    @Operation(summary = "Update product name",
            description = "Updates the name of a product")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Name updated successfully"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    public Mono<ServerResponse> updateProductName(ServerRequest request) {
        String franchiseId = request.pathVariable("franchiseId");
        String branchId = request.pathVariable("branchId");
        String productId = request.pathVariable("productId");
        return request.bodyToMono(UpdateProductNameRequest.class)
                .flatMap(dto ->
                        updateProductNameUseCase.execute(franchiseId, branchId, productId, dto.newName())
                )
                .flatMap(product ->
                        ServerResponse.ok().bodyValue(product)
                )
                .onErrorResume(exceptionHandler::handleException);
    }

    @Operation(summary = "Get product with maximum stock",
            description = "Gets the product with the highest stock quantity in a franchise")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Product obtained successfully"),
        @ApiResponse(responseCode = "404", description = "Franchise not found"),
        @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    public Mono<ServerResponse> getMaxStockProduct(ServerRequest request) {
        String franchiseId = request.pathVariable("franchiseId");
        return getMaxStockProductUseCase.execute(franchiseId)
                .collectList()
                .flatMap(product -> ServerResponse.ok().bodyValue(product))
                .onErrorResume(exceptionHandler::handleException);
    }
}
