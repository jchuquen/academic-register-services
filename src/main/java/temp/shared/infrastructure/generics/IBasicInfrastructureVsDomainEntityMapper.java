package temp.shared.infrastructure.generics;

import temp.shared.domain.interfaces.IEntity;

import java.util.List;

public interface IBasicInfrastructureVsDomainEntityMapper<
    D extends IEntity,
    I extends IInfEntity> {

    D infToDomCourse(I infEntity);

    List<D> infToDomCourseList(List<I> list);

    I domToInfCourse(D entity);
}
