package co.grow.plan.academic.register.shared.infrastructure.generics.adapters;

import co.grow.plan.academic.register.shared.domain.interfaces.IBasicEntity;
import co.grow.plan.academic.register.shared.infrastructure.generics.BasicRepositoryAdapterForBasicEntity;
import co.grow.plan.academic.register.shared.infrastructure.generics.IBasicInfrastructureVsDomainEntityMapper;
import co.grow.plan.academic.register.shared.infrastructure.generics.IInfBasicEntity;
import co.grow.plan.academic.register.shared.infrastructure.generics.IJpaRepositoryForBasicEntity;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class BasicRepositoryAdapterForBasicEntityTest<
    I extends IInfBasicEntity, //The infrastructure entity
    D extends IBasicEntity, // The domain entity
    R extends IJpaRepositoryForBasicEntity<I> & JpaRepository<I, Integer>, // The infrastructure repository
    M extends IBasicInfrastructureVsDomainEntityMapper<D,I>,
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
