package co.grow.plan.academic.register.domain.shared.interfaces;

import co.grow.plan.academic.register.domain.shared.exceptions.EmptyPropertyException;

public interface BasicEntity extends Entity {
    /**
     * Gets the entity's name
     * @return Entity's name
     */
    String name();

    default void validate() throws EmptyPropertyException {
        if (this.name() == null || this.name().isBlank()) {
            throw new EmptyPropertyException("name", this.getClass().getName());
        }
    }
}
