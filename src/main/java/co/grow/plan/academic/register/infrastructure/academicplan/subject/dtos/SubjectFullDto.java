package co.grow.plan.academic.register.infrastructure.academicplan.subject.dtos;

import co.grow.plan.academic.register.shared.domain.interfaces.IBasicEntity;
import co.grow.plan.academic.register.shared.infrastructure.generics.IFullEntityDto;

public record SubjectFullDto(Integer id, String name, Long version)
    implements IFullEntityDto, IBasicEntity {
}
