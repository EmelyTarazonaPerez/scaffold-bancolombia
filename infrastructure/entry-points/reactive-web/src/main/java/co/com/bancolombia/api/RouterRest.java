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
                    path = Constans.API_FRANCHISES,
                    method = RequestMethod.POST,
                    beanClass = FranchiseHandler.class,
                    beanMethod = "createFranchise",
                    operation = @Operation(
                            operationId = "createFranchise",
                            summary = Constans.CREATE_FRANCHISE_SUMMARY,
                            description = Constans.CREATE_FRANCHISE_FULL_DESCRIPTION,
                            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                    description = Constans.FRANCHISE_CREATION_REQUEST_DESC,
                                    required = true,
                                    content = @Content(
                                            mediaType = Constans.MEDIA_TYPE_JSON,
                                            schema = @Schema(implementation = CreateFranchiseRequest.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(responseCode = Constans.RESPONSE_CODE_200, description = Constans.FRANCHISE_CREATED_SUCCESS,
                                            content = @Content(mediaType = Constans.MEDIA_TYPE_JSON, schema = @Schema(implementation = Object.class))),
                                    @ApiResponse(responseCode = Constans.RESPONSE_CODE_400, description = Constans.INVALID_REQUEST),
                                    @ApiResponse(responseCode = Constans.RESPONSE_CODE_503, description = Constans.SERVICE_UNAVAILABLE)
                            }
                    )
            ),

            @RouterOperation(
                    path = Constans.API_FRANCHISES_ID,
                    method = RequestMethod.PUT,
                    beanClass = FranchiseHandler.class,
                    beanMethod = "updateFranchiseName",
                    operation = @Operation(
                            operationId = "updateFranchiseName",
                            summary = Constans.UPDATE_FRANCHISE_NAME_SUMMARY,
                            description = Constans.UPDATE_FRANCHISE_NAME_DESCRIPTION,
                            parameters = {
                                    @Parameter(name = Constans.PARAM_FRANCHISE_ID, in = ParameterIn.PATH, required = true,
                                            description = Constans.PARAM_DESC_FRANCHISE_ID, example = Constans.PARAM_DESC_FRANCHISE_ID_EXAMPLE)
                            },
                            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                    description = Constans.UPDATE_FRANCHISE_NAME_REQUEST_DESC,
                                    required = true,
                                    content = @Content(
                                            mediaType = Constans.MEDIA_TYPE_JSON,
                                            schema = @Schema(implementation = UpdateFranchiseNameRequest.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(responseCode = Constans.RESPONSE_CODE_200, description = Constans.NAME_UPDATED_SUCCESSFULLY,
                                            content = @Content(mediaType = Constans.MEDIA_TYPE_JSON, schema = @Schema(implementation = CreateFranchiseRequest.class))),
                                    @ApiResponse(responseCode = Constans.RESPONSE_CODE_400, description = Constans.INVALID_REQUEST),
                                    @ApiResponse(responseCode = Constans.RESPONSE_CODE_404, description = Constans.FRANCHISE_NOT_FOUND)
                            }
                    )
            ),

            @RouterOperation(
                    path = Constans.API_FRANCHISES_BRANCHES,
                    method = RequestMethod.POST,
                    beanClass = FranchiseHandler.class,
                    beanMethod = "addBranch",
                    operation = @Operation(
                            operationId = "addBranch",
                            summary = Constans.ADD_BRANCH_SUMMARY,
                            description = Constans.ADD_BRANCH_DESCRIPTION,
                            parameters = {
                                    @Parameter(name = Constans.PARAM_FRANCHISE_ID, in = ParameterIn.PATH, required = true,
                                            description = Constans.PARAM_DESC_FRANCHISE_ID, example = Constans.PARAM_DESC_FRANCHISE_ID_EXAMPLE)
                            },
                            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                    description = Constans.ADD_BRANCH_REQUEST_DESC,
                                    required = true,
                                    content = @Content(
                                            mediaType = Constans.MEDIA_TYPE_JSON,
                                            schema = @Schema(implementation = AddBranchRequest.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(responseCode = Constans.RESPONSE_CODE_201, description = Constans.BRANCH_ADDED_SUCCESS,
                                            content = @Content(mediaType = Constans.MEDIA_TYPE_JSON, schema = @Schema(implementation = Object.class))),
                                    @ApiResponse(responseCode = Constans.RESPONSE_CODE_400, description = Constans.INVALID_REQUEST),
                                    @ApiResponse(responseCode = Constans.RESPONSE_CODE_404, description = Constans.FRANCHISE_NOT_FOUND)
                            }
                    )
            ),

            @RouterOperation(
                    path = Constans.API_FRANCHISES_BRANCHES_ID,
                    method = RequestMethod.PUT,
                    beanClass = FranchiseHandler.class,
                    beanMethod = "updateBranchName",
                    operation = @Operation(
                            operationId = "updateBranchName",
                            summary = Constans.UPDATE_BRANCH_NAME_SUMMARY,
                            description = Constans.UPDATE_BRANCH_NAME_DESCRIPTION,
                            parameters = {
                                    @Parameter(name = Constans.PARAM_FRANCHISE_ID, in = ParameterIn.PATH, required = true, description = Constans.PARAM_DESC_FRANCHISE_ID),
                                    @Parameter(name = Constans.PARAM_BRANCH_ID, in = ParameterIn.PATH, required = true, description = Constans.PARAM_DESC_BRANCH_ID)
                            },
                            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                    description = Constans.UPDATE_BRANCH_NAME_REQUEST_DESC,
                                    required = true,
                                    content = @Content(
                                            mediaType = Constans.MEDIA_TYPE_JSON,
                                            schema = @Schema(implementation = UpdateBranchNameRequest.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(responseCode = Constans.RESPONSE_CODE_200, description = Constans.NAME_UPDATED_SUCCESSFULLY,
                                            content = @Content(mediaType = Constans.MEDIA_TYPE_JSON)),
                                    @ApiResponse(responseCode = Constans.RESPONSE_CODE_400, description = Constans.INVALID_REQUEST),
                                    @ApiResponse(responseCode = Constans.RESPONSE_CODE_404, description = Constans.FRANCHISE_OR_BRANCH_NOT_FOUND)
                            }
                    )
            ),

            @RouterOperation(
                    path = Constans.API_FRANCHISES_BRANCHES_PRODUCTS,
                    method = RequestMethod.POST,
                    beanClass = FranchiseHandler.class,
                    beanMethod = "addProduct",
                    operation = @Operation(
                            operationId = "addProduct",
                            summary = Constans.ADD_PRODUCT_SUMMARY,
                            description = Constans.ADD_PRODUCT_DESCRIPTION,
                            parameters = {
                                    @Parameter(name = Constans.PARAM_FRANCHISE_ID, in = ParameterIn.PATH, required = true, description = Constans.PARAM_DESC_FRANCHISE_ID),
                                    @Parameter(name = Constans.PARAM_BRANCH_ID, in = ParameterIn.PATH, required = true, description = Constans.PARAM_DESC_BRANCH_ID)
                            },
                            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                    description = Constans.ADD_PRODUCT_REQUEST_DESC,
                                    required = true,
                                    content = @Content(
                                            mediaType = Constans.MEDIA_TYPE_JSON,
                                            schema = @Schema(implementation = AddProductRequest.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(responseCode = Constans.RESPONSE_CODE_201, description = Constans.PRODUCT_ADDED_SUCCESS,
                                            content = @Content(mediaType = Constans.MEDIA_TYPE_JSON)),
                                    @ApiResponse(responseCode = Constans.RESPONSE_CODE_400, description = Constans.INVALID_REQUEST),
                                    @ApiResponse(responseCode = Constans.RESPONSE_CODE_404, description = Constans.FRANCHISE_OR_BRANCH_NOT_FOUND)
                            }
                    )
            ),

            @RouterOperation(
                    path = Constans.API_FRANCHISES_BRANCHES_PRODUCTS_ID,
                    method = RequestMethod.DELETE,
                    beanClass = FranchiseHandler.class,
                    beanMethod = "deleteProduct",
                    operation = @Operation(
                            operationId = "deleteProduct",
                            summary = Constans.DELETE_PRODUCT_SUMMARY,
                            description = Constans.DELETE_PRODUCT_DESCRIPTION,
                            parameters = {
                                    @Parameter(name = Constans.PARAM_FRANCHISE_ID, in = ParameterIn.PATH, required = true, description = Constans.PARAM_DESC_FRANCHISE_ID),
                                    @Parameter(name = Constans.PARAM_BRANCH_ID, in = ParameterIn.PATH, required = true, description = Constans.PARAM_DESC_BRANCH_ID),
                                    @Parameter(name = Constans.PARAM_PRODUCT_ID, in = ParameterIn.PATH, required = true, description = Constans.PARAM_DESC_PRODUCT_ID)
                            },
                            responses = {
                                    @ApiResponse(responseCode = Constans.RESPONSE_CODE_204, description = Constans.PRODUCT_DELETED_SUCCESS),
                                    @ApiResponse(responseCode = Constans.RESPONSE_CODE_400, description = Constans.INVALID_REQUEST),
                                    @ApiResponse(responseCode = Constans.RESPONSE_CODE_404, description = Constans.PRODUCT_NOT_FOUND)
                            }
                    )
            ),

            @RouterOperation(
                    path = Constans.API_FRANCHISES_BRANCHES_PRODUCTS_STOCK,
                    method = RequestMethod.PUT,
                    beanClass = FranchiseHandler.class,
                    beanMethod = "updateProductStock",
                    operation = @Operation(
                            operationId = "updateProductStock",
                            summary = Constans.UPDATE_PRODUCT_STOCK_SUMMARY,
                            description = Constans.UPDATE_PRODUCT_STOCK_DESCRIPTION,
                            parameters = {
                                    @Parameter(name = Constans.PARAM_FRANCHISE_ID, in = ParameterIn.PATH, required = true, description = Constans.PARAM_DESC_FRANCHISE_ID),
                                    @Parameter(name = Constans.PARAM_BRANCH_ID, in = ParameterIn.PATH, required = true, description = Constans.PARAM_DESC_BRANCH_ID),
                                    @Parameter(name = Constans.PARAM_PRODUCT_ID, in = ParameterIn.PATH, required = true, description = Constans.PARAM_DESC_PRODUCT_ID)
                            },
                            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                    description = Constans.UPDATE_PRODUCT_STOCK_REQUEST_DESC,
                                    required = true,
                                    content = @Content(
                                            mediaType = Constans.MEDIA_TYPE_JSON,
                                            schema = @Schema(implementation = UpdateProductStockRequest.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(responseCode = Constans.RESPONSE_CODE_200, description = Constans.STOCK_UPDATED_SUCCESSFULLY,
                                            content = @Content(mediaType = Constans.MEDIA_TYPE_JSON)),
                                    @ApiResponse(responseCode = Constans.RESPONSE_CODE_400, description = Constans.INVALID_STOCK_VALUE_ONLY),
                                    @ApiResponse(responseCode = Constans.RESPONSE_CODE_404, description = Constans.PRODUCT_NOT_FOUND)
                            }
                    )
            ),

            @RouterOperation(
                    path = Constans.API_FRANCHISES_BRANCHES_PRODUCTS_ID,
                    method = RequestMethod.PUT,
                    beanClass = FranchiseHandler.class,
                    beanMethod = "updateProductName",
                    operation = @Operation(
                            operationId = "updateProductName",
                            summary = Constans.UPDATE_PRODUCT_NAME_SUMMARY,
                            description = Constans.UPDATE_PRODUCT_NAME_DESCRIPTION,
                            parameters = {
                                    @Parameter(name = Constans.PARAM_FRANCHISE_ID, in = ParameterIn.PATH, required = true, description = Constans.PARAM_DESC_FRANCHISE_ID),
                                    @Parameter(name = Constans.PARAM_BRANCH_ID, in = ParameterIn.PATH, required = true, description = Constans.PARAM_DESC_BRANCH_ID),
                                    @Parameter(name = Constans.PARAM_PRODUCT_ID, in = ParameterIn.PATH, required = true, description = Constans.PARAM_DESC_PRODUCT_ID)
                            },
                            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                    description = Constans.UPDATE_PRODUCT_NAME_REQUEST_DESC,
                                    required = true,
                                    content = @Content(
                                            mediaType = Constans.MEDIA_TYPE_JSON,
                                            schema = @Schema(implementation = UpdateProductNameRequest.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(responseCode = Constans.RESPONSE_CODE_200, description = Constans.NAME_UPDATED_SUCCESSFULLY,
                                            content = @Content(mediaType = Constans.MEDIA_TYPE_JSON)),
                                    @ApiResponse(responseCode = Constans.RESPONSE_CODE_400, description = Constans.INVALID_REQUEST),
                                    @ApiResponse(responseCode = Constans.RESPONSE_CODE_404, description = Constans.PRODUCT_NOT_FOUND)
                            }
                    )
            ),

            @RouterOperation(
                    path = Constans.API_FRANCHISES_MAX_STOCK_PRODUCT,
                    method = RequestMethod.GET,
                    beanClass = FranchiseHandler.class,
                    beanMethod = "getMaxStockProduct",
                    operation = @Operation(
                            operationId = "getMaxStockProduct",
                            summary = Constans.GET_MAX_STOCK_PRODUCT_SUMMARY,
                            description = Constans.GET_MAX_STOCK_PRODUCT_DESCRIPTION,
                            parameters = {
                                    @Parameter(name = Constans.PARAM_FRANCHISE_ID, in = ParameterIn.PATH, required = true, description = Constans.PARAM_DESC_FRANCHISE_ID)
                            },
                            responses = {
                                    @ApiResponse(responseCode = Constans.RESPONSE_CODE_200, description = Constans.PRODUCT_OBTAINED_SUCCESS,
                                            content = @Content(mediaType = Constans.MEDIA_TYPE_JSON)),
                                    @ApiResponse(responseCode = Constans.RESPONSE_CODE_400, description = Constans.INVALID_REQUEST),
                                    @ApiResponse(responseCode = Constans.RESPONSE_CODE_404, description = Constans.FRANCHISE_NOT_FOUND)
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> routerFunction(FranchiseHandler handler) {
        return route(GET(Constans.ACTUATOR_HEALTH), request ->
                ServerResponse.ok().bodyValue(Constans.RESPONSE_OK))
                .andRoute(POST(Constans.API_FRANCHISES), handler::createFranchise)
                .andRoute(PUT(Constans.API_FRANCHISES_ID), handler::updateFranchiseName)
                .andRoute(POST(Constans.API_FRANCHISES_BRANCHES), handler::addBranch)
                .andRoute(PUT(Constans.API_FRANCHISES_BRANCHES_ID), handler::updateBranchName)
                .andRoute(POST(Constans.API_FRANCHISES_BRANCHES_PRODUCTS), handler::addProduct)
                .andRoute(DELETE(Constans.API_FRANCHISES_BRANCHES_PRODUCTS_ID), handler::deleteProduct)
                .andRoute(PUT(Constans.API_FRANCHISES_BRANCHES_PRODUCTS_STOCK), handler::updateProductStock)
                .andRoute(PUT(Constans.API_FRANCHISES_BRANCHES_PRODUCTS_ID), handler::updateProductName)
                .andRoute(GET(Constans.API_FRANCHISES_MAX_STOCK_PRODUCT), handler::getMaxStockProduct);
    }
}