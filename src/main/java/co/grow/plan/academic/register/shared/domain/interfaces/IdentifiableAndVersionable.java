package co.grow.plan.academic.register.shared.domain.interfaces;

public interface IdentifiableAndVersionable {

    /**
     * Gets the entity's id
     * @return Entity's id
     */
    Integer id();

    /**
     * Gets the Entity's info version
     * @return Entity's info version
     */
    Long version();
}
