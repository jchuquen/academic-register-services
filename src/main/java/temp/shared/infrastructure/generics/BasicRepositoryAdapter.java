package temp.shared.infrastructure.generics;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import temp.shared.application.generics.IBasicRepository;
import temp.shared.domain.interfaces.IEntity;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class BasicRepositoryAdapter <
    I extends IInfEntity, //The infrastructure entity
    D extends IEntity, // The domain entity
    R extends JpaRepository<I, Integer>, // The infrastructure repository
    M extends IBasicInfrastructureVsDomainEntityMapper<D,I>
    >
    implements IBasicRepository<D> {

    protected R repository;
    protected M infrastructureVsDomainMapper;

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
