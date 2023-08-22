package co.grow.plan.academic.register.academicplan.course.infrastructure;

import co.grow.plan.academic.register.academicplan.course.application.CourseDto;
import co.grow.plan.academic.register.academicplan.course.application.CourseNewDto;
import co.grow.plan.academic.register.academicplan.course.application.ICourseService;
import co.grow.plan.academic.register.shared.generics.BasicRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/academic-plans/courses")
public class CourseRestController
    extends BasicRestController<
        CourseDto,
        CourseNewDto,
        ICourseService
    >
    implements ICourseRest {

    @Autowired
    public CourseRestController(ICourseService service) {
        super(service);
    }
}
