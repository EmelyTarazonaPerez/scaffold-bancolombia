package co.com.bancolombia.model.exception;

/**
 * Exception thrown when a business rule is violated
 */
public class BusinessRuleException extends DomainException {

    public BusinessRuleException(String rule) {
        super("BUSINESS_RULE_VIOLATION",
              String.format("Business rule violated: %s", rule));
    }

    public BusinessRuleException(String code, String message) {
        super(code, message);
    }
}

