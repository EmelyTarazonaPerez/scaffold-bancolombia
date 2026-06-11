package co.com.bancolombia.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {
    @Bean
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