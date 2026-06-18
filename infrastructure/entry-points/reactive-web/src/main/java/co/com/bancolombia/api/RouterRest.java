package co.com.bancolombia.api;

import co.com.bancolombia.api.dto.request.*;
import co.com.bancolombia.api.utils.Constants;
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
                    path = Constants.API_FRANCHISES,
                    method = RequestMethod.POST,
                    beanClass = FranchiseHandler.class,
                    beanMethod = "createFranchise",
                    operation = @Operation(
                            operationId = "createFranchise",
                            summary = Constants.CREATE_FRANCHISE_SUMMARY,
                            description = Constants.CREATE_FRANCHISE_FULL_DESCRIPTION,
                            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                    description = Constants.FRANCHISE_CREATION_REQUEST_DESC,
                                    required = true,
                                    content = @Content(
                                            mediaType = Constants.MEDIA_TYPE_JSON,
                                            schema = @Schema(implementation = CreateFranchiseRequest.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(responseCode = Constants.RESPONSE_CODE_200, description = Constants.FRANCHISE_CREATED_SUCCESS,
                                            content = @Content(mediaType = Constants.MEDIA_TYPE_JSON, schema = @Schema(implementation = Object.class))),
                                    @ApiResponse(responseCode = Constants.RESPONSE_CODE_400, description = Constants.INVALID_REQUEST),
                                    @ApiResponse(responseCode = Constants.RESPONSE_CODE_503, description = Constants.SERVICE_UNAVAILABLE)
                            }
                    )
            ),

            @RouterOperation(
                    path = Constants.API_FRANCHISES_ID,
                    method = RequestMethod.PUT,
                    beanClass = FranchiseHandler.class,
                    beanMethod = "updateFranchiseName",
                    operation = @Operation(
                            operationId = "updateFranchiseName",
                            summary = Constants.UPDATE_FRANCHISE_NAME_SUMMARY,
                            description = Constants.UPDATE_FRANCHISE_NAME_DESCRIPTION,
                            parameters = {
                                    @Parameter(name = Constants.PARAM_FRANCHISE_ID, in = ParameterIn.PATH, required = true,
                                            description = Constants.PARAM_DESC_FRANCHISE_ID, example = Constants.PARAM_DESC_FRANCHISE_ID_EXAMPLE)
                            },
                            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                    description = Constants.UPDATE_FRANCHISE_NAME_REQUEST_DESC,
                                    required = true,
                                    content = @Content(
                                            mediaType = Constants.MEDIA_TYPE_JSON,
                                            schema = @Schema(implementation = UpdateFranchiseNameRequest.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(responseCode = Constants.RESPONSE_CODE_200, description = Constants.NAME_UPDATED_SUCCESSFULLY,
                                            content = @Content(mediaType = Constants.MEDIA_TYPE_JSON, schema = @Schema(implementation = CreateFranchiseRequest.class))),
                                    @ApiResponse(responseCode = Constants.RESPONSE_CODE_400, description = Constants.INVALID_REQUEST),
                                    @ApiResponse(responseCode = Constants.RESPONSE_CODE_404, description = Constants.FRANCHISE_NOT_FOUND)
                            }
                    )
            ),

            @RouterOperation(
                    path = Constants.API_FRANCHISES_BRANCHES,
                    method = RequestMethod.POST,
                    beanClass = FranchiseHandler.class,
                    beanMethod = "addBranch",
                    operation = @Operation(
                            operationId = "addBranch",
                            summary = Constants.ADD_BRANCH_SUMMARY,
                            description = Constants.ADD_BRANCH_DESCRIPTION,
                            parameters = {
                                    @Parameter(name = Constants.PARAM_FRANCHISE_ID, in = ParameterIn.PATH, required = true,
                                            description = Constants.PARAM_DESC_FRANCHISE_ID, example = Constants.PARAM_DESC_FRANCHISE_ID_EXAMPLE)
                            },
                            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                    description = Constants.ADD_BRANCH_REQUEST_DESC,
                                    required = true,
                                    content = @Content(
                                            mediaType = Constants.MEDIA_TYPE_JSON,
                                            schema = @Schema(implementation = AddBranchRequest.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(responseCode = Constants.RESPONSE_CODE_201, description = Constants.BRANCH_ADDED_SUCCESS,
                                            content = @Content(mediaType = Constants.MEDIA_TYPE_JSON, schema = @Schema(implementation = Object.class))),
                                    @ApiResponse(responseCode = Constants.RESPONSE_CODE_400, description = Constants.INVALID_REQUEST),
                                    @ApiResponse(responseCode = Constants.RESPONSE_CODE_404, description = Constants.FRANCHISE_NOT_FOUND)
                            }
                    )
            ),

            @RouterOperation(
                    path = Constants.API_FRANCHISES_BRANCHES_ID,
                    method = RequestMethod.PUT,
                    beanClass = FranchiseHandler.class,
                    beanMethod = "updateBranchName",
                    operation = @Operation(
                            operationId = "updateBranchName",
                            summary = Constants.UPDATE_BRANCH_NAME_SUMMARY,
                            description = Constants.UPDATE_BRANCH_NAME_DESCRIPTION,
                            parameters = {
                                    @Parameter(name = Constants.PARAM_FRANCHISE_ID, in = ParameterIn.PATH, required = true, description = Constants.PARAM_DESC_FRANCHISE_ID),
                                    @Parameter(name = Constants.PARAM_BRANCH_ID, in = ParameterIn.PATH, required = true, description = Constants.PARAM_DESC_BRANCH_ID)
                            },
                            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                    description = Constants.UPDATE_BRANCH_NAME_REQUEST_DESC,
                                    required = true,
                                    content = @Content(
                                            mediaType = Constants.MEDIA_TYPE_JSON,
                                            schema = @Schema(implementation = UpdateBranchNameRequest.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(responseCode = Constants.RESPONSE_CODE_200, description = Constants.NAME_UPDATED_SUCCESSFULLY,
                                            content = @Content(mediaType = Constants.MEDIA_TYPE_JSON)),
                                    @ApiResponse(responseCode = Constants.RESPONSE_CODE_400, description = Constants.INVALID_REQUEST),
                                    @ApiResponse(responseCode = Constants.RESPONSE_CODE_404, description = Constants.FRANCHISE_OR_BRANCH_NOT_FOUND)
                            }
                    )
            ),

            @RouterOperation(
                    path = Constants.API_FRANCHISES_BRANCHES_PRODUCTS,
                    method = RequestMethod.POST,
                    beanClass = FranchiseHandler.class,
                    beanMethod = "addProduct",
                    operation = @Operation(
                            operationId = "addProduct",
                            summary = Constants.ADD_PRODUCT_SUMMARY,
                            description = Constants.ADD_PRODUCT_DESCRIPTION,
                            parameters = {
                                    @Parameter(name = Constants.PARAM_FRANCHISE_ID, in = ParameterIn.PATH, required = true, description = Constants.PARAM_DESC_FRANCHISE_ID),
                                    @Parameter(name = Constants.PARAM_BRANCH_ID, in = ParameterIn.PATH, required = true, description = Constants.PARAM_DESC_BRANCH_ID)
                            },
                            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                    description = Constants.ADD_PRODUCT_REQUEST_DESC,
                                    required = true,
                                    content = @Content(
                                            mediaType = Constants.MEDIA_TYPE_JSON,
                                            schema = @Schema(implementation = AddProductRequest.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(responseCode = Constants.RESPONSE_CODE_201, description = Constants.PRODUCT_ADDED_SUCCESS,
                                            content = @Content(mediaType = Constants.MEDIA_TYPE_JSON)),
                                    @ApiResponse(responseCode = Constants.RESPONSE_CODE_400, description = Constants.INVALID_REQUEST),
                                    @ApiResponse(responseCode = Constants.RESPONSE_CODE_404, description = Constants.FRANCHISE_OR_BRANCH_NOT_FOUND)
                            }
                    )
            ),

            @RouterOperation(
                    path = Constants.API_FRANCHISES_BRANCHES_PRODUCTS_ID,
                    method = RequestMethod.DELETE,
                    beanClass = FranchiseHandler.class,
                    beanMethod = "deleteProduct",
                    operation = @Operation(
                            operationId = "deleteProduct",
                            summary = Constants.DELETE_PRODUCT_SUMMARY,
                            description = Constants.DELETE_PRODUCT_DESCRIPTION,
                            parameters = {
                                    @Parameter(name = Constants.PARAM_FRANCHISE_ID, in = ParameterIn.PATH, required = true, description = Constants.PARAM_DESC_FRANCHISE_ID),
                                    @Parameter(name = Constants.PARAM_BRANCH_ID, in = ParameterIn.PATH, required = true, description = Constants.PARAM_DESC_BRANCH_ID),
                                    @Parameter(name = Constants.PARAM_PRODUCT_ID, in = ParameterIn.PATH, required = true, description = Constants.PARAM_DESC_PRODUCT_ID)
                            },
                            responses = {
                                    @ApiResponse(responseCode = Constants.RESPONSE_CODE_204, description = Constants.PRODUCT_DELETED_SUCCESS),
                                    @ApiResponse(responseCode = Constants.RESPONSE_CODE_400, description = Constants.INVALID_REQUEST),
                                    @ApiResponse(responseCode = Constants.RESPONSE_CODE_404, description = Constants.PRODUCT_NOT_FOUND)
                            }
                    )
            ),

            @RouterOperation(
                    path = Constants.API_FRANCHISES_BRANCHES_PRODUCTS_STOCK,
                    method = RequestMethod.PUT,
                    beanClass = FranchiseHandler.class,
                    beanMethod = "updateProductStock",
                    operation = @Operation(
                            operationId = "updateProductStock",
                            summary = Constants.UPDATE_PRODUCT_STOCK_SUMMARY,
                            description = Constants.UPDATE_PRODUCT_STOCK_DESCRIPTION,
                            parameters = {
                                    @Parameter(name = Constants.PARAM_FRANCHISE_ID, in = ParameterIn.PATH, required = true, description = Constants.PARAM_DESC_FRANCHISE_ID),
                                    @Parameter(name = Constants.PARAM_BRANCH_ID, in = ParameterIn.PATH, required = true, description = Constants.PARAM_DESC_BRANCH_ID),
                                    @Parameter(name = Constants.PARAM_PRODUCT_ID, in = ParameterIn.PATH, required = true, description = Constants.PARAM_DESC_PRODUCT_ID)
                            },
                            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                    description = Constants.UPDATE_PRODUCT_STOCK_REQUEST_DESC,
                                    required = true,
                                    content = @Content(
                                            mediaType = Constants.MEDIA_TYPE_JSON,
                                            schema = @Schema(implementation = UpdateProductStockRequest.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(responseCode = Constants.RESPONSE_CODE_200, description = Constants.STOCK_UPDATED_SUCCESSFULLY,
                                            content = @Content(mediaType = Constants.MEDIA_TYPE_JSON)),
                                    @ApiResponse(responseCode = Constants.RESPONSE_CODE_400, description = Constants.INVALID_STOCK_VALUE_ONLY),
                                    @ApiResponse(responseCode = Constants.RESPONSE_CODE_404, description = Constants.PRODUCT_NOT_FOUND)
                            }
                    )
            ),

            @RouterOperation(
                    path = Constants.API_FRANCHISES_BRANCHES_PRODUCTS_ID,
                    method = RequestMethod.PUT,
                    beanClass = FranchiseHandler.class,
                    beanMethod = "updateProductName",
                    operation = @Operation(
                            operationId = "updateProductName",
                            summary = Constants.UPDATE_PRODUCT_NAME_SUMMARY,
                            description = Constants.UPDATE_PRODUCT_NAME_DESCRIPTION,
                            parameters = {
                                    @Parameter(name = Constants.PARAM_FRANCHISE_ID, in = ParameterIn.PATH, required = true, description = Constants.PARAM_DESC_FRANCHISE_ID),
                                    @Parameter(name = Constants.PARAM_BRANCH_ID, in = ParameterIn.PATH, required = true, description = Constants.PARAM_DESC_BRANCH_ID),
                                    @Parameter(name = Constants.PARAM_PRODUCT_ID, in = ParameterIn.PATH, required = true, description = Constants.PARAM_DESC_PRODUCT_ID)
                            },
                            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                    description = Constants.UPDATE_PRODUCT_NAME_REQUEST_DESC,
                                    required = true,
                                    content = @Content(
                                            mediaType = Constants.MEDIA_TYPE_JSON,
                                            schema = @Schema(implementation = UpdateProductNameRequest.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(responseCode = Constants.RESPONSE_CODE_200, description = Constants.NAME_UPDATED_SUCCESSFULLY,
                                            content = @Content(mediaType = Constants.MEDIA_TYPE_JSON)),
                                    @ApiResponse(responseCode = Constants.RESPONSE_CODE_400, description = Constants.INVALID_REQUEST),
                                    @ApiResponse(responseCode = Constants.RESPONSE_CODE_404, description = Constants.PRODUCT_NOT_FOUND)
                            }
                    )
            ),

            @RouterOperation(
                    path = Constants.API_FRANCHISES_MAX_STOCK_PRODUCT,
                    method = RequestMethod.GET,
                    beanClass = FranchiseHandler.class,
                    beanMethod = "getMaxStockProduct",
                    operation = @Operation(
                            operationId = "getMaxStockProduct",
                            summary = Constants.GET_MAX_STOCK_PRODUCT_SUMMARY,
                            description = Constants.GET_MAX_STOCK_PRODUCT_DESCRIPTION,
                            parameters = {
                                    @Parameter(name = Constants.PARAM_FRANCHISE_ID, in = ParameterIn.PATH, required = true, description = Constants.PARAM_DESC_FRANCHISE_ID)
                            },
                            responses = {
                                    @ApiResponse(responseCode = Constants.RESPONSE_CODE_200, description = Constants.PRODUCT_OBTAINED_SUCCESS,
                                            content = @Content(mediaType = Constants.MEDIA_TYPE_JSON)),
                                    @ApiResponse(responseCode = Constants.RESPONSE_CODE_400, description = Constants.INVALID_REQUEST),
                                    @ApiResponse(responseCode = Constants.RESPONSE_CODE_404, description = Constants.FRANCHISE_NOT_FOUND)
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> routerFunction(FranchiseHandler handler) {
        return route(GET(Constants.ACTUATOR_HEALTH), request ->
                ServerResponse.ok().bodyValue(Constants.RESPONSE_OK))
                .andRoute(POST(Constants.API_FRANCHISES), handler::createFranchise)
                .andRoute(PUT(Constants.API_FRANCHISES_ID), handler::updateFranchiseName)
                .andRoute(POST(Constants.API_FRANCHISES_BRANCHES), handler::addBranch)
                .andRoute(PUT(Constants.API_FRANCHISES_BRANCHES_ID), handler::updateBranchName)
                .andRoute(POST(Constants.API_FRANCHISES_BRANCHES_PRODUCTS), handler::addProduct)
                .andRoute(DELETE(Constants.API_FRANCHISES_BRANCHES_PRODUCTS_ID), handler::deleteProduct)
                .andRoute(PUT(Constants.API_FRANCHISES_BRANCHES_PRODUCTS_STOCK), handler::updateProductStock)
                .andRoute(PUT(Constants.API_FRANCHISES_BRANCHES_PRODUCTS_ID), handler::updateProductName)
                .andRoute(GET(Constants.API_FRANCHISES_MAX_STOCK_PRODUCT), handler::getMaxStockProduct);
    }
}