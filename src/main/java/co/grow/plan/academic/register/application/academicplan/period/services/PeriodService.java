package co.grow.plan.academic.register.application.academicplan.period.services;


import co.grow.plan.academic.register.application.academicplan.period.ports.api.IPeriodServiceAPI;
import co.grow.plan.academic.register.application.academicplan.period.ports.spi.IPeriodRepositorySPI;
import co.grow.plan.academic.register.domain.academicplan.period.model.Period;
import co.grow.plan.academic.register.shared.application.generics.services.BasicServiceForBasicEntity;

public final class PeriodService
    extends BasicServiceForBasicEntity<Period, IPeriodRepositorySPI>
    implements IPeriodServiceAPI {

    public PeriodService(IPeriodRepositorySPI repository) {
        super(repository);
    }
}
