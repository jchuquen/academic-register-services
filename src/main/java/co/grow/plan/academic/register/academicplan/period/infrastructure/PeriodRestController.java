package co.grow.plan.academic.register.academicplan.period.infrastructure;

import co.grow.plan.academic.register.academicplan.period.application.IPeriodService;
import co.grow.plan.academic.register.academicplan.period.application.PeriodDto;
import co.grow.plan.academic.register.academicplan.period.application.PeriodNewDto;
import co.grow.plan.academic.register.shared.generics.BasicRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/academic-plans/periods")
public class PeriodRestController
    extends BasicRestController<
        PeriodDto,
        PeriodNewDto,
        IPeriodService
    >
    implements IPeriodRest {

    @Autowired
    public PeriodRestController(IPeriodService service) {
        super(service);
    }
}
