package co.grow.plan.academic.register.infrastructure.admissions.identificationtype.dtos;

import co.grow.plan.academic.register.shared.infrastructure.generics.IFullEntityDto;

public record IdentificationTypeFullDto(Integer id, String name, Long version)
    implements IFullEntityDto {
}
