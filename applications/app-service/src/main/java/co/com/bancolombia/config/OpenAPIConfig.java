package co.com.bancolombia.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Configuración global de OpenAPI/Swagger para WebFlux Funcional.
 *
 * La documentación se genera desde:
 * 1. Anotaciones @RouterOperations en RouterRest (mapeo de rutas)
 * 2. Anotaciones @Operation en FranchiseHandler (detalles del endpoint)
 * 3. Anotaciones @Schema en DTOs (estructura de request/response)
 * 4. Esta clase (metadatos globales: título, versión, contacto, licencia)
 */
@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(buildApiInfo());
    }

    private Info buildApiInfo() {
        return new Info()
                .title("Franchise Management API")
                .description("REST API for managing franchises, branches and products. " +
                        "Implemented with Clean Architecture and reactive WebFlux.")
                .version("1.0.0")
                .contact(new Contact()
                        .name("Bancolombia Tech")
                        .url("https://www.bancolombia.com")
                        .email("tech@bancolombia.com"))
                .license(new License()
                        .name("Apache 2.0")
                        .url("https://www.apache.org/licenses/LICENSE-2.0.html"));
    }
}

