package co.grow.plan.academic.register.academicplan.course.infrastructure;

import co.grow.plan.academic.register.academicplan.course.application.CourseDto;
import co.grow.plan.academic.register.academicplan.course.application.CourseNewDto;
import co.grow.plan.academic.register.shared.generics.IBasicRestController;

public interface ICourseRest
    extends IBasicRestController<CourseDto, CourseNewDto> {

}
