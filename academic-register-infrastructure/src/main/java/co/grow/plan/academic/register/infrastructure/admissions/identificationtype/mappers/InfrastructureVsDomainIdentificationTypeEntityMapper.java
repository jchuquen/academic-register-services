package co.grow.plan.academic.register.infrastructure.admissions.identificationtype.mappers;

import co.grow.plan.academic.register.domain.admissions.identificationtype.model.IdentificationType;
import co.grow.plan.academic.register.infrastructure.admissions.identificationtype.entities.IdentificationTypeJpaEntity;
import co.grow.plan.academic.register.infrastructure.shared.generics.BasicInfrastructureVsDomainEntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InfrastructureVsDomainIdentificationTypeEntityMapper
    extends BasicInfrastructureVsDomainEntityMapper<
        IdentificationType,
        IdentificationTypeJpaEntity
    > {
}
