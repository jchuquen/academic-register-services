package co.grow.plan.academic.register.infrastructure.shared.tests.generics.services;

import co.grow.plan.academic.register.application.shared.exceptions.ApiConflictException;
import co.grow.plan.academic.register.application.shared.exceptions.ApiNoEntityException;
import co.grow.plan.academic.register.application.shared.generics.services.BasicRepository;
import co.grow.plan.academic.register.application.shared.generics.services.BasicServiceImpl;
import co.grow.plan.academic.register.application.shared.tests.services.PropertyError;
import co.grow.plan.academic.register.domain.shared.helpers.AssertionHelper;
import co.grow.plan.academic.register.domain.shared.interfaces.Entity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
@Getter
public abstract class BasicServiceIntegrationTest<
    E extends Entity,
    R extends BasicRepository<E>,
    S extends BasicServiceImpl<E, R>
    > {

    private final JdbcTemplate jdbcTemplate;
    private final S service;

    @BeforeEach
    public void setupDatabase() {
        jdbcTemplate.execute(getRestartAutoincrementSentence());
        jdbcTemplate.execute(getFirstExampleInsertSentence());
        jdbcTemplate.execute(getSecondExampleInsertSentence());
        jdbcTemplate.execute(getThirdExampleInsertSentence());
    }

    @Test
    public void shouldReturnAListOfEntitiesAtList() {
        List<E> expectedList = getEntitiesToList();
        List<E> currentList = service.list();

        assertEquals(expectedList.size(), currentList.size());

        E expected;
        E current;
        for (int i = 0; i < expectedList.size(); i++) {
            expected = expectedList.get(i);
            current = currentList.get(i);

            AssertionHelper.assertObjectProperties(expected, current);
        }
    }

    @Test
    public void shouldReturnAnEmptyListOfEntitiesAtList() {
        jdbcTemplate.execute(getDeleteAllSentence());

        List<E> expectedList = List.of();
        List<E> currentList = service.list();

        assertIterableEquals(expectedList, currentList);
    }

    @Test
    public void shouldCreateNewEntityAndReturnPersistedInformation() {
        E entityToCreate = getEntityToCreate();
        E expected = getCreatedEntity();
        E persisted = service.create(entityToCreate);

        AssertionHelper.assertObjectProperties(
            expected, persisted);
    }



    @Test
    public void shouldGenerateExceptionWhenIdDoesNotExistAtFindById() {
        assertThrows(
            ApiNoEntityException.class,
            () -> service.findById(9)
        );
    }

    @Test
    public void shouldReturnTheEntityWhenIdDoesExistAtFindById() {
        E expected = getPersistedEntity();
        E foundEntity = service.findById(expected.id());

        AssertionHelper.assertObjectProperties(expected, foundEntity);
    }

    @Test
    public void shouldGenerateExceptionWhenIdDoesNotExistAtUpdating() {
        Integer id = getWrongIdToUpdateOrDelete();
        E entityWithUpdatedInfo = getWrongEntityWithUpdatedInfo();

        assertThrows(
            ApiNoEntityException.class,
            () -> service.update(id, entityWithUpdatedInfo)
        );
    }

    @Test
    public void shouldGenerateExceptionWhenVersionsDoesNotMatchAtUpdating() {
        Integer id = getIdToUpdateOrDelete();

        E entityWithUpdatedInfo =
            getEntityWithUpdatedInfo(PropertyError.WRONG_VERSION);

        assertThrows(
            ApiConflictException.class,
            () -> service.update(
                id, entityWithUpdatedInfo)
        );
    }

    @Test
    public void shouldUpdateEntityAndReturnPersistedInformation() {
        Integer id = getIdToUpdateOrDelete();

        E entityToUpdate =
            getEntityWithUpdatedInfo(PropertyError.NONE);

        E updatedEntity =
            service.update(
                id, entityToUpdate);

        E expected = getUpdatedEntity();

        AssertionHelper.assertObjectProperties(
            expected, updatedEntity);
    }

    @Test
    public void shouldDeleteTheEntity() {
        Integer id = getIdToUpdateOrDelete();

        service.delete(id);

        assertThrows(
            ApiNoEntityException.class,
            () -> service.findById(3)
        );

    }

    @AfterEach
    public void clearDatabase() {
        jdbcTemplate.execute(getDeleteAllSentence());
    }

    // SQL sentences
    protected abstract String getRestartAutoincrementSentence();
    protected abstract String getFirstExampleInsertSentence();
    protected abstract String getSecondExampleInsertSentence();
    protected abstract String getThirdExampleInsertSentence();
    protected abstract String getDeleteAllSentence();

    // Templates to implement
    protected abstract List<E> getEntitiesToList();
    protected abstract E getConflictedEntityAtCreate();
    protected abstract E getEntityToCreate();
    protected abstract E getCreatedEntity();
    protected abstract E getPersistedEntity();
    protected abstract Integer getWrongIdToUpdateOrDelete();
    protected abstract E getWrongEntityWithUpdatedInfo();
    protected abstract E getConflictedEntityAtUpdating();
    protected abstract E getEntityWithUpdatedInfo(PropertyError propertyError);
    protected abstract Integer getIdToUpdateOrDelete();
    protected abstract E getUpdatedEntity();
}

