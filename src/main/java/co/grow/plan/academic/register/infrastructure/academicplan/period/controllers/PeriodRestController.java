package co.grow.plan.academic.register.infrastructure.academicplan.period.controllers;

import co.grow.plan.academic.register.application.academicplan.period.ports.api.IPeriodServiceAPI;
import co.grow.plan.academic.register.domain.academicplan.period.model.Period;
import co.grow.plan.academic.register.infrastructure.academicplan.period.dtos.PeriodCreationalDto;
import co.grow.plan.academic.register.infrastructure.academicplan.period.dtos.PeriodFullDto;
import co.grow.plan.academic.register.infrastructure.academicplan.period.mappers.IInfrastructureDtoVsDomainPeriodMapper;
import co.grow.plan.academic.register.shared.infrastructure.generics.BasicRestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/academic-plans/periods")
public class PeriodRestController
    extends BasicRestController<
    PeriodFullDto,
    PeriodCreationalDto,
    Period,
    IPeriodServiceAPI,
    IInfrastructureDtoVsDomainPeriodMapper
    >
    implements IPeriodRestController {

    public PeriodRestController(IPeriodServiceAPI service, IInfrastructureDtoVsDomainPeriodMapper mapper) {
        super(service, mapper);
    }
}
