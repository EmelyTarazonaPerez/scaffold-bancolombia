package co.com.bancolombia.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * Router de endpoints reactivos con WebFlux funcional.
 *
 * Las anotaciones @RouterOperations son NECESARIAS para que SpringDoc descubra
 * la documentación en WebFlux funcional. Cada @RouterOperation mapea:
 * - path: La ruta HTTP
 * - method: El verbo HTTP (POST, PUT, GET, DELETE)
 * - beanClass: La clase del Handler
 * - beanMethod: El nombre del método a invocar
 * - operation: La anotación @Operation con documentación completa
 *
 * ¿Por qué es necesario?
 * En @RestController, SpringDoc descubre automáticamente @Operation en métodos.
 * En WebFlux funcional, los métodos están en Handler pero las rutas están en RouterRest.
 * SpringDoc no puede conectarlos automáticamente, así que necesita @RouterOperations
 * para crear el mapeo: "Esta ruta en RouterRest corresponde a este Handler.método"
 */
@Configuration
public class RouterRest {

    @Bean
    @RouterOperations({
            // ===================== POST /api/franchises =====================
            @RouterOperation(
                    path = "/api/franchises",
                    method = RequestMethod.POST,
                    beanClass = FranchiseHandler.class,
                    beanMethod = "createFranchise",
                    operation = @Operation(
                            operationId = "createFranchise",
                            summary = "Create a new franchise",
                            description = "Creates a new franchise with the provided name and optional branches with products",
                            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                    description = "Franchise creation request",
                                    required = true,
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = CreateFranchiseRequest.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(responseCode = "201", description = "Franchise created successfully",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))),
                                    @ApiResponse(responseCode = "400", description = "Invalid request"),
                                    @ApiResponse(responseCode = "503", description = "Service unavailable")
                            }
                    )
            ),

            @RouterOperation(
                    path = "/api/franchises/{franchiseId}",
                    method = RequestMethod.PUT,
                    beanClass = FranchiseHandler.class,
                    beanMethod = "updateFranchiseName",
                    operation = @Operation(
                            operationId = "updateFranchiseName",
                            summary = "Update franchise name",
                            description = "Updates the name of an existing franchise",
                            parameters = {
                                    @Parameter(name = "franchiseId", in = ParameterIn.PATH, required = true,
                                            description = "Franchise ID", example = "550e8400-e29b-41d4-a716-446655440000")
                            },
                            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                    description = "Update franchise name request",
                                    required = true,
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = UpdateFranchiseNameRequest.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Name updated successfully",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))),
                                    @ApiResponse(responseCode = "400", description = "Invalid request"),
                                    @ApiResponse(responseCode = "404", description = "Franchise not found")
                            }
                    )
            ),

            @RouterOperation(
                    path = "/api/franchises/{franchiseId}/branches",
                    method = RequestMethod.POST,
                    beanClass = FranchiseHandler.class,
                    beanMethod = "addBranch",
                    operation = @Operation(
                            operationId = "addBranch",
                            summary = "Add branch to franchise",
                            description = "Adds a new branch to an existing franchise",
                            parameters = {
                                    @Parameter(name = "franchiseId", in = ParameterIn.PATH, required = true,
                                            description = "Franchise ID", example = "550e8400-e29b-41d4-a716-446655440000")
                            },
                            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                    description = "Add branch request",
                                    required = true,
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = AddBranchRequest.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(responseCode = "201", description = "Branch added successfully",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))),
                                    @ApiResponse(responseCode = "400", description = "Invalid request"),
                                    @ApiResponse(responseCode = "404", description = "Franchise not found")
                            }
                    )
            ),

            // ===================== PUT /api/franchises/{franchiseId}/branches/{branchId} =====================
            @RouterOperation(
                    path = "/api/franchises/{franchiseId}/branches/{branchId}",
                    method = RequestMethod.PUT,
                    beanClass = FranchiseHandler.class,
                    beanMethod = "updateBranchName",
                    operation = @Operation(
                            operationId = "updateBranchName",
                            summary = "Update branch name",
                            description = "Updates the name of an existing branch",
                            parameters = {
                                    @Parameter(name = "franchiseId", in = ParameterIn.PATH, required = true, description = "Franchise ID"),
                                    @Parameter(name = "branchId", in = ParameterIn.PATH, required = true, description = "Branch ID")
                            },
                            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                    description = "Update branch name request",
                                    required = true,
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = UpdateBranchNameRequest.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Name updated successfully",
                                            content = @Content(mediaType = "application/json")),
                                    @ApiResponse(responseCode = "400", description = "Invalid request"),
                                    @ApiResponse(responseCode = "404", description = "Franchise or branch not found")
                            }
                    )
            ),

            // ===================== POST /api/franchises/{franchiseId}/branches/{branchId}/products =====================
            @RouterOperation(
                    path = "/api/franchises/{franchiseId}/branches/{branchId}/products",
                    method = RequestMethod.POST,
                    beanClass = FranchiseHandler.class,
                    beanMethod = "addProduct",
                    operation = @Operation(
                            operationId = "addProduct",
                            summary = "Add product to branch",
                            description = "Adds a new product to an existing branch",
                            parameters = {
                                    @Parameter(name = "franchiseId", in = ParameterIn.PATH, required = true, description = "Franchise ID"),
                                    @Parameter(name = "branchId", in = ParameterIn.PATH, required = true, description = "Branch ID")
                            },
                            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                    description = "Add product request",
                                    required = true,
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = AddProductRequest.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(responseCode = "201", description = "Product added successfully",
                                            content = @Content(mediaType = "application/json")),
                                    @ApiResponse(responseCode = "400", description = "Invalid request"),
                                    @ApiResponse(responseCode = "404", description = "Franchise or branch not found")
                            }
                    )
            ),

            // ===================== DELETE /api/franchises/{franchiseId}/branches/{branchId}/products/{productId} =====================
            @RouterOperation(
                    path = "/api/franchises/{franchiseId}/branches/{branchId}/products/{productId}",
                    method = RequestMethod.DELETE,
                    beanClass = FranchiseHandler.class,
                    beanMethod = "deleteProduct",
                    operation = @Operation(
                            operationId = "deleteProduct",
                            summary = "Delete product",
                            description = "Deletes a product from a branch",
                            parameters = {
                                    @Parameter(name = "franchiseId", in = ParameterIn.PATH, required = true, description = "Franchise ID"),
                                    @Parameter(name = "branchId", in = ParameterIn.PATH, required = true, description = "Branch ID"),
                                    @Parameter(name = "productId", in = ParameterIn.PATH, required = true, description = "Product ID")
                            },
                            responses = {
                                    @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
                                    @ApiResponse(responseCode = "400", description = "Invalid request"),
                                    @ApiResponse(responseCode = "404", description = "Product not found")
                            }
                    )
            ),

            // ===================== PUT /api/franchises/{franchiseId}/branches/{branchId}/products/{productId}/stock =====================
            @RouterOperation(
                    path = "/api/franchises/{franchiseId}/branches/{branchId}/products/{productId}/stock",
                    method = RequestMethod.PUT,
                    beanClass = FranchiseHandler.class,
                    beanMethod = "updateProductStock",
                    operation = @Operation(
                            operationId = "updateProductStock",
                            summary = "Update product stock",
                            description = "Updates the stock quantity of a product",
                            parameters = {
                                    @Parameter(name = "franchiseId", in = ParameterIn.PATH, required = true, description = "Franchise ID"),
                                    @Parameter(name = "branchId", in = ParameterIn.PATH, required = true, description = "Branch ID"),
                                    @Parameter(name = "productId", in = ParameterIn.PATH, required = true, description = "Product ID")
                            },
                            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                    description = "Update product stock request",
                                    required = true,
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = UpdateProductStockRequest.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Stock updated successfully",
                                            content = @Content(mediaType = "application/json")),
                                    @ApiResponse(responseCode = "400", description = "Invalid stock value"),
                                    @ApiResponse(responseCode = "404", description = "Product not found")
                            }
                    )
            ),

            // ===================== PUT /api/franchises/{franchiseId}/branches/{branchId}/products/{productId} =====================
            @RouterOperation(
                    path = "/api/franchises/{franchiseId}/branches/{branchId}/products/{productId}",
                    method = RequestMethod.PUT,
                    beanClass = FranchiseHandler.class,
                    beanMethod = "updateProductName",
                    operation = @Operation(
                            operationId = "updateProductName",
                            summary = "Update product name",
                            description = "Updates the name of a product",
                            parameters = {
                                    @Parameter(name = "franchiseId", in = ParameterIn.PATH, required = true, description = "Franchise ID"),
                                    @Parameter(name = "branchId", in = ParameterIn.PATH, required = true, description = "Branch ID"),
                                    @Parameter(name = "productId", in = ParameterIn.PATH, required = true, description = "Product ID")
                            },
                            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                    description = "Update product name request",
                                    required = true,
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = UpdateProductNameRequest.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Name updated successfully",
                                            content = @Content(mediaType = "application/json")),
                                    @ApiResponse(responseCode = "400", description = "Invalid request"),
                                    @ApiResponse(responseCode = "404", description = "Product not found")
                            }
                    )
            ),

            // ===================== GET /api/franchises/{franchiseId}/products/max-stock =====================
            @RouterOperation(
                    path = "/api/franchises/{franchiseId}/products/max-stock",
                    method = RequestMethod.GET,
                    beanClass = FranchiseHandler.class,
                    beanMethod = "getMaxStockProduct",
                    operation = @Operation(
                            operationId = "getMaxStockProduct",
                            summary = "Get product with maximum stock",
                            description = "Gets the product with the highest stock quantity in a franchise",
                            parameters = {
                                    @Parameter(name = "franchiseId", in = ParameterIn.PATH, required = true, description = "Franchise ID")
                            },
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Product obtained successfully",
                                            content = @Content(mediaType = "application/json")),
                                    @ApiResponse(responseCode = "400", description = "Invalid request"),
                                    @ApiResponse(responseCode = "404", description = "Franchise not found")
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> routerFunction(FranchiseHandler handler) {
        return route(GET("/actuator/health"), request ->
                ServerResponse.ok().bodyValue("OK"))
                .andRoute(POST("/api/franchises"), handler::createFranchise)
                .andRoute(PUT("/api/franchises/{franchiseId}"), handler::updateFranchiseName)
                .andRoute(POST("/api/franchises/{franchiseId}/branches"), handler::addBranch)
                .andRoute(PUT("/api/franchises/{franchiseId}/branches/{branchId}"), handler::updateBranchName)
                .andRoute(POST("/api/franchises/{franchiseId}/branches/{branchId}/products"), handler::addProduct)
                .andRoute(DELETE("/api/franchises/{franchiseId}/branches/{branchId}/products/{productId}"), handler::deleteProduct)
                .andRoute(PUT("/api/franchises/{franchiseId}/branches/{branchId}/products/{productId}/stock"), handler::updateProductStock)
                .andRoute(PUT("/api/franchises/{franchiseId}/branches/{branchId}/products/{productId}"), handler::updateProductName)
                .andRoute(GET("/api/franchises/{franchiseId}/products/max-stock"), handler::getMaxStockProduct);
    }
}