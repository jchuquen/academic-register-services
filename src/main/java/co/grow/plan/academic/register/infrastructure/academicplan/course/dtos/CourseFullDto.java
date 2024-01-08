package co.grow.plan.academic.register.infrastructure.academicplan.course.dtos;

import co.grow.plan.academic.register.shared.infrastructure.generics.IFullEntityDto;

public record CourseFullDto (Integer id, String name, Long version)
    implements IFullEntityDto {
}
