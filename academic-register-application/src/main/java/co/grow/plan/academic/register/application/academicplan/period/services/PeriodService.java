package co.grow.plan.academic.register.application.academicplan.period.services;


import co.grow.plan.academic.register.application.academicplan.period.ports.api.PeriodServiceAPI;
import co.grow.plan.academic.register.application.academicplan.period.ports.spi.PeriodRepositorySPI;
import co.grow.plan.academic.register.application.shared.generics.services.BasicServiceForBasicEntity;
import co.grow.plan.academic.register.domain.academicplan.period.model.Period;

public final class PeriodService
    extends BasicServiceForBasicEntity<Period, PeriodRepositorySPI>
    implements PeriodServiceAPI {

    public PeriodService(PeriodRepositorySPI repository) {
        super(repository);
    }
}
