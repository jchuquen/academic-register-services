package co.grow.plan.academic.register.shared.infrastructure.generics;

import co.grow.plan.academic.register.shared.domain.interfaces.IEntity;

import java.util.List;

public interface IBasicInfrastructureDtoVsDomainEntityMapper<
    E extends IEntity, // The domain entity
    F extends IFullEntityDto, // The full DTO
    C extends ICreationalDto // The DTO without ID and Version
    > {

    F domainEntityToFullDto(E domainEntity);

    List<F> domainEntitiesToFullDtos(List<E> domainEntitiesList);

    E creationalDtoToDomainEntity(C creationalDto);

    E fullDtoToDomainEntity(F fullDto);
}
