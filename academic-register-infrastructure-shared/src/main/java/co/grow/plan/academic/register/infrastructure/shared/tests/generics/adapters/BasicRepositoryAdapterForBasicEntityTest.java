package co.grow.plan.academic.register.infrastructure.shared.tests.generics.adapters;

import co.grow.plan.academic.register.domain.shared.interfaces.BasicEntity;
import co.grow.plan.academic.register.infrastructure.shared.generics.BasicInfrastructureVsDomainEntityMapper;
import co.grow.plan.academic.register.infrastructure.shared.generics.BasicRepositoryAdapterForBasicEntity;
import co.grow.plan.academic.register.infrastructure.shared.generics.InfBasicEntity;
import co.grow.plan.academic.register.infrastructure.shared.generics.JpaRepositoryForBasicEntity;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class BasicRepositoryAdapterForBasicEntityTest<
    I extends InfBasicEntity, //The infrastructure entity
    D extends BasicEntity, // The domain entity
    R extends JpaRepositoryForBasicEntity<I> & JpaRepository<I, Integer>, // The infrastructure repository
    M extends BasicInfrastructureVsDomainEntityMapper<D,I>,
    A extends BasicRepositoryAdapterForBasicEntity<I, D, R, M>
    >
    extends BasicRepositoryAdapterTest<I, D, R, M, A>{

    public BasicRepositoryAdapterForBasicEntityTest(
        JdbcTemplate jdbcTemplate,
        A repositoryAdapter
    ) {
        super(jdbcTemplate, repositoryAdapter);
    }

    @Test
    public void shouldReturnEmptyOptionalWhenEntityDoesNotExist() {
        Optional<D> optionalEntity =
            this.getRepositoryAdapter().getByName(getWrongName());

        assertEquals(optionalEntity.isPresent(), false);
    }

    @Test
    public void shouldReturnTheEntityWhenEntityDoesExist() {
        Optional<D> optionalEntity =
            this.getRepositoryAdapter().getByName(getExistingName());

        assertEquals(optionalEntity.isPresent(), true);
    }

    // Templates to implement
    protected abstract String getWrongName();
    protected abstract String getExistingName();
}
