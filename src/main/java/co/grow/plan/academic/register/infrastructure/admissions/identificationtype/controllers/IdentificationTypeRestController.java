package co.grow.plan.academic.register.infrastructure.admissions.identificationtype.controllers;

import co.grow.plan.academic.register.application.admissions.identificationtype.ports.api.IIdentificationTypeServiceAPI;
import co.grow.plan.academic.register.domain.admissions.identificationtype.model.IdentificationType;
import co.grow.plan.academic.register.infrastructure.admissions.identificationtype.dtos.IdentificationTypeCreationalDto;
import co.grow.plan.academic.register.infrastructure.admissions.identificationtype.dtos.IdentificationTypeFullDto;
import co.grow.plan.academic.register.infrastructure.admissions.identificationtype.mappers.InfrastructureDtoVsDomainIdentificationTypeMapper;
import co.grow.plan.academic.register.shared.infrastructure.generics.BasicRestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/admissions/identification-types")
public class IdentificationTypeRestController
    extends BasicRestController<
    IdentificationTypeFullDto,
    IdentificationTypeCreationalDto,
    IdentificationType,
    IIdentificationTypeServiceAPI,
    InfrastructureDtoVsDomainIdentificationTypeMapper
    >
    implements IIdentificationTypeRestController {

    public IdentificationTypeRestController(
        IIdentificationTypeServiceAPI service,
        InfrastructureDtoVsDomainIdentificationTypeMapper mapper) {
        super(service, mapper);
    }
}
