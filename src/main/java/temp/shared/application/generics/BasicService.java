package temp.shared.application.generics;

import co.grow.plan.academic.register.shared.exceptions.ApiError;
import co.grow.plan.academic.register.shared.exceptions.ApiNoEntityException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import temp.shared.domain.interfaces.IEntity;

import java.util.List;

import static temp.shared.application.helpers.EntitiesValidationsHelper.validateIdsMatchingOrException;
import static temp.shared.application.helpers.EntitiesValidationsHelper.validateVersionsMatchOrException;
import static temp.shared.helpers.ObjectValidationsHelper.validateNotNull;

@AllArgsConstructor
@NoArgsConstructor
public abstract class BasicService<
    E extends IEntity,
    R extends IBasicRepository
    >
    implements IBasicService<E>
    {

    protected R repository;

    @Override
    public List<E> list() {
        return repository.findAll();
    }

    @Override
    public E findById(Integer id) {
        validateNotNull(id, "ID");
        return validateInstanceIfExistsAndReturn(id);
    }

    @Override
    public E create(E entity) {
        validateNotNull(entity,"Entity");
        entity.validate();
        validateConstrains(null, entity);
        return (E) repository.save(entity);
    }

    @Override
    public E update(Integer id, E entity) {
        validateNotNull(id, "ID");
        validateNotNull(entity,"Entity");

        validateIdsMatchingOrException(
            id, entity.id());

        var persistedEntity = validateInstanceIfExistsAndReturn(id);

        validateVersionsMatchOrException(
            entity.version(), persistedEntity.version());

        entity.validate();

        validateConstrains(id, entity);

        return (E) repository.save(entity);
    }

    @Override
    public void delete(Integer id) {
        validateNotNull(id, "ID");
        validateInstanceIfExistsAndReturn(id);
        repository.deleteById(id);
    }

    // Validations
    protected abstract void validateConstrains(Integer id, E entity);

    private E validateInstanceIfExistsAndReturn(Integer id) {

        var entity = repository.findById(id);

        if (entity == null) {
            throw new ApiNoEntityException(
                new ApiError(
                    String.format(
                        "Instance with id %s doesn't exist",
                        id)
                )
            );
        }
        return (E) entity;
    }
}
