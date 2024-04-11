package co.grow.plan.academic.register.application.shared.generics.services;

import co.grow.plan.academic.register.application.shared.exceptions.ApiError;
import co.grow.plan.academic.register.application.shared.exceptions.ApiMissingInformationException;
import co.grow.plan.academic.register.application.shared.exceptions.ApiNoEntityException;
import co.grow.plan.academic.register.application.shared.helpers.EntitiesValidationsHelper;
import co.grow.plan.academic.register.application.shared.helpers.ObjectValidationsHelper;
import co.grow.plan.academic.register.domain.shared.exceptions.EmptyPropertyException;
import co.grow.plan.academic.register.domain.shared.interfaces.Entity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public abstract class BasicServiceImpl<
    E extends Entity,
    R extends BasicRepository<E>
    >
    implements BasicService<E>
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
        return repository.save(entity);
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

        return repository.save(entity);
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
        return entity;
    }
}
