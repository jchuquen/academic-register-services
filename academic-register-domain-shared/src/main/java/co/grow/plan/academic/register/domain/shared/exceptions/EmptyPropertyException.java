package co.grow.plan.academic.register.domain.shared.exceptions;

public class EmptyPropertyException extends Exception {

    public static final String MESSAGE_FORMAT = "Field '%s' is required in '%s'";

    public EmptyPropertyException(String propertyName, String entityName) {
        super(
            String.format(MESSAGE_FORMAT, propertyName, entityName)
        );
    }
}
