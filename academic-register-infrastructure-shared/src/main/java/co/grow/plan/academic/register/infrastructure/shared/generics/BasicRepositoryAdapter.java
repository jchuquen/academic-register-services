package co.grow.plan.academic.register.infrastructure.shared.generics;

import co.grow.plan.academic.register.application.shared.generics.services.BasicRepository;
import co.grow.plan.academic.register.domain.shared.interfaces.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@AllArgsConstructor
@Getter
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
        return
            infrastructureVsDomainMapper
                .infToDomEntityList(repository.findAll());
    }

    @Override
    public D findById(Integer id) {
        var entity = repository.findById(id);
        return entity.map(infrastructureVsDomainMapper::infToDomEntity).orElse(null);
    }

    @Override
    public D save(D entity) {
        return infrastructureVsDomainMapper.infToDomEntity(
            repository.save(
                infrastructureVsDomainMapper
                    .domToInfEntity(entity)
            )
        );
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
