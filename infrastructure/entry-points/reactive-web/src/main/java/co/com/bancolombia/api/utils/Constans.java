package co.com.bancolombia.api.utils;

/**
 * Clase que centraliza todas las constantes y strings hardcoded de la capa entry-points
 */
public class Constans {

    // =============== TAGS Y ANOTACIONES ===============
    public static final String FRANCHISE_MANAGEMENT_TAG = "Franchise Management";
    public static final String FRANCHISE_MANAGEMENT_DESCRIPTION = "APIs for managing franchises, branches and products";

    // =============== OPERACIONES API ===============
    public static final String CREATE_FRANCHISE_SUMMARY = "Create a new franchise";
    public static final String CREATE_FRANCHISE_DESCRIPTION = "Creates a new franchise with the provided name";
    public static final String CREATE_FRANCHISE_FULL_DESCRIPTION = "Creates a new franchise with the provided name and optional branches with products";
    public static final String UPDATE_FRANCHISE_NAME_SUMMARY = "Update franchise name";
    public static final String UPDATE_FRANCHISE_NAME_DESCRIPTION = "Updates the name of an existing franchise";
    public static final String ADD_BRANCH_SUMMARY = "Add branch to franchise";
    public static final String ADD_BRANCH_DESCRIPTION = "Adds a new branch to an existing franchise";
    public static final String UPDATE_BRANCH_NAME_SUMMARY = "Update branch name";
    public static final String UPDATE_BRANCH_NAME_DESCRIPTION = "Updates the name of an existing branch";
    public static final String ADD_PRODUCT_SUMMARY = "Add product to branch";
    public static final String ADD_PRODUCT_DESCRIPTION = "Adds a new product to an existing branch";
    public static final String DELETE_PRODUCT_SUMMARY = "Delete product";
    public static final String DELETE_PRODUCT_DESCRIPTION = "Deletes a product from a branch";
    public static final String UPDATE_PRODUCT_STOCK_SUMMARY = "Update product stock";
    public static final String UPDATE_PRODUCT_STOCK_DESCRIPTION = "Updates the stock quantity of a product";
    public static final String UPDATE_PRODUCT_NAME_SUMMARY = "Update product name";
    public static final String UPDATE_PRODUCT_NAME_DESCRIPTION = "Updates the name of a product";
    public static final String GET_MAX_STOCK_PRODUCT_SUMMARY = "Get product with maximum stock";
    public static final String GET_MAX_STOCK_PRODUCT_DESCRIPTION = "Gets the product with the highest stock quantity in a franchise";

    // =============== RESPUESTAS API ===============
    public static final String FRANCHISE_CREATED_SUCCESS = "Franchise created successfully";
    public static final String NAME_UPDATED_SUCCESSFULLY = "Name updated successfully";
    public static final String BRANCH_ADDED_SUCCESS = "Branch added successfully";
    public static final String PRODUCT_ADDED_SUCCESS = "Product added successfully";
    public static final String PRODUCT_DELETED_SUCCESS = "Product deleted successfully";
    public static final String STOCK_UPDATED_SUCCESSFULLY = "Stock updated successfully";
    public static final String PRODUCT_OBTAINED_SUCCESS = "Product obtained successfully";
    public static final String FRANCHISE_NOT_FOUND = "Franchise not found";
    public static final String RESOURCE_NOT_FOUND = "Resource not found";
    public static final String FRANCHISE_OR_BRANCH_NOT_FOUND = "Franchise or branch not found";
    public static final String INVALID_REQUEST = "Invalid request";
    public static final String INVALID_STOCK_VALUE = "Invalid stock value or invalid request";
    public static final String INVALID_STOCK_VALUE_ONLY = "Invalid stock value";
    public static final String PRODUCT_NOT_FOUND = "Product not found";
    public static final String SERVICE_UNAVAILABLE = "Service unavailable";
    public static final String FRANCHISE_CREATION_REQUEST_DESC = "Franchise creation request";
    public static final String UPDATE_FRANCHISE_NAME_REQUEST_DESC = "Update franchise name request";
    public static final String ADD_BRANCH_REQUEST_DESC = "Add branch request";
    public static final String UPDATE_BRANCH_NAME_REQUEST_DESC = "Update branch name request";
    public static final String ADD_PRODUCT_REQUEST_DESC = "Add product request";
    public static final String UPDATE_PRODUCT_STOCK_REQUEST_DESC = "Update product stock request";
    public static final String UPDATE_PRODUCT_NAME_REQUEST_DESC = "Update product name request";

    // =============== CÓDIGOS DE RESPUESTA HTTP ===============
    public static final String RESPONSE_CODE_200 = "200";
    public static final String RESPONSE_CODE_201 = "201";
    public static final String RESPONSE_CODE_204 = "204";
    public static final String RESPONSE_CODE_400 = "400";
    public static final String RESPONSE_CODE_404 = "404";
    public static final String RESPONSE_CODE_503 = "503";

