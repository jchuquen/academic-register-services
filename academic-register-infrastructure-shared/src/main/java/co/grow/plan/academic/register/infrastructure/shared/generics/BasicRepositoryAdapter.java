package co.grow.plan.academic.register.infrastructure.shared.generics;

import co.grow.plan.academic.register.application.shared.generics.services.BasicRepository;
import co.grow.plan.academic.register.domain.shared.interfaces.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@AllArgsConstructor
@Getter
@Slf4j
public class BasicRepositoryAdapter <
    I extends InfEntity, //The infrastructure entity
    D extends Entity, // The domain entity
    R extends JpaRepository<I, Integer>, // The infrastructure repository
    M extends BasicInfrastructureVsDomainEntityMapper<D,I>
    >
    implements BasicRepository<D> {

    private final R repository;
    private final M infrastructureVsDomainMapper;

    @Override
    public List<D> findAll() {

        log.debug("Listing Entities using BasicRepositoryAdapter");

        return
            infrastructureVsDomainMapper
                .infToDomEntityList(repository.findAll());
    }

    @Override
    public D findById(Integer id) {

        log.debug(String.format("Finding entity by id %s using BasicRepositoryAdapter", id));

        var entity = repository.findById(id);
        return entity.map(infrastructureVsDomainMapper::infToDomEntity).orElse(null);
    }

    @Override
    public D save(D entity) {

        log.debug("Saving entity using BasicRepositoryAdapter");

        return infrastructureVsDomainMapper.infToDomEntity(
            repository.save(
                infrastructureVsDomainMapper
                    .domToInfEntity(entity)
            )
        );
    }

    @Override
    public void deleteById(Integer id) {

        log.debug(String.format("Updating entity with id %s using BasicRepositoryAdapter", id));

        repository.deleteById(id);
    }
}
