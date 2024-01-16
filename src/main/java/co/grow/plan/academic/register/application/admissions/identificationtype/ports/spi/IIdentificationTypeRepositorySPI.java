package co.grow.plan.academic.register.application.admissions.identificationtype.ports.spi;

import co.grow.plan.academic.register.domain.admissions.identificationtype.model.IdentificationType;
import co.grow.plan.academic.register.shared.application.generics.services.IBasicRepositoryForBasicEntity;

public interface IIdentificationTypeRepositorySPI
    extends IBasicRepositoryForBasicEntity<IdentificationType> {
}
