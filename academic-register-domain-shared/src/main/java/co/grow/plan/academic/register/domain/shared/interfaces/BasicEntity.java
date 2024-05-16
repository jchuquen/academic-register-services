package co.grow.plan.academic.register.domain.shared.interfaces;

import co.grow.plan.academic.register.domain.shared.exceptions.EmptyPropertyException;

import java.util.logging.Level;
import java.util.logging.Logger;

public interface BasicEntity extends Entity {
    Logger logger = Logger.getLogger(BasicEntity.class.getName());

    /**
     * Gets the entity's name
     * @return Entity's name
     */
    String name();

    default void validate() throws EmptyPropertyException {
        logger.log(Level.INFO, "Validating Basic Entity");
        if (this.name() == null || this.name().isBlank()) {
            throw new EmptyPropertyException("name", this.getClass().getName());
        }
    }
}
