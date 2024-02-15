package co.grow.plan.academic.register.infrastructure.admissions.identificationtype.controllers;

import co.grow.plan.academic.register.application.admissions.identificationtype.ports.api.IdentificationTypeServiceAPI;
import co.grow.plan.academic.register.domain.admissions.identificationtype.model.IdentificationType;
import co.grow.plan.academic.register.infrastructure.admissions.identificationtype.dtos.IdentificationTypeCreationalDto;
import co.grow.plan.academic.register.infrastructure.admissions.identificationtype.dtos.IdentificationTypeFullDto;
import co.grow.plan.academic.register.infrastructure.admissions.identificationtype.mappers.InfrastructureDtoVsDomainIdentificationTypeMapper;
import co.grow.plan.academic.register.infrastructure.shared.generics.BasicRestControllerImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/admissions/identification-types")
public class IdentificationTypeRestControllerImpl
    extends BasicRestControllerImpl<
        IdentificationTypeFullDto,
        IdentificationTypeCreationalDto,
        IdentificationType,
        IdentificationTypeServiceAPI,
        InfrastructureDtoVsDomainIdentificationTypeMapper
    >
    implements IdentificationTypeRestController {

    public IdentificationTypeRestControllerImpl(
        IdentificationTypeServiceAPI service,
        InfrastructureDtoVsDomainIdentificationTypeMapper mapper) {
        super(service, mapper);
    }
}
