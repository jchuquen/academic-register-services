package co.grow.plan.academic.register.application.admissions.identificationtype.services;

import co.grow.plan.academic.register.application.admissions.identificationtype.ports.api.IIdentificationTypeServiceAPI;
import co.grow.plan.academic.register.application.admissions.identificationtype.ports.spi.IIdentificationTypeRepositorySPI;
import co.grow.plan.academic.register.domain.admissions.identificationtype.model.IdentificationType;
import co.grow.plan.academic.register.shared.application.generics.BasicServiceForBasicEntity;

public final class IdentificationTypeService
    extends BasicServiceForBasicEntity<
        IdentificationType,
        IIdentificationTypeRepositorySPI
    >
    implements IIdentificationTypeServiceAPI {

    public IdentificationTypeService(IIdentificationTypeRepositorySPI repository) {
        super(repository);
    }
}
