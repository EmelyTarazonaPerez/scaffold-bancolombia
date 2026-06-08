package co.com.bancolombia.model.exception;


public class ServiceUnavailableException extends DomainException {

    private final String serviceName;
    private final Throwable cause;

    public ServiceUnavailableException(String serviceName, String message, Throwable cause) {
        super("SERVICE_UNAVAILABLE", message);
        this.serviceName = serviceName;
        this.cause = cause;
    }

    public ServiceUnavailableException(String serviceName, String message) {
        this(serviceName, message, null);
    }

    public String getServiceName() {
        return serviceName;
    }

    @Override
    public synchronized Throwable getCause() {
        return cause;
    }
}

