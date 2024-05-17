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
import java.util.logging.Level;
import java.util.logging.Logger;

@RequiredArgsConstructor
@Getter
public abstract class BasicServiceImpl<
    E extends Entity,
    R extends BasicRepository<E>
>
implements BasicService<E>
{

    private static final Logger logger = Logger.getLogger(BasicServiceImpl.class.getName());
    public static final String INSTANCE_DOES_NOT_EXIST = "Instance with id %s doesn't exist";
    public static final String ID = "ID";
    public static final String ENTITY = "Entity";

    private final R repository;

    @Override
    public List<E> list() {

        logger.log(Level.INFO, "Listing entities using BasicServiceImpl");

        return repository.findAll();
    }

    @Override
    public E findById(Integer id) {

        logger.log(Level.INFO,
            String.format(
                "Finding entity by id %s using BasicServiceImpl",
                id
            )
        );

        ObjectValidationsHelper.validateNotNull(id, ID);
        return validateInstanceIfExistsAndReturn(id);
    }

    @Override
    public E create(E entity) {
        logger.log(Level.INFO, "Creating entity using BasicServiceImpl");
        ObjectValidationsHelper.validateNotNull(entity, ENTITY);
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

        logger.log(Level.INFO,
            String.format(
                "Updating entity by id %s using BasicServiceImpl",
                id
            )
        );

        ObjectValidationsHelper.validateNotNull(id, ID);
        ObjectValidationsHelper.validateNotNull(entity,ENTITY);

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

        logger.log(Level.INFO,
            String.format(
                "Deleting entity by id %s using BasicServiceImpl",
                id
            )
        );

        ObjectValidationsHelper.validateNotNull(id, ID);
        validateInstanceIfExistsAndReturn(id);
        repository.deleteById(id);
    }

    // Validations
    protected abstract void validateConstrains(Integer id, E entity);

    private E validateInstanceIfExistsAndReturn(Integer id) {

        logger.log(Level.INFO,
            String.format(
                "Validating entity existence by id %s using BasicServiceImpl",
                id
            )
        );

        var entity = repository.findById(id);

        if (entity == null) {
            throw new ApiNoEntityException(
                new ApiError(
                    String.format(
                        INSTANCE_DOES_NOT_EXIST,
                        id
                    )
                )
            );
        }
        return entity;
    }
}
