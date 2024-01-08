package co.grow.plan.academic.register.infrastructure.admissions.identificationtype.mappers;

import co.grow.plan.academic.register.domain.admissions.identificationtype.model.IdentificationType;
import co.grow.plan.academic.register.infrastructure.admissions.identificationtype.dtos.IdentificationTypeCreationalDto;
import co.grow.plan.academic.register.infrastructure.admissions.identificationtype.dtos.IdentificationTypeFullDto;
import co.grow.plan.academic.register.shared.infrastructure.generics.IBasicInfrastructureDtoVsDomainEntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IInfrastructureDtoVsDomainIdentificationTypeMapper
    extends IBasicInfrastructureDtoVsDomainEntityMapper<
        IdentificationType,
        IdentificationTypeFullDto,
        IdentificationTypeCreationalDto
    > {
}
