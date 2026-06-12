package co.com.bancolombia.api;

import co.com.bancolombia.api.dto.resquest.*;
import co.com.bancolombia.api.utils.Constans;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/api/franchises",
                    method = RequestMethod.POST,
                    beanClass = FranchiseHandler.class,
                    beanMethod = "createFranchise",
                    operation = @Operation(
                            operationId = "createFranchise",
                            summary = Constans.CREATE_FRANCHISE_SUMMARY,
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
                                    @ApiResponse(responseCode = "201", description = Constans.FRANCHISE_CREATED_SUCCESS,
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))),
                                    @ApiResponse(responseCode = "400", description = Constans.INVALID_REQUEST),
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
                            summary = Constans.UPDATE_FRANCHISE_NAME_SUMMARY,
                            description = Constans.UPDATE_FRANCHISE_NAME_DESCRIPTION,
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
                                    @ApiResponse(responseCode = "200", description = Constans.NAME_UPDATED_SUCCESSFULLY,
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))),
                                    @ApiResponse(responseCode = "400", description = Constans.INVALID_REQUEST),
                                    @ApiResponse(responseCode = "404", description = Constans.FRANCHISE_NOT_FOUND)
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
                            summary = Constans.ADD_BRANCH_SUMMARY,
                            description = Constans.ADD_BRANCH_DESCRIPTION,
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
                                    @ApiResponse(responseCode = "201", description = Constans.BRANCH_ADDED_SUCCESS,
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))),
                                    @ApiResponse(responseCode = "400", description = Constans.INVALID_REQUEST),
                                    @ApiResponse(responseCode = "404", description = Constans.FRANCHISE_NOT_FOUND)
                            }
                    )
            ),

            @RouterOperation(
                    path = "/api/franchises/{franchiseId}/branches/{branchId}",
                    method = RequestMethod.PUT,
                    beanClass = FranchiseHandler.class,
                    beanMethod = "updateBranchName",
                    operation = @Operation(
                            operationId = "updateBranchName",
                            summary = Constans.UPDATE_BRANCH_NAME_SUMMARY,
                            description = Constans.UPDATE_BRANCH_NAME_DESCRIPTION,
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
                                    @ApiResponse(responseCode = "200", description = Constans.NAME_UPDATED_SUCCESSFULLY,
                                            content = @Content(mediaType = "application/json")),
                                    @ApiResponse(responseCode = "400", description = Constans.INVALID_REQUEST),
                                    @ApiResponse(responseCode = "404", description = Constans.FRANCHISE_OR_BRANCH_NOT_FOUND)
                            }
                    )
            ),

            @RouterOperation(
                    path = "/api/franchises/{franchiseId}/branches/{branchId}/products",
                    method = RequestMethod.POST,
                    beanClass = FranchiseHandler.class,
                    beanMethod = "addProduct",
                    operation = @Operation(
                            operationId = "addProduct",
                            summary = Constans.ADD_PRODUCT_SUMMARY,
                            description = Constans.ADD_PRODUCT_DESCRIPTION,
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
                                    @ApiResponse(responseCode = "201", description = Constans.PRODUCT_ADDED_SUCCESS,
                                            content = @Content(mediaType = "application/json")),
                                    @ApiResponse(responseCode = "400", description = Constans.INVALID_REQUEST),
                                    @ApiResponse(responseCode = "404", description = Constans.FRANCHISE_OR_BRANCH_NOT_FOUND)
                            }
                    )
            ),

            @RouterOperation(
                    path = "/api/franchises/{franchiseId}/branches/{branchId}/products/{productId}",
                    method = RequestMethod.DELETE,
                    beanClass = FranchiseHandler.class,
                    beanMethod = "deleteProduct",
                    operation = @Operation(
                            operationId = "deleteProduct",
                            summary = Constans.DELETE_PRODUCT_SUMMARY,
                            description = Constans.DELETE_PRODUCT_DESCRIPTION,
                            parameters = {
                                    @Parameter(name = "franchiseId", in = ParameterIn.PATH, required = true, description = "Franchise ID"),
                                    @Parameter(name = "branchId", in = ParameterIn.PATH, required = true, description = "Branch ID"),
                                    @Parameter(name = "productId", in = ParameterIn.PATH, required = true, description = "Product ID")
                            },
                            responses = {
                                    @ApiResponse(responseCode = "204", description = Constans.PRODUCT_DELETED_SUCCESS),
                                    @ApiResponse(responseCode = "400", description = Constans.INVALID_REQUEST),
                                    @ApiResponse(responseCode = "404", description = Constans.RESOURCE_NOT_FOUND)
                            }
                    )
            ),

            @RouterOperation(
                    path = "/api/franchises/{franchiseId}/branches/{branchId}/products/{productId}/stock",
                    method = RequestMethod.PUT,
                    beanClass = FranchiseHandler.class,
                    beanMethod = "updateProductStock",
                    operation = @Operation(
                            operationId = "updateProductStock",
                            summary = Constans.UPDATE_PRODUCT_STOCK_SUMMARY,
                            description = Constans.UPDATE_PRODUCT_STOCK_DESCRIPTION,
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
                                    @ApiResponse(responseCode = "200", description = Constans.STOCK_UPDATED_SUCCESSFULLY,
                                            content = @Content(mediaType = "application/json")),
                                    @ApiResponse(responseCode = "400", description = Constans.INVALID_STOCK_VALUE),
                                    @ApiResponse(responseCode = "404", description = Constans.RESOURCE_NOT_FOUND)
                            }
                    )
            ),

            @RouterOperation(
                    path = "/api/franchises/{franchiseId}/branches/{branchId}/products/{productId}",
                    method = RequestMethod.PUT,
                    beanClass = FranchiseHandler.class,
                    beanMethod = "updateProductName",
                    operation = @Operation(
                            operationId = "updateProductName",
                            summary = Constans.UPDATE_PRODUCT_NAME_SUMMARY,
                            description = Constans.UPDATE_PRODUCT_NAME_DESCRIPTION,
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
                                    @ApiResponse(responseCode = "200", description = Constans.NAME_UPDATED_SUCCESSFULLY,
                                            content = @Content(mediaType = "application/json")),
                                    @ApiResponse(responseCode = "400", description = Constans.INVALID_REQUEST),
                                    @ApiResponse(responseCode = "404", description = Constans.RESOURCE_NOT_FOUND)
                            }
                    )
            ),

            @RouterOperation(
                    path = "/api/franchises/{franchiseId}/products/max-stock",
                    method = RequestMethod.GET,
                    beanClass = FranchiseHandler.class,
                    beanMethod = "getMaxStockProduct",
                    operation = @Operation(
                            operationId = "getMaxStockProduct",
                            summary = Constans.GET_MAX_STOCK_PRODUCT_SUMMARY,
                            description = Constans.GET_MAX_STOCK_PRODUCT_DESCRIPTION,
                            parameters = {
                                    @Parameter(name = "franchiseId", in = ParameterIn.PATH, required = true, description = "Franchise ID")
                            },
                            responses = {
                                    @ApiResponse(responseCode = "200", description = Constans.PRODUCT_OBTAINED_SUCCESS,
                                            content = @Content(mediaType = "application/json")),
                                    @ApiResponse(responseCode = "400", description = Constans.INVALID_REQUEST),
                                    @ApiResponse(responseCode = "404", description = Constans.FRANCHISE_NOT_FOUND)
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