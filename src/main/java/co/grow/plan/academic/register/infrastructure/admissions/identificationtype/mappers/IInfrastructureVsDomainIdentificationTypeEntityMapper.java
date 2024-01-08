package co.grow.plan.academic.register.infrastructure.admissions.identificationtype.mappers;

import co.grow.plan.academic.register.domain.admissions.identificationtype.model.IdentificationType;
import co.grow.plan.academic.register.infrastructure.admissions.identificationtype.entities.IdentificationTypeJpaEntity;
import co.grow.plan.academic.register.shared.infrastructure.generics.IBasicInfrastructureVsDomainEntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IInfrastructureVsDomainIdentificationTypeEntityMapper
    extends IBasicInfrastructureVsDomainEntityMapper<
        IdentificationType,
        IdentificationTypeJpaEntity
    > {
}
