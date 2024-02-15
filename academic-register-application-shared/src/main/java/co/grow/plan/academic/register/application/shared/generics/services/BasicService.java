package co.grow.plan.academic.register.application.shared.generics.services;

import co.grow.plan.academic.register.domain.shared.interfaces.Entity;

import java.util.List;

public interface BasicService<E extends Entity> {

    /**
     * Gets a list of all entities
     * @return List of Entities
     */
    List<E> list();

    /**
     * Get an entity using it's ID
     * @param id ID to get
     * @return Entity found
     */
    E findById(Integer id);

    /**
     * Persists an entity in the repository
     * @param entity Entity to persist
     * @return Persisted entity
     */
    E create(E entity);

    /**
     * Updates an entity in the repository
     * @param id Entity's id to be updated
     * @param entity Entity's info to be updated
     * @return Updated entity
     */
    E update(Integer id, E entity);

    /**
     * Deletes an entity
     * @param id Entity's id to be deleted
     */
    void delete(Integer id);
}
