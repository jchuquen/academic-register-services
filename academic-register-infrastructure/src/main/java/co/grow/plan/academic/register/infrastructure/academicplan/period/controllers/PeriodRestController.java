package co.grow.plan.academic.register.infrastructure.academicplan.period.controllers;

import co.grow.plan.academic.register.infrastructure.academicplan.period.dtos.PeriodCreationalDto;
import co.grow.plan.academic.register.infrastructure.academicplan.period.dtos.PeriodFullDto;
import co.grow.plan.academic.register.infrastructure.shared.generics.BasicRestController;

public interface PeriodRestController
    extends BasicRestController<PeriodFullDto, PeriodCreationalDto> {
}
