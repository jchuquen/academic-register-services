package co.grow.plan.academic.register.shared.application.generics.services;

import co.grow.plan.academic.register.shared.application.exceptions.ApiConflictException;
import co.grow.plan.academic.register.shared.application.exceptions.ApiMissingInformationException;
import co.grow.plan.academic.register.shared.domain.interfaces.IBasicEntity;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public abstract class BasicServiceForBasicEntityTest<
    E extends IBasicEntity,
    R extends IBasicRepositoryForBasicEntity<E>,
    S extends BasicServiceForBasicEntity<E, R>
    >
    extends BasicServiceTest <E, R, S>{

    @Test
    public void shouldGenerateExceptionWhenNameIsNullAtCreate() {
        assertThrows(
            ApiMissingInformationException.class,
            () -> getService().create(getEntityWithNullFields())
        );
    }

    @Test
    public void shouldGenerateExceptionWhenNameExistsAtCreate() {

        E entityToCreate = getEntityToCreate();

        E conflictedEntity = getPersistedEntity();

        when(
            getRepository().getByName(entityToCreate.name())
        ).thenReturn(Optional.of(conflictedEntity));

        assertThrows(
            ApiConflictException.class,
            () -> getService().create(
                entityToCreate
            )
        );

        verify(getRepository(), times(1)).
            getByName(entityToCreate.name());
    }

    @Test
    public void shouldGenerateExceptionWhenNameNullAtUpdating() {
        Integer id = getIdToUpdateOrDelete();

        E entityWithNullName = getEntityWithUpdatedInfo(PropertyError.NULL_NAME);

        E persistedEntity = getPersistedEntity();

        when(
            getRepository().findById(id)
        ).thenReturn(persistedEntity);

        assertThrows(
            ApiMissingInformationException.class,
            () -> getService().update(
                id, entityWithNullName)
        );

        verify(getRepository(), times(1)).
            findById(id);
    }

    @Test
    public void shouldGenerateExceptionWhenNameIsEmptyAtUpdating() {
        Integer id = getIdToUpdateOrDelete();

        E entityWithEmptyName = getEntityWithUpdatedInfo(PropertyError.EMPTY_NAME);

        E persistedEntity = getPersistedEntity();

        when(
            getRepository().findById(id)
        ).thenReturn(persistedEntity);

        assertThrows(
            ApiMissingInformationException.class,
            () -> getService().update(
                id, entityWithEmptyName)
        );

        verify(getRepository(), times(1)).
            findById(id);
    }

    @Test
    public void shouldGenerateExceptionWhenNameExistsAtUpdating() {
        Integer id = getIdToUpdateOrDelete();

        E entityWithUpdatedInfo = getEntityWithUpdatedInfo(PropertyError.NONE);

        E persistedEntity = getPersistedEntity();

        E entityWithSameName = getEntityWithDuplicatedName();

        when(
            getRepository().findById(id)
        ).thenReturn(persistedEntity);

        when(
            getRepository().getByName(entityWithUpdatedInfo.name())
        ).thenReturn(Optional.of(entityWithSameName));

        assertThrows(
            ApiConflictException.class,
            () -> getService().update(
                id, entityWithUpdatedInfo)
        );

        verify(getRepository(), times(1)).
            findById(id);

        verify(getRepository(), times(1)).
            getByName(entityWithUpdatedInfo.name());
    }

    // Templates to implement
    protected abstract E getEntityWithDuplicatedName();
}
