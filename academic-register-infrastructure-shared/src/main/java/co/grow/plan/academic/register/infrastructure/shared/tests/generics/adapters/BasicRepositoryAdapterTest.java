package co.grow.plan.academic.register.infrastructure.shared.tests.generics.adapters;

import co.grow.plan.academic.register.domain.shared.helpers.AssertionHelper;
import co.grow.plan.academic.register.domain.shared.interfaces.Entity;
import co.grow.plan.academic.register.infrastructure.shared.generics.BasicInfrastructureVsDomainEntityMapper;
import co.grow.plan.academic.register.infrastructure.shared.generics.BasicRepositoryAdapter;
import co.grow.plan.academic.register.infrastructure.shared.generics.InfEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@RequiredArgsConstructor
@Getter
public abstract class BasicRepositoryAdapterTest <
    I extends InfEntity, //The infrastructure entity
    D extends Entity, // The domain entity
    R extends JpaRepository<I, Integer>, // The infrastructure repository
    M extends BasicInfrastructureVsDomainEntityMapper<D,I>,
    A extends BasicRepositoryAdapter<I, D, R, M>
    > {

    private final JdbcTemplate jdbcTemplate;
    private final A repositoryAdapter;

    @BeforeEach
    public void setupDatabase() {
        jdbcTemplate.execute(getRestartAutoincrementSentence());
        jdbcTemplate.execute(getFirstExampleInsertSentence());
        jdbcTemplate.execute(getSecondExampleInsertSentence());
        jdbcTemplate.execute(getThirdExampleInsertSentence());
    }

    @Test
    public void shouldReturnAListOfEntitiesAtFindAll() {
        List<D> expectedList = getEntitiesToList();
        List<D> currentList = repositoryAdapter.findAll();

        assertEquals(expectedList.size(), currentList.size());

        D expected;
        D current;
        for (int i = 0; i < expectedList.size(); i++) {
            expected = expectedList.get(i);
            current = currentList.get(i);

            AssertionHelper.assertObjectProperties(expected, current);
        }
    }

    @Test
    public void shouldReturnTheEntityWhenIdDoesExistAtFindById() {
        D expected = getPersistedEntity();
        D foundEntity =
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
        D entityToCreate = getEntityToCreate();
        D expected = getCreatedEntity();

        D persisted = repositoryAdapter.save(entityToCreate);

        AssertionHelper.assertObjectProperties(expected, persisted);
    }

    @Test
    public void shouldUpdateEntityAndReturnPersistedInformation() {
        Integer id = getIdToUpdateOrDelete();

        D entityToUpdate =
            getEntityWithUpdatedInfo();

        D updatedEntity =
            repositoryAdapter.save(entityToUpdate);

        D expected = getUpdatedEntity();

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
    protected abstract List<D> getEntitiesToList();
    protected abstract D getPersistedEntity();
    protected abstract Integer getWrongIdToUpdateOrDelete();
    protected abstract D getEntityToCreate();
    protected abstract D getCreatedEntity();
    protected abstract Integer getIdToUpdateOrDelete();
    protected abstract D getEntityWithUpdatedInfo();
    protected abstract D getUpdatedEntity();

}
