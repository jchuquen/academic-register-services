package co.grow.plan.academic.register.infrastructure.academicplan.subject.mappers;

import co.grow.plan.academic.register.domain.academicplan.subject.model.Subject;
import co.grow.plan.academic.register.infrastructure.academicplan.subject.dtos.SubjectCreationalDto;
import co.grow.plan.academic.register.infrastructure.academicplan.subject.dtos.SubjectFullDto;
import co.grow.plan.academic.register.shared.infrastructure.generics.IBasicInfrastructureDtoVsDomainEntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IInfrastructureDtoVsDomainSubjectMapper
    extends IBasicInfrastructureDtoVsDomainEntityMapper<
        Subject,
        SubjectFullDto,
        SubjectCreationalDto
    > {
}
