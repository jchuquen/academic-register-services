package co.grow.plan.academic.register.infrastructure.academicplan.course.dtos;

import co.grow.plan.academic.register.shared.domain.interfaces.BasicEntity;
import co.grow.plan.academic.register.shared.infrastructure.generics.FullEntityDto;

public record CourseFullDto (Integer id, String name, Long version)
    implements FullEntityDto, BasicEntity {
}
