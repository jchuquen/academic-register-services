package co.grow.plan.academic.register.infrastructure.shared.generics;

import co.grow.plan.academic.register.domain.shared.interfaces.Entity;

import java.util.List;

public interface BasicInfrastructureDtoVsDomainEntityMapper<
    E extends Entity, // The domain entity
    F extends FullEntityDto, // The full DTO
    C extends CreationalDto // The DTO without ID and Version
    > {

    F domainEntityToFullDto(E domainEntity);

    List<F> domainEntitiesToFullDtos(List<E> domainEntitiesList);

    E creationalDtoToDomainEntity(C creationalDto);

    E fullDtoToDomainEntity(F fullDto);
}
