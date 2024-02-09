package co.grow.plan.academic.register.shared.domain.interfaces;

import co.grow.plan.academic.register.shared.domain.exceptions.EmptyPropertyException;

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
