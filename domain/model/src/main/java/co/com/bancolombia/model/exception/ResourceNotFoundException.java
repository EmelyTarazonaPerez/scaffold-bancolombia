package co.com.bancolombia.model.exception;

/**
 * Exception thrown when a resource is not found
 */
public class ResourceNotFoundException extends DomainException {
    private final String resourceId;

    public ResourceNotFoundException(String resourceType, String resourceId) {
        super("RESOURCE_NOT_FOUND",
              String.format("%s with id '%s' not found", resourceType, resourceId));
        this.resourceId = resourceId;
    }

    public String getResourceId() {
        return resourceId;
    }
}

