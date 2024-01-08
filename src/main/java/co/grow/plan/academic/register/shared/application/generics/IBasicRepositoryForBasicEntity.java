package co.grow.plan.academic.register.shared.application.generics;

import co.grow.plan.academic.register.shared.domain.interfaces.IEntity;

import java.util.Optional;

public interface IBasicRepositoryForBasicEntity<E extends IEntity>
    extends IBasicRepository<E>{
    Optional<E> getByName(String name);
}
