package co.grow.plan.academic.register.infrastructure.academicplan.course.dtos;

import co.grow.plan.academic.register.shared.infrastructure.generics.ICreationalDto;

public record CourseCreationalDto(String name)
    implements ICreationalDto {
}
