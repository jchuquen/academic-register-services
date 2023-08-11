package co.grow.plan.academic.register.shared.generics;

import org.mapstruct.MappingTarget;

import java.util.List;

public interface IBaseEntityMapper<
    E, // The entity
    F extends IIdentificableAndVersionable, // The DTO including id and version properties
    N extends INoIdAndVersionEntityDto // The DTO with properties except id and version. Especially to create a new instance
    > {

    // NoIdAndVersionEntityDto To Entity
    E noIdAndVersionDtoToEntity(N dto);

    // Entity to full DTO
    F entityToFullDto(E entity);

    // Full DTO to NoIdAndEntity DTO. Specially to update only valid properties
    N fullDtoToNoIdAndVersionDto(F dto);

    E updateEntityFromNoIdAndVersionDto(@MappingTarget E entity, N dto);

    // List of entities to list of full DTOs
    List<F> enityListToFullDtoList(Iterable<E> list);
}
