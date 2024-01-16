package co.grow.plan.academic.register.shared.infrastructure.generics.adapters;

import co.grow.plan.academic.register.shared.application.generics.services.IBasicRepository;
import co.grow.plan.academic.register.shared.domain.interfaces.IBasicEntity;
import co.grow.plan.academic.register.shared.helpers.AssertionHelper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@RequiredArgsConstructor
public abstract class BasicRepositoryAdapterTest <
    E extends IBasicEntity,
    R extends IBasicRepository<E>
    > {

    private final JdbcTemplate jdbcTemplate;
    private final R repositoryAdapter;

    @BeforeEach
    public void setupDatabase() {
        jdbcTemplate.execute(getRestartAutoincrementSentence());
        jdbcTemplate.execute(getFirstExampleInsertSentence());
        jdbcTemplate.execute(getSecondExampleInsertSentence());
        jdbcTemplate.execute(getThirdExampleInsertSentence());
    }

    @Test
    public void shouldReturnAListOfEntitiesAtFindAll() {
        List<E> expectedList = getEntitiesToList();
        List<E> currentList = repositoryAdapter.findAll();

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
    public void shouldReturnTheEntityWhenIdDoesExistAtFindById() {
        E expected = getPersistedEntity();
        E foundEntity =
            repositoryAdapter.findById(expected.id());

        AssertionHelper.assertObjectProperties(expected, foundEntity);
    }

    @Test
    public void shouldReturnNullWhenIdDoesNotExistAtFindById() {
        assertNull(repositoryAdapter.findById(
            getWrongIdToUpdateOrDelete()
        ));
    }

    @Test
    public void shouldCreateNewEntityAndReturnPersistedInformation() {
        E entityToCreate = getEntityToCreate();
        E expected = getCreatedEntity();

        E persisted = repositoryAdapter.save(entityToCreate);

        AssertionHelper.assertObjectProperties(expected, persisted);
    }

    @Test
    public void shouldUpdateEntityAndReturnPersistedInformation() {
        Integer id = getIdToUpdateOrDelete();

        E entityToUpdate =
            getEntityWithUpdatedInfo();

        E updatedEntity =
            repositoryAdapter.save(entityToUpdate);

        E expected = getUpdatedEntity();

        AssertionHelper.assertObjectProperties(expected, updatedEntity);
    }

    @Test
    public void shouldDeleteTheEntity() {
        Integer id = getIdToUpdateOrDelete();
        repositoryAdapter.deleteById(id);
        assertNull(repositoryAdapter.findById(id));
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
    protected abstract E getPersistedEntity();
    protected abstract Integer getWrongIdToUpdateOrDelete();
    protected abstract E getEntityToCreate();
    protected abstract E getCreatedEntity();
    protected abstract Integer getIdToUpdateOrDelete();
    protected abstract E getEntityWithUpdatedInfo();
    protected abstract E getUpdatedEntity();

}
