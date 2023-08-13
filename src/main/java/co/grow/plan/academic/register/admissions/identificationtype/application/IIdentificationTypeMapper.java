package co.grow.plan.academic.register.admissions.identificationtype.application;

import co.grow.plan.academic.register.admissions.identificationtype.domain.IdentificationType;
import co.grow.plan.academic.register.shared.generics.IBasicMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IIdentificationTypeMapper
    extends IBasicMapper<
        IdentificationType,
        IdentificationTypeDto,
        IdentificationTypeNewDto
    > {
}
