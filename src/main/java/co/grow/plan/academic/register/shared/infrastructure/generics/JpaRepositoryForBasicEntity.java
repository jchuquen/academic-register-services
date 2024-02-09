package co.grow.plan.academic.register.shared.infrastructure.generics;

import java.util.Optional;

public interface JpaRepositoryForBasicEntity<
    E extends InfBasicEntity
    >{
    Optional<E> getByName(String name);
}
