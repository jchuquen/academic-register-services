package co.grow.plan.academic.register.infrastructure.admissions.identificationtype.dtos;

import co.grow.plan.academic.register.shared.domain.interfaces.BasicEntity;
import co.grow.plan.academic.register.shared.infrastructure.generics.FullEntityDto;

public record IdentificationTypeFullDto(Integer id, String name, Long version)
    implements FullEntityDto, BasicEntity {
}
