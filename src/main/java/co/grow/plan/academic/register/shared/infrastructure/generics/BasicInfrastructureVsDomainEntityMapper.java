package co.grow.plan.academic.register.shared.infrastructure.generics;

import co.grow.plan.academic.register.shared.domain.interfaces.Entity;

import java.util.List;

public interface BasicInfrastructureVsDomainEntityMapper<
    D extends Entity,
    I extends InfEntity> {

    D infToDomEntity(I infEntity);

    List<D> infToDomEntityList(List<I> list);

    I domToInfEntity(D entity);
}
