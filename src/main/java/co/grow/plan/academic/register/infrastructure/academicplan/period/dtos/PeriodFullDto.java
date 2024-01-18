package co.grow.plan.academic.register.infrastructure.academicplan.period.dtos;

import co.grow.plan.academic.register.shared.domain.interfaces.IBasicEntity;
import co.grow.plan.academic.register.shared.infrastructure.generics.IFullEntityDto;

public record PeriodFullDto(Integer id, String name, Boolean active, Long version)
    implements IFullEntityDto, IBasicEntity {
}
