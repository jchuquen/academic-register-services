package co.grow.plan.academic.register.shared.application.generics.services;

import co.grow.plan.academic.register.shared.application.exceptions.ApiConflictException;
import co.grow.plan.academic.register.shared.application.exceptions.ApiMissingInformationException;
import co.grow.plan.academic.register.shared.domain.interfaces.IBasicEntity;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class BasicServiceIntegrationForBasicEntityTest<
        E extends IBasicEntity,
        R extends IBasicRepositoryForBasicEntity<E>,
        S extends BasicServiceForBasicEntity<E, R>
    > extends BasicServiceIntegrationTest<E, R, S>
    {

    public BasicServiceIntegrationForBasicEntityTest (
        JdbcTemplate jdbcTemplate,
        S service
    ){
        super(jdbcTemplate, service);
    }

    @Test
    public void shouldGenerateExceptionWhenNameExistsAtCreate() {
        E conflictedEntity = getConflictedEntityAtCreate();

        assertThrows(
            ApiConflictException.class,
            () -> this.getService().create(conflictedEntity)
        );
    }

    @Test
    public void shouldGenerateExceptionWhenNameNullAtUpdating() {
        Integer id = getIdToUpdateOrDelete();

        E entityWithNullName =
            getEntityWithUpdatedInfo(PropertyError.NULL_NAME);

        assertThrows(
            ApiMissingInformationException.class,
            () -> this.getService().update(
                id, entityWithNullName)
        );
    }

    @Test
    public void shouldGenerateExceptionWhenNameIsEmptyAtUpdating() {
        Integer id = getIdToUpdateOrDelete();

        E entityWithEmptyName =
            getEntityWithUpdatedInfo(PropertyError.EMPTY_NAME);

        assertThrows(
            ApiMissingInformationException.class,
            () -> this.getService().update(
                id, entityWithEmptyName)
        );
    }

    @Test
    public void shouldGenerateExceptionWhenNameExistsAtUpdating() {
        Integer id = getIdToUpdateOrDelete();

        E entityWithUpdatedInfo =
            getConflictedEntityAtUpdating();

        assertThrows(
            ApiConflictException.class,
            () -> this.getService().update(
                id, entityWithUpdatedInfo)
        );
    }
}

