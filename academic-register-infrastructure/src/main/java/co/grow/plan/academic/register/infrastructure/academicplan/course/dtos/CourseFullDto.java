package co.grow.plan.academic.register.infrastructure.academicplan.course.dtos;

import co.grow.plan.academic.register.domain.shared.interfaces.BasicEntity;
import co.grow.plan.academic.register.infrastructure.shared.generics.FullEntityDto;

public record CourseFullDto (Integer id, String name, Long version)
    implements FullEntityDto, BasicEntity {
}
