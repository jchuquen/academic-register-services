package co.grow.plan.academic.register.application.shared.generics.services;

import co.grow.plan.academic.register.domain.shared.interfaces.BasicEntity;

import java.util.Optional;

public interface BasicRepositoryForBasicEntity<E extends BasicEntity>
    extends BasicRepository<E> {
    Optional<E> getByName(String name);
}
