package co.grow.plan.academic.register.shared.application.generics.services;

import co.grow.plan.academic.register.shared.domain.interfaces.IBasicEntity;

import java.util.Optional;

public interface IBasicRepositoryForBasicEntity<E extends IBasicEntity>
    extends IBasicRepository<E>{
    Optional<E> getByName(String name);
}
