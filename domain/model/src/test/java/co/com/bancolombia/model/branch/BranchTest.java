package co.com.bancolombia.model.branch;

import co.com.bancolombia.model.product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Branch Model Tests")
class BranchTest {

    private Branch branch;

    @BeforeEach
    void setUp() {
        branch = Branch.builder()
                .id("branch-1")
                .name("Downtown Branch")
                .products(new ArrayList<>())
                .build();
    }

    @Test
    @DisplayName("Should create branch with valid data")
    void testCreateBranchWithValidData() {
        assertThat(branch).isNotNull();
        assertThat(branch.getId()).isEqualTo("branch-1");
        assertThat(branch.getName()).isEqualTo("Downtown Branch");
        assertThat(branch.getProducts()).isNotNull();
    }

    @Test
    @DisplayName("Should update branch name")
    void testUpdateBranchName() {
        Branch updatedBranch = branch.toBuilder()
                .name("Midtown Branch")
                .build();

        assertThat(updatedBranch.getName()).isEqualTo("Midtown Branch");
        assertThat(updatedBranch.getId()).isEqualTo(branch.getId());
    }

    @Test
    @DisplayName("Should have empty products initially")
    void testInitiallyEmptyProducts() {
        assertThat(branch.getProducts()).isEmpty();
    }

    @Test
    @DisplayName("Should add products to branch")
    void testAddProductsToBranch() {
        Product product = Product.builder()
                .id("product-1")
                .name("Credit Card")
                .stock(100)
                .build();

        List<Product> products = new ArrayList<>();
        products.add(product);

        Branch updatedBranch = branch.toBuilder()
                .products(products)
                .build();

        assertThat(updatedBranch.getProducts()).hasSize(1);
        assertThat(updatedBranch.getProducts().get(0).getName()).isEqualTo("Credit Card");
    }

    @Test
    @DisplayName("Should have multiple products")
    void testMultipleProducts() {
        List<Product> products = new ArrayList<>();
        products.add(Product.builder().id("p1").name("Card 1").stock(50).build());
        products.add(Product.builder().id("p2").name("Card 2").stock(75).build());
        products.add(Product.builder().id("p3").name("Card 3").stock(100).build());

        Branch updatedBranch = branch.toBuilder()
                .products(products)
                .build();

        assertThat(updatedBranch.getProducts()).hasSize(3);
    }
}


