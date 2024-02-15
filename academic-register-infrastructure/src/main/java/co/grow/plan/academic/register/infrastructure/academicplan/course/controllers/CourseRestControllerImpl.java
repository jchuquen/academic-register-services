package co.grow.plan.academic.register.infrastructure.academicplan.course.controllers;

import co.grow.plan.academic.register.application.academicplan.course.ports.api.CourseServiceAPI;
import co.grow.plan.academic.register.domain.academicplan.course.model.Course;
import co.grow.plan.academic.register.infrastructure.academicplan.course.dtos.CourseCreationalDto;
import co.grow.plan.academic.register.infrastructure.academicplan.course.dtos.CourseFullDto;
import co.grow.plan.academic.register.infrastructure.academicplan.course.mappers.InfrastructureDtoVsDomainCourseMapper;
import co.grow.plan.academic.register.infrastructure.shared.generics.BasicRestControllerImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/academic-plans/courses")
public class CourseRestControllerImpl
    extends BasicRestControllerImpl<
        CourseFullDto,
        CourseCreationalDto,
        Course,
        CourseServiceAPI,
        InfrastructureDtoVsDomainCourseMapper
    >
    implements CourseRestController {

    public CourseRestControllerImpl(CourseServiceAPI service, InfrastructureDtoVsDomainCourseMapper mapper) {
        super(service, mapper);
    }
}
