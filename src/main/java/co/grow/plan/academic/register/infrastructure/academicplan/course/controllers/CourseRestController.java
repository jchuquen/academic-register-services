package co.grow.plan.academic.register.infrastructure.academicplan.course.controllers;

import co.grow.plan.academic.register.application.academicplan.course.ports.api.ICourseServiceAPI;
import co.grow.plan.academic.register.domain.academicplan.course.model.Course;
import co.grow.plan.academic.register.infrastructure.academicplan.course.dtos.CourseCreationalDto;
import co.grow.plan.academic.register.infrastructure.academicplan.course.dtos.CourseFullDto;
import co.grow.plan.academic.register.infrastructure.academicplan.course.mappers.InfrastructureDtoVsDomainCourseMapper;
import co.grow.plan.academic.register.shared.infrastructure.generics.BasicRestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/academic-plans/courses")
public class CourseRestController
    extends BasicRestController<
    CourseFullDto,
    CourseCreationalDto,
    Course,
    ICourseServiceAPI,
    InfrastructureDtoVsDomainCourseMapper
    >
    implements ICourseRestController{

    public CourseRestController(ICourseServiceAPI service, InfrastructureDtoVsDomainCourseMapper mapper) {
        super(service, mapper);
    }
}
