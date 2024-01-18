package co.grow.plan.academic.register.infrastructure.academicplan.period.dtos;

import co.grow.plan.academic.register.shared.infrastructure.generics.ICreationalDto;

public record PeriodCreationalDto(String name, Boolean active)
    implements ICreationalDto {
}
