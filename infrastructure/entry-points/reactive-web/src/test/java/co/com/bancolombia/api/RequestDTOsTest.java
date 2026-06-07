package co.com.bancolombia.api;

import co.com.bancolombia.model.franchise.Franchise;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Request DTOs Tests")
class RequestDTOsTest {

    @Test
    @DisplayName("CreateFranchiseRequest should convert to Franchise model")
    void testCreateFranchiseRequestConversion() {
        CreateFranchiseRequest request = new CreateFranchiseRequest("Bancolombia Center");
        Franchise franchise = request.toFranchise();

        assertThat(franchise).isNotNull();
        assertThat(franchise.getName()).isEqualTo("Bancolombia Center");
        assertThat(franchise.getId()).isNotNull();
    }

    @Test
    @DisplayName("UpdateFranchiseNameRequest should have correct name")
    void testUpdateFranchiseNameRequest() {
        UpdateFranchiseNameRequest request = new UpdateFranchiseNameRequest("New Name");

        assertThat(request.newName()).isEqualTo("New Name");
    }

    @Test
    @DisplayName("AddBranchRequest should convert to Branch model")
    void testAddBranchRequestConversion() {
        AddBranchRequest request = new AddBranchRequest("Downtown Branch");
        var branch = request.toBranch();

        assertThat(branch).isNotNull();
        assertThat(branch.getName()).isEqualTo("Downtown Branch");
        assertThat(branch.getId()).isNotNull();
    }

    @Test
    @DisplayName("UpdateBranchNameRequest should have correct name")
    void testUpdateBranchNameRequest() {
        UpdateBranchNameRequest request = new UpdateBranchNameRequest("Updated Name");

        assertThat(request.newName()).isEqualTo("Updated Name");
    }

    @Test
    @DisplayName("AddProductRequest should convert to Product model")
    void testAddProductRequestConversion() {
        AddProductRequest request = new AddProductRequest("Credit Card", 100);
        var product = request.toProduct();

        assertThat(product).isNotNull();
        assertThat(product.getName()).isEqualTo("Credit Card");
        assertThat(product.getStock()).isEqualTo(100);
        assertThat(product.getId()).isNotNull();
    }

    @Test
    @DisplayName("UpdateProductStockRequest should have correct stock")
    void testUpdateProductStockRequest() {
        UpdateProductStockRequest request = new UpdateProductStockRequest(75);

        assertThat(request.newStock()).isEqualTo(75);
    }

    @Test
    @DisplayName("UpdateProductNameRequest should have correct name")
    void testUpdateProductNameRequest() {
        UpdateProductNameRequest request = new UpdateProductNameRequest("Platinum Card");

        assertThat(request.newName()).isEqualTo("Platinum Card");
    }
}

