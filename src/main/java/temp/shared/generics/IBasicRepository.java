package temp.shared.generics;

import java.util.List;
import java.util.Optional;

public interface IBasicRepository<E extends IEntity> {

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
    Optional<E> findById(Integer id);

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
