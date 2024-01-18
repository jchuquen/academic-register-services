package co.grow.plan.academic.register.infrastructure.academicplan.subject.mappers;

import co.grow.plan.academic.register.domain.academicplan.subject.model.Subject;
import co.grow.plan.academic.register.infrastructure.academicplan.subject.entities.SubjectJpaEntity;
import co.grow.plan.academic.register.shared.infrastructure.generics.IBasicInfrastructureVsDomainEntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IInfrastructureVsDomainSubjectEntityMapper
    extends IBasicInfrastructureVsDomainEntityMapper<
            Subject,
            SubjectJpaEntity
        > {
}
