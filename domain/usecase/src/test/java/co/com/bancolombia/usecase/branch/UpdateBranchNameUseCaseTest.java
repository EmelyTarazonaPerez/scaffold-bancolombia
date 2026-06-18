package co.com.bancolombia.usecase.branch;

import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.model.exception.ResourceNotFoundException;
import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.franchise.gateways.IFranchiseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("UpdateBranchNameUseCase Tests")
class UpdateBranchNameUseCaseTest {

    private UpdateBranchNameUseCase updateBranchNameUseCase;

    @Mock
    private IFranchiseRepository IFranchiseRepository;

    @BeforeEach
    void setUp() {
        updateBranchNameUseCase = new UpdateBranchNameUseCase(IFranchiseRepository);
    }

    @Test
    @DisplayName("Should update branch name successfully")
    void testUpdateBranchNameSuccess() {
        String franchiseId = "franchise-1";
        String branchId = "branch-1";
        String newBranchName = "Downtown Updated";

        Branch branch = Branch.builder()
                .id(branchId)
                .name("Downtown")
                .products(new ArrayList<>())
                .build();

        Franchise franchise = Franchise.builder()
                .id(franchiseId)
                .name("Bancolombia Center")
                .branches(new ArrayList<>(List.of(branch)))
                .build();

        when(IFranchiseRepository.findById(franchiseId))
                .thenReturn(Mono.just(franchise));
        when(IFranchiseRepository.save(any(Franchise.class)))
                .thenReturn(Mono.just(franchise));

        StepVerifier.create(updateBranchNameUseCase.execute(franchiseId, branchId, newBranchName))
                .assertNext(result -> {
                    assertThat(result).isNotNull();
                    assertThat(result.getId()).isEqualTo(branchId);
                    assertThat(result.getName()).isEqualTo(newBranchName);
                })
                .verifyComplete();
    }

    @Test
    @DisplayName("Should throw franchiseNotFound when franchise does not exist")
    void testUpdateBranchNameFranchiseNotFound() {
        String franchiseId = "non-existent-franchise";
        String branchId = "branch-1";
        String newBranchName = "Downtown Updated";

        when(IFranchiseRepository.findById(franchiseId))
                .thenReturn(Mono.empty());

        StepVerifier.create(updateBranchNameUseCase.execute(franchiseId, branchId, newBranchName))
                .expectError(ResourceNotFoundException.class)
                .verify();
    }

    @Test
    @DisplayName("Should throw branchNotFound when branch does not exist")
    void testUpdateBranchNameBranchNotFound() {
        String franchiseId = "franchise-1";
        String branchId = "non-existent-branch";
        String newBranchName = "Downtown Updated";

        Branch existingBranch = Branch.builder()
                .id("branch-2")
                .name("Uptown")
                .products(new ArrayList<>())
                .build();

        Franchise franchise = Franchise.builder()
                .id(franchiseId)
                .name("Bancolombia Center")
                .branches(new ArrayList<>(List.of(existingBranch)))
                .build();

        when(IFranchiseRepository.findById(franchiseId))
                .thenReturn(Mono.just(franchise));

        StepVerifier.create(updateBranchNameUseCase.execute(franchiseId, branchId, newBranchName))
                .expectError(ResourceNotFoundException.class)
                .verify();
    }

    @Test
    @DisplayName("Should update branch name in franchise with multiple branches")
    void testUpdateBranchNameMultipleBranches() {
        String franchiseId = "franchise-1";
        String branchId = "branch-1";
        String newBranchName = "Downtown Updated";

        Branch branch1 = Branch.builder()
                .id("branch-1")
                .name("Downtown")
                .products(new ArrayList<>())
                .build();

        Branch branch2 = Branch.builder()
                .id("branch-2")
                .name("Uptown")
                .products(new ArrayList<>())
                .build();

        Franchise franchise = Franchise.builder()
                .id(franchiseId)
                .name("Bancolombia Center")
                .branches(new ArrayList<>(List.of(branch1, branch2)))
                .build();

        when(IFranchiseRepository.findById(franchiseId))
                .thenReturn(Mono.just(franchise));
        when(IFranchiseRepository.save(any(Franchise.class)))
                .thenReturn(Mono.just(franchise));

        StepVerifier.create(updateBranchNameUseCase.execute(franchiseId, branchId, newBranchName))
                .assertNext(result -> {
                    assertThat(result).isNotNull();
                    assertThat(result.getId()).isEqualTo(branchId);
                    assertThat(result.getName()).isEqualTo(newBranchName);
                })
                .verifyComplete();
    }

    @Test
    @DisplayName("Should preserve other branches when updating one branch name")
    void testUpdateBranchNamePreservesOtherBranches() {
        String franchiseId = "franchise-1";
        String branchId = "branch-1";
        String newBranchName = "Downtown Updated";

        Branch branch1 = Branch.builder()
                .id("branch-1")
                .name("Downtown")
                .products(new ArrayList<>())
                .build();

        Branch branch2 = Branch.builder()
                .id("branch-2")
                .name("Uptown")
                .products(new ArrayList<>())
                .build();

        Franchise franchise = Franchise.builder()
                .id(franchiseId)
                .name("Bancolombia Center")
                .branches(new ArrayList<>(List.of(branch1, branch2)))
                .build();

        when(IFranchiseRepository.findById(franchiseId))
                .thenReturn(Mono.just(franchise));
        when(IFranchiseRepository.save(any(Franchise.class)))
                .thenReturn(Mono.just(franchise));

        StepVerifier.create(updateBranchNameUseCase.execute(franchiseId, branchId, newBranchName))
                .assertNext(result -> {
                    assertThat(result.getName()).isEqualTo(newBranchName);
                    assertThat(franchise.getBranches()).hasSize(2);
                    assertThat(franchise.getBranches().getLast().getName()).isEqualTo("Uptown");
                })
                .verifyComplete();
    }

    @Test
    @DisplayName("Should update branch name with empty string")
    void testUpdateBranchNameWithEmptyString() {
        String franchiseId = "franchise-1";
        String branchId = "branch-1";
        String newBranchName = "";

        Branch branch = Branch.builder()
                .id(branchId)
                .name("Downtown")
                .products(new ArrayList<>())
                .build();

        Franchise franchise = Franchise.builder()
                .id(franchiseId)
                .name("Bancolombia Center")
                .branches(new ArrayList<>(List.of(branch)))
                .build();

        when(IFranchiseRepository.findById(franchiseId))
                .thenReturn(Mono.just(franchise));
        when(IFranchiseRepository.save(any(Franchise.class)))
                .thenReturn(Mono.just(franchise));

        StepVerifier.create(updateBranchNameUseCase.execute(franchiseId, branchId, newBranchName))
                .assertNext(result -> {
                    assertThat(result).isNotNull();
                    assertThat(result.getName()).isEqualTo(newBranchName);
                })
                .verifyComplete();
    }
}
