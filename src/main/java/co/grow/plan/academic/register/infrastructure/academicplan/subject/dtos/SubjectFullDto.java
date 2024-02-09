package co.grow.plan.academic.register.infrastructure.academicplan.subject.dtos;

import co.grow.plan.academic.register.shared.domain.interfaces.BasicEntity;
import co.grow.plan.academic.register.shared.infrastructure.generics.FullEntityDto;

public record SubjectFullDto(Integer id, String name, Long version)
    implements FullEntityDto, BasicEntity {
}
