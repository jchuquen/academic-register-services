package co.grow.plan.academic.register.shared.infrastructure.generics;

import co.grow.plan.academic.register.shared.application.generics.services.IBasicRepository;
import co.grow.plan.academic.register.shared.domain.interfaces.IEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@AllArgsConstructor
@Getter
public class BasicRepositoryAdapter <
    I extends IInfEntity, //The infrastructure entity
    D extends IEntity, // The domain entity
    R extends JpaRepository<I, Integer>, // The infrastructure repository
    M extends IBasicInfrastructureVsDomainEntityMapper<D,I>
    >
    implements IBasicRepository<D> {

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
