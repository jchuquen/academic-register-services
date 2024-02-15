package co.grow.plan.academic.register.infrastructure.academicplan.period.dtos;

import co.grow.plan.academic.register.infrastructure.shared.generics.CreationalDto;

public record PeriodCreationalDto(String name, Boolean active)
    implements CreationalDto {
}
