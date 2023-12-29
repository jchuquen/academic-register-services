package co.grow.plan.academic.register.shared.application.generics;

import co.grow.plan.academic.register.shared.application.exceptions.ApiBadInformationException;
import co.grow.plan.academic.register.shared.application.exceptions.ApiConflictException;
import co.grow.plan.academic.register.shared.application.exceptions.ApiMissingInformationException;
import co.grow.plan.academic.register.shared.application.exceptions.ApiNoEntityException;
import co.grow.plan.academic.register.shared.domain.interfaces.IBasicEntity;
import co.grow.plan.academic.register.shared.helpers.AssertionHelper;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@AllArgsConstructor
public abstract class BasicServiceTest<
    E extends IBasicEntity,
    R extends IBasicRepositoryForBasicEntity<E>,
    S extends BasicServiceForBasicEntity<E, R>
    > {

    @Test
    public void shouldReturnAListOfEntitiesAtList() {

        List<E> mockedList = getMockedEntitiesToList();

        when(getRepository().findAll()).thenReturn(mockedList);

        var currentList = getService().list();

        assertEquals(mockedList.size(), currentList.size());

        E expected;
        E current;

        for (int i = 0; i < mockedList.size(); i++) {
            expected = mockedList.get(i);
            current = currentList.get(i);

            AssertionHelper.assertObjectProperties(expected, current);
        }

        verify(getRepository(), times(1)).findAll();
    }



    @Test
    public void shouldReturnAnEmptyListOfEntitiesAtList() {

        List<E> mockedList = List.of();

        when(getRepository().findAll()).thenReturn(mockedList);

        List<E> currentList = getService().list();

        assertIterableEquals(mockedList, currentList);
        verify(getRepository(), times(1))
            .findAll();
    }

    @Test
    public void shouldGenerateExceptionWhenEntityIsNullAtCreate() {
        assertThrows(
            ApiBadInformationException.class,
            () -> getService().create(null)
        );
    }

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
    public void shouldCreateNewEntityAndReturnPersistedInformation() {

        E entityToCreate = getEntityToCreate();

        E expected = getPersistedEntity();

        when(
            getRepository().save(entityToCreate)
        ).thenReturn(expected);

        E persisted =
            getService().create(entityToCreate);

        AssertionHelper.assertObjectProperties(
            expected, persisted);

        verify(getRepository(), times(1)).
            save(any());
    }

    @Test
    public void shouldGenerateExceptionWhenIdNullAtFindById() {
        assertThrows(
            ApiBadInformationException.class,
            () -> getService().findById(null)
        );
    }

    @Test
    public void shouldGenerateExceptionWhenIdDoesNotExistAtFindById() {
        assertThrows(
            ApiNoEntityException.class,
            () -> getService().findById(9)
        );
    }

    @Test
    public void shouldReturnTheEntityWhenIdDoesExistAtFindById() {

        E persistedEntity = getPersistedEntity();

        when(
            getRepository().findById(1)
        ).thenReturn(persistedEntity);

        E foundEntity =
            getService().findById(1);

        AssertionHelper.assertObjectProperties(
            persistedEntity, foundEntity);

        verify(getRepository(), times(1)).
            findById(1);
    }

    @Test
    public void shouldGenerateExceptionWhenIdNullAtUpdating () {
        assertThrows(
            ApiBadInformationException.class,
            () -> getService().update(
                null, null)
        );
    }

    @Test
    public void shouldGenerateExceptionWhenObjectNullAtUpdating() {
        assertThrows(
            ApiBadInformationException.class,
            () -> getService().update(
                5, null)
        );
    }

    @Test
    public void shouldGenerateExceptionWhenIdsDoesNotMatchAtUpdating() {
        Integer id = getWrongIdToUpdateOrDelete();

        E entityWithUpdatedInfo = getEntityWithUpdatedInfo(PropertyError.NONE);

        assertThrows(
            ApiBadInformationException.class,
            () -> getService().update(
                id, entityWithUpdatedInfo)
        );
    }

    @Test
    public void shouldGenerateExceptionWhenIdDoesNotExistAtUpdating() {
        Integer id = getIdToUpdateOrDelete();
        E entityWithUpdatedInfo = getEntityWithUpdatedInfo(PropertyError.NONE);

        assertThrows(
            ApiNoEntityException.class,
            () -> getService().update(
                id, entityWithUpdatedInfo)
        );
    }

    @Test
    public void shouldGenerateExceptionWhenVersionsDoesNotMatchAtUpdating() {
        Integer id = getIdToUpdateOrDelete();

        E entityWithUpdatedInfo = getEntityWithUpdatedInfo(PropertyError.WRONG_VERSION);

        E persistedEntity = getPersistedEntity();

        when(
            getRepository().findById(id)
        ).thenReturn(persistedEntity);

        assertThrows(
            ApiConflictException.class,
            () -> getService().update(
                id, entityWithUpdatedInfo)
        );

        verify(getRepository(), times(1)).
            findById(id);
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



    @Test
    public void shouldUpdateEntityAndReturnPersistedInformation() {
        Integer id = getIdToUpdateOrDelete();

        E entityToUpdate = getEntityWithUpdatedInfo(PropertyError.NONE);

        E persistedEntity = getPersistedEntity();

        when(
            getRepository().findById(id)
        ).thenReturn(persistedEntity);

        E updatedEntityFromRepo = getUpdatedEntity();

        when(
            getRepository().save(entityToUpdate)
        ).thenReturn(updatedEntityFromRepo);

        E updatedEntityFromService =
            getService().update(
                id, entityToUpdate);

        AssertionHelper.assertObjectProperties(
            updatedEntityFromRepo, updatedEntityFromService);

        verify(getRepository(), times(1)).
            findById(id);

        verify(getRepository(), times(1)).
            save(any());
    }

    @Test
    public void shouldGenerateExceptionWhenIdNullAtDeleting() {
        assertThrows(
            ApiBadInformationException.class,
            () -> getService().delete(null)
        );
    }

    @Test
    public void shouldGenerateExceptionWhenIdDoesNotExistAtDeleting() {
        assertThrows(
            ApiNoEntityException.class,
            () -> getService().delete(7)
        );
    }

    @Test
    public void shouldDeleteTheEntity() {
        Integer id = getIdToUpdateOrDelete();

        E entityToDelete = getPersistedEntity();

        when(
            getRepository().findById(id)
        ).thenReturn(entityToDelete);

        getService().delete(id);

        verify(getRepository(), times(1)).
            findById(id);

        assertTimeoutPreemptively(Duration.ofSeconds(3),
            () -> getService().delete(id),
            "Should execute in less than 3 seconds");
    }

    protected abstract R getRepository();
    protected abstract S getService();

    // Templates to implement
    protected abstract List<E> getMockedEntitiesToList();
    protected abstract E getEntityWithNullFields();
    protected abstract E getEntityToCreate();
    protected abstract E getPersistedEntity();
    protected abstract Integer getIdToUpdateOrDelete();
    protected abstract Integer getWrongIdToUpdateOrDelete();
    protected abstract E getEntityWithUpdatedInfo(PropertyError propertyError);
    protected abstract E getEntityWithDuplicatedName();
    protected abstract E getUpdatedEntity();

}
