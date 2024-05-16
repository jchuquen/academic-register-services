package co.grow.plan.academic.register.domain.shared.exceptions;

import java.util.logging.Level;
import java.util.logging.Logger;

public class EmptyPropertyException extends Exception {
    Logger logger = Logger.getLogger(EmptyPropertyException.class.getName());

    public static final String MESSAGE_FORMAT = "Field '%s' is required in '%s'";

    public EmptyPropertyException(String propertyName, String entityName) {
        super(
            String.format(MESSAGE_FORMAT, propertyName, entityName)
        );
        logger.log(Level.INFO, "Creating EmptyPropertyException");
    }
}
