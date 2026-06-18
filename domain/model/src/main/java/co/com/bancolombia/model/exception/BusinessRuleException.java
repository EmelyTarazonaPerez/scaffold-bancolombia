package co.com.bancolombia.model.exception;


public class BusinessRuleException extends DomainException {

    public BusinessRuleException(String rule) {
        super("BUSINESS_RULE_VIOLATION",
              String.format("Business rule violated: %s", rule));
    }

    public BusinessRuleException(String code, String message) {
        super(code, message);
    }
}

