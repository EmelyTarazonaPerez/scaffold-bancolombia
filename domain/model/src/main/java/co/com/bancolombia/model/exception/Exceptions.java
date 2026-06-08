package co.com.bancolombia.model.exception;

public class Exceptions {

    private Exceptions() {}

    public static ResourceNotFoundException franchiseNotFound() {
        return new ResourceNotFoundException(
                "FRANCHISE_NOT_FOUND",
                "Franchise not found"
        );
    }

    public static ResourceNotFoundException branchNotFound() {
        return new ResourceNotFoundException(
                "BRANCH_NOT_FOUND",
                "Branch not found"
        );
    }

    public static ResourceNotFoundException productNotFound() {
        return new ResourceNotFoundException(
                "PRODUCT_NOT_FOUND",
                "Product not found"
        );
    }
}
