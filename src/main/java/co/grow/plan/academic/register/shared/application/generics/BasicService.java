package co.grow.plan.academic.register.shared.application.generics;

import co.grow.plan.academic.register.shared.application.exceptions.ApiError;
import co.grow.plan.academic.register.shared.application.exceptions.ApiMissingInformationException;
import co.grow.plan.academic.register.shared.application.exceptions.ApiNoEntityException;
import co.grow.plan.academic.register.shared.application.helpers.EntitiesValidationsHelper;
import co.grow.plan.academic.register.shared.domain.exceptions.EmptyPropertyException;
import co.grow.plan.academic.register.shared.domain.interfaces.IEntity;
import co.grow.plan.academic.register.shared.helpers.ObjectValidationsHelper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public abstract class BasicService<
    E extends IEntity,
    R extends IBasicRepository<E>
    >
    implements IBasicService<E>
    {

    private final R repository;

    @Override
    public List<E> list() {
        return repository.findAll();
    }

    @Override
    public E findById(Integer id) {
        ObjectValidationsHelper.validateNotNull(id, "ID");
        return validateInstanceIfExistsAndReturn(id);
    }

    @Override
    public E create(E entity) {
        ObjectValidationsHelper.validateNotNull(entity,"Entity");
        try {
            entity.validate();
        } catch (EmptyPropertyException e) {
            throw new ApiMissingInformationException(
                new ApiError(e.getMessage())
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        validateConstrains(null, entity);
        return (E) repository.save(entity);
    }

    @Override
    public E update(Integer id, E entity) {
        ObjectValidationsHelper.validateNotNull(id, "ID");
        ObjectValidationsHelper.validateNotNull(entity,"Entity");

        EntitiesValidationsHelper.validateIdsMatchingOrException(
            id, entity.id());

        var persistedEntity = validateInstanceIfExistsAndReturn(id);

        EntitiesValidationsHelper.validateVersionsMatchOrException(
            entity.version(), persistedEntity.version());

        try {
            entity.validate();
        } catch (EmptyPropertyException e) {
            throw new ApiMissingInformationException(
                new ApiError(e.getMessage())
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        validateConstrains(id, entity);

        return (E) repository.save(entity);
    }

    @Override
    public void delete(Integer id) {
        ObjectValidationsHelper.validateNotNull(id, "ID");
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
