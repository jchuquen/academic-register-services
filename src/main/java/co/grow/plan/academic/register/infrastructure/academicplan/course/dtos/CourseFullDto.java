package co.grow.plan.academic.register.infrastructure.academicplan.course.dtos;

import co.grow.plan.academic.register.shared.infrastructure.generics.IFullEntityDto;

public record CourseFullDto (int id, String name, long version)
    implements IFullEntityDto {
}
