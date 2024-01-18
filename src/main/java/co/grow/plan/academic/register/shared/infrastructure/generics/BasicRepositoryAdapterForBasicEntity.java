package co.grow.plan.academic.register.shared.infrastructure.generics;

import co.grow.plan.academic.register.shared.domain.interfaces.IBasicEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public abstract class BasicRepositoryAdapterForBasicEntity<
        I extends IInfBasicEntity, //The infrastructure entity
        D extends IBasicEntity, // The domain entity
        R extends IJpaRepositoryForBasicEntity<I> & JpaRepository<I, Integer>, // The infrastructure repository
        M extends IBasicInfrastructureVsDomainEntityMapper<D,I>
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
