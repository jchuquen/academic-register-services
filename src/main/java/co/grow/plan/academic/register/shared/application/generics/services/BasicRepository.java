package co.grow.plan.academic.register.shared.application.generics.services;

import co.grow.plan.academic.register.shared.domain.interfaces.Entity;

import java.util.List;

public interface BasicRepository<E extends Entity> {

    /**
     * Gets a list of all entities
     * @return List of Entities
     */
    List<E> findAll();

    /**
     * Gets an entity using it's ID
     * @param id ID to get
     * @return Entity found
     */
    E findById(Integer id);

    /**
     * Persists or updates an entity
     * @param entity Entity to persist
     * @return Persisted entity
     */
    E save (E entity);

    /**
     * Deletes an entity
     * @param id ID to delete
     */
    void deleteById(Integer id);
}
