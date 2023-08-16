package co.grow.plan.academic.register.academicplan.subject.application;

import co.grow.plan.academic.register.academicplan.subject.domain.Subject;
import co.grow.plan.academic.register.shared.generics.IBasicMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ISubjectMapper
    extends IBasicMapper<
        Subject,
        SubjectDto,
        SubjectNewDto
    > {
}