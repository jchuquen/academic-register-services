package co.grow.plan.academic.register.shared.infrastructure.generics;

import co.grow.plan.academic.register.shared.domain.interfaces.BasicEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public abstract class BasicRepositoryAdapterForBasicEntity<
        I extends InfBasicEntity, //The infrastructure entity
        D extends BasicEntity, // The domain entity
        R extends JpaRepositoryForBasicEntity<I> & JpaRepository<I, Integer>, // The infrastructure repository
        M extends BasicInfrastructureVsDomainEntityMapper<D,I>
    > extends BasicRepositoryAdapter<I, D, R, M>{

    public BasicRepositoryAdapterForBasicEntity(
        R repository,
        M mapper
    ) {
        super(repository, mapper);
    }

    public Optional<D> getByName(String name) {
        var optionalEntity = this.getRepository().getByName(name);
        if (optionalEntity.isPresent()) {
            return Optional.of(
                this.getInfrastructureVsDomainMapper().infToDomEntity(optionalEntity.get())
            );
        } else {
            return Optional.empty();
        }
    }
}
