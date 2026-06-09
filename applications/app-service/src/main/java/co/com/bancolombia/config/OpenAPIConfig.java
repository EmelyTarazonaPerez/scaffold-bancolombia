package co.com.bancolombia.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
public class OpenAPIConfig {

    private static final String TAG = "Franchise Management";
    private static final String FRANCHISE_ID = "franchiseId";
    private static final String BRANCH_ID = "branchId";
    private static final String PRODUCT_ID = "productId";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(buildApiInfo())
                .components(buildComponents())
                .path("/api/franchises", buildCreateFranchisesPath())
                .path("/api/franchises/{franchiseId}", buildUpdateFranchiseNamePath())
                .path("/api/franchises/{franchiseId}/branches", buildAddBranchPath())
                .path("/api/franchises/{franchiseId}/branches/{branchId}", buildUpdateBranchNamePath())
                .path("/api/franchises/{franchiseId}/branches/{branchId}/products", buildAddProductPath())
                .path("/api/franchises/{franchiseId}/branches/{branchId}/products/{productId}", buildDeleteAndUpdateProductPath())
                .path("/api/franchises/{franchiseId}/branches/{branchId}/products/{productId}/stock", buildUpdateProductStockPath())
                .path("/api/franchises/{franchiseId}/products/max-stock", buildGetMaxStockProductPath());
    }

    private Info buildApiInfo() {
        return new Info()
                .title("Franchise Management API")
                .description("REST API for managing franchises, branches and products. Implemented with Clean Architecture and reactive WebFlux.")
                .version("1.0.0")
                .contact(new Contact()
                        .name("Bancolombia Tech")
                        .url("https://www.bancolombia.com")
                        .email("tech@bancolombia.com"))
                .license(new License()
                        .name("Apache 2.0")
                        .url("https://www.apache.org/licenses/LICENSE-2.0.html"));
    }

    private Components buildComponents() {
        return new Components()
                .addSchemas("CreateFranchiseRequest", simpleSchema("franchiseName", "The name of the franchise"))
                .addSchemas("UpdateFranchiseNameRequest", simpleSchema("newName", "The new name for the franchise"))
                .addSchemas("AddBranchRequest", simpleSchema("branchName", "The name of the branch"))
                .addSchemas("UpdateBranchNameRequest", simpleSchema("newName", "The new name for the branch"))
                .addSchemas("AddProductRequest", multiPropertySchema(
                        new String[]{"productName", "stock"},
                        new String[]{"string", "integer"},
                        new String[]{"The name of the product", "The initial stock quantity"}))
                .addSchemas("UpdateProductStockRequest", simpleSchema("newStock", "The new stock quantity"))
                .addSchemas("UpdateProductNameRequest", simpleSchema("newName", "The new name for the product"));
    }

    // ===================== PATH BUILDERS =====================

    private PathItem buildCreateFranchisesPath() {
        return new PathItem().post(buildPostOp(
                "Create a new franchise",
                "Creates a new franchise with the provided name",
                "CreateFranchiseRequest",
                null,
                success(200, "Franchise created successfully"),
                error("400", "Invalid request")
        ));
    }

    private PathItem buildUpdateFranchiseNamePath() {
        return new PathItem().put(buildPutOp(
                "Update franchise name",
                "Updates the name of an existing franchise",
                "UpdateFranchiseNameRequest",
                singleParam(FRANCHISE_ID),
                success(200, "Name updated successfully"),
                error("404", "Franchise not found"),
                error("400", "Invalid request")
        ));
    }

    private PathItem buildAddBranchPath() {
        return new PathItem().post(buildPostOp(
                "Add branch to franchise",
                "Adds a new branch to an existing franchise",
                "AddBranchRequest",
                singleParam(FRANCHISE_ID),
                success(200, "Branch added successfully"),
                error("404", "Franchise not found"),
                error("400", "Invalid request")
        ));
    }

    private PathItem buildUpdateBranchNamePath() {
        return new PathItem().put(buildPutOp(
                "Update branch name",
                "Updates the name of an existing branch",
                "UpdateBranchNameRequest",
                multiParams(FRANCHISE_ID, BRANCH_ID),
                success(200, "Name updated successfully"),
                error("404", "Franchise or branch not found"),
                error("400", "Invalid request")
        ));
    }

    private PathItem buildAddProductPath() {
        return new PathItem().post(buildPostOp(
                "Add product to branch",
                "Adds a new product to an existing branch",
                "AddProductRequest",
                multiParams(FRANCHISE_ID, BRANCH_ID),
                success(200, "Product added successfully"),
                error("404", "Franchise or branch not found"),
                error("400", "Invalid request")
        ));
    }

    private PathItem buildDeleteAndUpdateProductPath() {
        return new PathItem()
                .delete(buildDeleteOp(
                        "Delete product",
                        "Deletes a product from a branch",
                        multiParams(FRANCHISE_ID, BRANCH_ID, PRODUCT_ID),
                        success(204, "Product deleted successfully"),
                        error("404", "Resource not found"),
                        error("400", "Invalid request")
                ))
                .put(buildPutOp(
                        "Update product name",
                        "Updates the name of a product",
                        "UpdateProductNameRequest",
                        multiParams(FRANCHISE_ID, BRANCH_ID, PRODUCT_ID),
                        success(200, "Name updated successfully"),
                        error("404", "Resource not found"),
                        error("400", "Invalid request")
                ));
    }

    private PathItem buildUpdateProductStockPath() {
        return new PathItem().put(buildPutOp(
                "Update product stock",
                "Updates the stock quantity of a product",
                "UpdateProductStockRequest",
                multiParams(FRANCHISE_ID, BRANCH_ID, PRODUCT_ID),
                success(200, "Stock updated successfully"),
                error("404", "Resource not found"),
                error("400", "Invalid stock value or invalid request")
        ));
    }

    private PathItem buildGetMaxStockProductPath() {
        return new PathItem().get(buildGetOp(
                "Get product with maximum stock",
                "Gets the product with the highest stock quantity in a franchise",
                singleParam(FRANCHISE_ID),
                success(200, "Product obtained successfully"),
                error("404", "Franchise not found"),
                error("400", "Invalid request")
        ));
    }

    // ===================== OPERATION BUILDERS =====================

    private Operation buildPostOp(String summary, String description, String schema,
                                  List<Parameter> params, ApiResponse... responses) {
        Operation op = baseOp(summary, description).requestBody(bodyRequest(schema));
        addParams(op, params);
        addResponses(op, responses);
        return op;
    }

    private Operation buildPutOp(String summary, String description, String schema,
                                 List<Parameter> params, ApiResponse... responses) {
        Operation op = baseOp(summary, description).requestBody(bodyRequest(schema));
        addParams(op, params);
        addResponses(op, responses);
        return op;
    }

    private Operation buildDeleteOp(String summary, String description, List<Parameter> params,
                                    ApiResponse... responses) {
        Operation op = baseOp(summary, description);
        addParams(op, params);
        addResponses(op, responses);
        return op;
    }

    private Operation buildGetOp(String summary, String description, List<Parameter> params,
                                 ApiResponse... responses) {
        Operation op = baseOp(summary, description);
        addParams(op, params);
        addResponses(op, responses);
        return op;
    }

    private Operation baseOp(String summary, String description) {
        return new Operation()
                .summary(summary)
                .description(description)
                .tags(Collections.singletonList(TAG));
    }

    private void addParams(Operation op, List<Parameter> params) {
        if (params != null) {
            params.forEach(op::addParametersItem);
        }
    }

    private void addResponses(Operation op, ApiResponse... responses) {
        ApiResponses apiResponses = new ApiResponses();
        String[] codes = {"200", "204", "400", "404"};
        for (int i = 0; i < responses.length && i < codes.length; i++) {
            apiResponses.addApiResponse(codes[i], responses[i]);
        }
        op.responses(apiResponses);
    }

    // ===================== PARAMETER BUILDERS =====================

    private List<Parameter> singleParam(String name) {
        List<Parameter> params = new ArrayList<>();
        params.add(pathParam(name));
        return params;
    }

    private List<Parameter> multiParams(String... names) {
        List<Parameter> params = new ArrayList<>();
        for (String name : names) {
            params.add(pathParam(name));
        }
        return params;
    }

    private Parameter pathParam(String name) {
        return new Parameter()
                .name(name)
                .in("path")
                .required(true)
                .description(name + " ID")
                .schema(new Schema<>().type("string"));
    }

    // ===================== REQUEST/RESPONSE BUILDERS =====================

    private RequestBody bodyRequest(String schemaRef) {
        return new RequestBody()
                .content(new Content()
                        .addMediaType("application/json",
                                new MediaType().schema(new Schema<>().$ref("#/components/schemas/" + schemaRef))));
    }

    private ApiResponse success(int code, String description) {
        return new ApiResponse()
                .description(description)
                .content(new Content()
                        .addMediaType("application/json",
                                new MediaType().schema(new Schema<>().type("object"))));
    }

    private ApiResponse error(String code, String description) {
        return new ApiResponse().description(description);
    }

    // ===================== SCHEMA BUILDERS =====================

    private Schema<?> simpleSchema(String propertyName, String description) {
        return new Schema<>()
                .type("object")
                .addProperty(propertyName, new Schema<>().type("string").description(description));
    }

    private Schema<?> multiPropertySchema(String[] propertyNames, String[] types, String[] descriptions) {
        Schema<?> schema = new Schema<>().type("object");
        for (int i = 0; i < propertyNames.length; i++) {
            schema.addProperty(propertyNames[i],
                    new Schema<>().type(types[i]).description(descriptions[i]));
        }
        return schema;
    }
}

