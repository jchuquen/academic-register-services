package co.grow.plan.academic.register.shared.infrastructure.generics;

import co.grow.plan.academic.register.shared.domain.interfaces.IEntity;

import java.util.List;

public interface IBasicInfrastructureVsDomainEntityMapper<
    D extends IEntity,
    I extends IInfEntity> {

    D infToDomCourse(I infEntity);

    List<D> infToDomCourseList(List<I> list);

    I domToInfCourse(D entity);
}
