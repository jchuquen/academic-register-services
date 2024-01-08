package co.grow.plan.academic.register.infrastructure.academicplan.course.controllers;

import co.grow.plan.academic.register.infrastructure.academicplan.course.dtos.CourseCreationalDto;
import co.grow.plan.academic.register.infrastructure.academicplan.course.dtos.CourseFullDto;
import co.grow.plan.academic.register.shared.infrastructure.generics.IBasicRestController;

public interface ICourseRestController
    extends IBasicRestController<CourseFullDto, CourseCreationalDto> {
}