    // =============== CÓDIGOS DE ERROR ===============
    public static final String ERROR_CODE_VALIDATION_ERROR = "VALIDATION_ERROR";
    public static final String ERROR_CODE_NULL_REQUEST = "NULL_REQUEST";
    public static final String ERROR_CODE_INVALID_INPUT = "INVALID_INPUT";
    public static final String ERROR_CODE_CONSTRAINT_VIOLATION = "CONSTRAINT_VIOLATION";

    // =============== MENSAJES DE ERROR ===============
    public static final String VALIDATION_ERRORS_MESSAGE = "The request contains validation errors. Please check the violations for details";
    public static final String NULL_REQUEST_MESSAGE = "The request body cannot be null";
    public static final String VALIDATION_ERROR_MESSAGE = "Validation error occurred";
    public static final String REQUESTED_RESOURCE_NOT_FOUND = "The requested resource was not found";
    public static final String PROVIDED_INPUT_INVALID = "The provided input is invalid. Please verify your request data";
    public static final String OPERATION_VIOLATES_BUSINESS_RULE = "The operation violates a business rule. Please check your request";
    public static final String ERROR_PROCESSING_REQUEST = "An error occurred while processing your request";

    // =============== RUTAS API ===============
    public static final String ACTUATOR_HEALTH = "/actuator/health";
    public static final String API_FRANCHISES = "/api/franchises";
    public static final String API_FRANCHISES_ID = "/api/franchises/{franchiseId}";
    public static final String API_FRANCHISES_BRANCHES = "/api/franchises/{franchiseId}/branches";
    public static final String API_FRANCHISES_BRANCHES_ID = "/api/franchises/{franchiseId}/branches/{branchId}";
    public static final String API_FRANCHISES_BRANCHES_PRODUCTS = "/api/franchises/{franchiseId}/branches/{branchId}/products";
    public static final String API_FRANCHISES_BRANCHES_PRODUCTS_ID = "/api/franchises/{franchiseId}/branches/{branchId}/products/{productId}";
    public static final String API_FRANCHISES_BRANCHES_PRODUCTS_STOCK = "/api/franchises/{franchiseId}/branches/{branchId}/products/{productId}/stock";
    public static final String API_FRANCHISES_MAX_STOCK_PRODUCT = "/api/franchises/{franchiseId}/products/max-stock";

    // =============== PARÁMETROS DE RUTA ===============
    public static final String PARAM_FRANCHISE_ID = "franchiseId";
    public static final String PARAM_BRANCH_ID = "branchId";
    public static final String PARAM_PRODUCT_ID = "productId";

    // =============== DESCRIPCIONES DE PARÁMETROS ===============
    public static final String PARAM_DESC_FRANCHISE_ID = "Franchise ID";
    public static final String PARAM_DESC_BRANCH_ID = "Branch ID";
    public static final String PARAM_DESC_PRODUCT_ID = "Product ID";
    public static final String PARAM_DESC_FRANCHISE_ID_EXAMPLE = "550e8400-e29b-41d4-a716-446655440000";

    // =============== MEDIA TYPES ===============
    public static final String MEDIA_TYPE_JSON = "application/json";


    // =============== HEADERS DE SEGURIDAD ===============
    public static final String HEADER_CONTENT_SECURITY_POLICY = "Content-Security-Policy";
    public static final String HEADER_CONTENT_SECURITY_POLICY_VALUE = "default-src 'self'; frame-ancestors 'self'; form-action 'self'";
    public static final String HEADER_STRICT_TRANSPORT_SECURITY = "Strict-Transport-Security";
    public static final String HEADER_STRICT_TRANSPORT_SECURITY_VALUE = "max-age=31536000; includeSubDomains; preload";
    public static final String HEADER_CONTENT_TYPE_OPTIONS = "X-Content-Type-Options";
    public static final String HEADER_CONTENT_TYPE_OPTIONS_VALUE = "nosniff";
    public static final String HEADER_SERVER = "Server";
    public static final String HEADER_SERVER_VALUE = "";
    public static final String HEADER_CACHE_CONTROL = "Cache-Control";
    public static final String HEADER_CACHE_CONTROL_VALUE = "no-store";
    public static final String HEADER_PRAGMA = "Pragma";
    public static final String HEADER_PRAGMA_VALUE = "no-cache";
    public static final String HEADER_REFERRER_POLICY = "Referrer-Policy";
    public static final String HEADER_REFERRER_POLICY_VALUE = "strict-origin-when-cross-origin";

    // =============== CONTENT TYPES ===============
    public static final String CONTENT_TYPE_APPLICATION_JSON = "application/json";

    // =============== ESTADOS DE RESPUESTA ===============
    public static final String RESPONSE_OK = "OK";
}
