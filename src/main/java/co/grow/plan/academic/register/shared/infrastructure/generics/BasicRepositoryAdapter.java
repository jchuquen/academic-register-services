package co.grow.plan.academic.register.shared.infrastructure.generics;

import co.grow.plan.academic.register.shared.application.generics.IBasicRepository;
import co.grow.plan.academic.register.shared.domain.interfaces.IEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
                .infToDomCourseList(repository.findAll());
    }

    @Override
    public D findById(Integer id) {
        var course = repository.getById(id);
        if (course != null) {
            return infrastructureVsDomainMapper
                .infToDomCourse(course);
        } else {
            return null;
        }
    }

    @Override
    public D save(D entity) {
        return infrastructureVsDomainMapper.infToDomCourse(
            repository.save(
                infrastructureVsDomainMapper
                    .domToInfCourse(entity)
            )
        );
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
