package co.grow.plan.academic.register.admissions.identificationtype.infrastructure;

import co.grow.plan.academic.register.admissions.identificationtype.application.IIdentificationTypeService;
import co.grow.plan.academic.register.admissions.identificationtype.application.IdentificationTypeDto;
import co.grow.plan.academic.register.admissions.identificationtype.application.IdentificationTypeNewDto;
import co.grow.plan.academic.register.shared.generics.BasicRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/admissions/identification-types")
public class IdentificationTypeRestController
    extends BasicRestController<
        IdentificationTypeDto,
        IdentificationTypeNewDto,
        IIdentificationTypeService
    >
    implements IIdentificationTypeRest {

    @Autowired
    public IdentificationTypeRestController(IIdentificationTypeService service) {
        super(service);
    }
}
