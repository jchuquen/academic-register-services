package co.grow.plan.academic.register.infrastructure.academicplan.period.controllers;

import co.grow.plan.academic.register.infrastructure.academicplan.period.dtos.PeriodCreationalDto;
import co.grow.plan.academic.register.infrastructure.academicplan.period.dtos.PeriodFullDto;
import co.grow.plan.academic.register.shared.infrastructure.generics.IBasicRestController;

public interface IPeriodRestController
    extends IBasicRestController<PeriodFullDto, PeriodCreationalDto> {
}
