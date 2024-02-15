package co.grow.plan.academic.register.infrastructure.academicplan.period.controllers;

import co.grow.plan.academic.register.application.academicplan.period.ports.api.PeriodServiceAPI;
import co.grow.plan.academic.register.domain.academicplan.period.model.Period;
import co.grow.plan.academic.register.infrastructure.academicplan.period.dtos.PeriodCreationalDto;
import co.grow.plan.academic.register.infrastructure.academicplan.period.dtos.PeriodFullDto;
import co.grow.plan.academic.register.infrastructure.academicplan.period.mappers.InfrastructureDtoVsDomainPeriodMapper;
import co.grow.plan.academic.register.infrastructure.shared.generics.BasicRestControllerImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/academic-plans/periods")
public class PeriodRestControllerImpl
    extends BasicRestControllerImpl<
        PeriodFullDto,
        PeriodCreationalDto,
        Period,
        PeriodServiceAPI,
        InfrastructureDtoVsDomainPeriodMapper
    >
    implements PeriodRestController {

    public PeriodRestControllerImpl(PeriodServiceAPI service, InfrastructureDtoVsDomainPeriodMapper mapper) {
        super(service, mapper);
    }
}
