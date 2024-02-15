package co.grow.plan.academic.register.infrastructure.academicplan.subject.mappers;

import co.grow.plan.academic.register.domain.academicplan.subject.model.Subject;
import co.grow.plan.academic.register.infrastructure.academicplan.subject.entities.SubjectJpaEntity;
import co.grow.plan.academic.register.infrastructure.shared.generics.BasicInfrastructureVsDomainEntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InfrastructureVsDomainSubjectEntityMapper
    extends BasicInfrastructureVsDomainEntityMapper<
            Subject,
            SubjectJpaEntity
        > {
}
