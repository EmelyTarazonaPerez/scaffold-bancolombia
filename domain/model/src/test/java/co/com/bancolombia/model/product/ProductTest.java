package co.com.bancolombia.model.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Product Model Tests")
class ProductTest {

    private Product product;

    @BeforeEach
    void setUp() {
        product = Product.builder()
                .id("product-1")
                .name("Premium Credit Card")
                .stock(100)
                .build();
    }

    @Test
    @DisplayName("Should create product with valid data")
    void testCreateProductWithValidData() {
        assertThat(product).isNotNull();
        assertThat(product.getId()).isEqualTo("product-1");
        assertThat(product.getName()).isEqualTo("Premium Credit Card");
        assertThat(product.getStock()).isEqualTo(100);
    }

    @Test
    @DisplayName("Should update product name")
    void testUpdateProductName() {
        Product updatedProduct = product.toBuilder()
                .name("Platinum Credit Card")
                .build();

        assertThat(updatedProduct.getName()).isEqualTo("Platinum Credit Card");
        assertThat(updatedProduct.getStock()).isEqualTo(100);
    }

    @Test
    @DisplayName("Should update product stock")
    void testUpdateProductStock() {
        Product updatedProduct = product.toBuilder()
                .stock(75)
                .build();

        assertThat(updatedProduct.getStock()).isEqualTo(75);
        assertThat(updatedProduct.getName()).isEqualTo("Premium Credit Card");
    }

    @Test
    @DisplayName("Should handle zero stock")
    void testZeroStock() {
        Product zeroStockProduct = product.toBuilder()
                .stock(0)
                .build();

        assertThat(zeroStockProduct.getStock()).isEqualTo(0);
    }

    @Test
    @DisplayName("Should handle high stock values")
    void testHighStockValues() {
        Product highStockProduct = product.toBuilder()
                .stock(999999)
                .build();

        assertThat(highStockProduct.getStock()).isEqualTo(999999);
    }

    @Test
    @DisplayName("Should maintain product immutability with builder")
    void testImmutability() {
        Product original = product;
        Product modified = product.toBuilder()
                .stock(50)
                .build();

        assertThat(original.getStock()).isEqualTo(100);
        assertThat(modified.getStock()).isEqualTo(50);
    }
}

