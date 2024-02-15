package co.grow.plan.academic.register.domain.shared.exceptions;

public class EmptyPropertyException extends Exception {
    public EmptyPropertyException(String propertyName, String entityName) {
        super(
            String.format("Field '%s' is required in '%s'",
                propertyName, entityName)
        );
    }
}
