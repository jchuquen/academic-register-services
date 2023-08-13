package co.grow.plan.academic.register.shared.generics;

import org.mapstruct.MappingTarget;

import java.util.List;

public interface IBasicMapper<
    E extends IIdentifiableAndVersionable, // The entity
    F extends IIdentifiableAndVersionable, // The DTO including id and version properties
    N extends INoIdentifiableAndVersionable // The DTO with properties except id and version. Especially to create a new instance
    > {

    // NoIdAndVersionEntityDto To Entity
    E noIdentifiableAndVersionableDtoToEntity(N dto);

    // Entity to full DTO|
    F entityToIdentifiableAndVersionableDto(E entity);

    // Full DTO to NoIdAndEntity DTO. Specially to update only valid properties
    N identifiableAndVersionableDtoToNoIdentifiableAndVersionableDto(F dto);

    E updateEntityFromNoIdentifiableAndVersionableDto(
        @MappingTarget E entity, N dto);

    // List of entities to list of full DTOs
    List<F> entityListToIdentifiableAndVersionableDtoList(Iterable<E> list);
}
