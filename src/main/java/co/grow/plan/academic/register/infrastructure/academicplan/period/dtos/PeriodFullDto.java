package co.grow.plan.academic.register.infrastructure.academicplan.period.dtos;

import co.grow.plan.academic.register.shared.domain.interfaces.BasicEntity;
import co.grow.plan.academic.register.shared.infrastructure.generics.FullEntityDto;

public record PeriodFullDto(Integer id, String name, Boolean active, Long version)
    implements FullEntityDto, BasicEntity {
}
