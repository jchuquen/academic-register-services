package co.grow.plan.academic.register.application.admissions.identificationtype.services;

import co.grow.plan.academic.register.application.admissions.identificationtype.ports.api.IdentificationTypeServiceAPI;
import co.grow.plan.academic.register.application.admissions.identificationtype.ports.spi.IdentificationTypeRepositorySPI;
import co.grow.plan.academic.register.domain.admissions.identificationtype.model.IdentificationType;
import co.grow.plan.academic.register.shared.application.generics.services.BasicServiceForBasicEntity;

public final class IdentificationTypeService
    extends BasicServiceForBasicEntity<
        IdentificationType,
    IdentificationTypeRepositorySPI
    >
    implements IdentificationTypeServiceAPI {

    public IdentificationTypeService(IdentificationTypeRepositorySPI repository) {
        super(repository);
    }
}
