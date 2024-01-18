package co.grow.plan.academic.register.shared.infrastructure.generics;

import java.util.Optional;

public interface IJpaRepositoryForBasicEntity <
    E extends IInfBasicEntity
    >{
    Optional<E> getByName(String name);
}
