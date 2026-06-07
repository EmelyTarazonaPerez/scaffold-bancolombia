package co.com.bancolombia.model.franchise;

import co.com.bancolombia.model.branch.Branch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Franchise Model Tests")
class FranchiseTest {

    private Franchise franchise;

    @BeforeEach
    void setUp() {
        franchise = Franchise.builder()
                .id("franchise-1")
                .name("Bancolombia Center")
                .branches(new ArrayList<>())
                .build();
    }

    @Test
    @DisplayName("Should create franchise with valid data")
    void testCreateFranchiseWithValidData() {
        assertThat(franchise).isNotNull();
        assertThat(franchise.getId()).isEqualTo("franchise-1");
        assertThat(franchise.getName()).isEqualTo("Bancolombia Center");
        assertThat(franchise.getBranches()).isNotNull();
    }

    @Test
    @DisplayName("Should update franchise name")
    void testUpdateFranchiseName() {
        Franchise updatedFranchise = franchise.toBuilder()
                .name("Bancolombia South")
                .build();

        assertThat(updatedFranchise.getName()).isEqualTo("Bancolombia South");
        assertThat(updatedFranchise.getId()).isEqualTo(franchise.getId());
    }

    @Test
    @DisplayName("Should have empty branches initially")
    void testInitiallyEmptyBranches() {
        assertThat(franchise.getBranches()).isEmpty();
    }

    @Test
    @DisplayName("Should add branches to franchise")
    void testAddBranchesToFranchise() {
        Branch branch = Branch.builder()
                .id("branch-1")
                .name("Downtown")
                .build();

        List<Branch> branches = new ArrayList<>();
        branches.add(branch);

        Franchise updatedFranchise = franchise.toBuilder()
                .branches(branches)
                .build();

        assertThat(updatedFranchise.getBranches()).hasSize(1);
        assertThat(updatedFranchise.getBranches().get(0).getName()).isEqualTo("Downtown");
    }

    @Test
    @DisplayName("Should handle null values properly")
    void testNullValues() {
        Franchise nullFranchise = Franchise.builder()
                .id(null)
                .name(null)
                .branches(null)
                .build();

        assertThat(nullFranchise.getId()).isNull();
        assertThat(nullFranchise.getName()).isNull();
        assertThat(nullFranchise.getBranches()).isNull();
    }
}


