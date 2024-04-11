package co.grow.plan.academic.register.infrastructure.academicplan.subject.mappers;

import co.grow.plan.academic.register.domain.academicplan.subject.model.Subject;
import co.grow.plan.academic.register.infrastructure.academicplan.subject.dtos.SubjectCreationalDto;
import co.grow.plan.academic.register.infrastructure.academicplan.subject.dtos.SubjectFullDto;
import co.grow.plan.academic.register.infrastructure.shared.generics.BasicInfrastructureDtoVsDomainEntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InfrastructureDtoVsDomainSubjectMapper
    extends BasicInfrastructureDtoVsDomainEntityMapper<
        Subject,
        SubjectFullDto,
        SubjectCreationalDto
    > {
}
