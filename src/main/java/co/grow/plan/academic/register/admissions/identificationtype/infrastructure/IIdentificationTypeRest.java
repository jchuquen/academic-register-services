package co.grow.plan.academic.register.admissions.identificationtype.infrastructure;

import co.grow.plan.academic.register.admissions.identificationtype.application.IdentificationTypeDto;
import co.grow.plan.academic.register.admissions.identificationtype.application.IdentificationTypeNewDto;
import co.grow.plan.academic.register.shared.generics.IBasicRestController;

public interface IIdentificationTypeRest
    extends IBasicRestController<IdentificationTypeDto, IdentificationTypeNewDto> {

}
