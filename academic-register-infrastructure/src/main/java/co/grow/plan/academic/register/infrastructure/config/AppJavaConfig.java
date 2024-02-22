package co.grow.plan.academic.register.infrastructure.config;

import co.grow.plan.academic.register.application.academicplan.course.ports.api.CourseServiceAPI;
import co.grow.plan.academic.register.application.academicplan.course.ports.spi.CourseRepositorySPI;
import co.grow.plan.academic.register.application.academicplan.course.services.CourseService;
import co.grow.plan.academic.register.application.academicplan.period.ports.api.PeriodServiceAPI;
import co.grow.plan.academic.register.application.academicplan.period.ports.spi.PeriodRepositorySPI;
import co.grow.plan.academic.register.application.academicplan.period.services.PeriodService;
import co.grow.plan.academic.register.application.academicplan.subject.ports.api.SubjectServiceAPI;
import co.grow.plan.academic.register.application.academicplan.subject.ports.spi.SubjectRepositorySPI;
import co.grow.plan.academic.register.application.academicplan.subject.services.SubjectService;
import co.grow.plan.academic.register.application.admissions.identificationtype.ports.api.IdentificationTypeServiceAPI;
import co.grow.plan.academic.register.application.admissions.identificationtype.ports.spi.IdentificationTypeRepositorySPI;
import co.grow.plan.academic.register.application.admissions.identificationtype.services.IdentificationTypeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppJavaConfig {
    @Bean
    CourseServiceAPI courseServiceAPI(CourseRepositorySPI courseRepositorySPI) {
        return new CourseService(courseRepositorySPI);
    }

    @Bean
    IdentificationTypeServiceAPI identificationTypeServiceAPI (
        IdentificationTypeRepositorySPI identificationTypeRepositorySPI) {
        return new IdentificationTypeService(
            identificationTypeRepositorySPI
        );
    }

    @Bean
    SubjectServiceAPI subjectServiceAPI(SubjectRepositorySPI subjectRepositorySPI) {
        return new SubjectService(subjectRepositorySPI);
    }

    @Bean
    PeriodServiceAPI periodServiceAPI(PeriodRepositorySPI periodRepositorySPI) {
        return new PeriodService(periodRepositorySPI);
    }
}
