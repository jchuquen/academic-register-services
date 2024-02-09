package co.grow.plan.academic.register.shared.application.generics.services;

import co.grow.plan.academic.register.shared.domain.interfaces.BasicEntity;

import java.util.Optional;

public interface BasicRepositoryForBasicEntity<E extends BasicEntity>
    extends BasicRepository<E> {
    Optional<E> getByName(String name);
}
