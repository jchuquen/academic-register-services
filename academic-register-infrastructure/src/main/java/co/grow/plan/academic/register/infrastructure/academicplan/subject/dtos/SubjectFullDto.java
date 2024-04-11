package co.grow.plan.academic.register.infrastructure.academicplan.subject.dtos;

import co.grow.plan.academic.register.domain.shared.interfaces.BasicEntity;
import co.grow.plan.academic.register.infrastructure.shared.generics.FullEntityDto;

public record SubjectFullDto(Integer id, String name, Long version)
    implements FullEntityDto, BasicEntity {
}
