package co.grow.plan.academic.register;

import co.grow.plan.academic.register.application.academicplan.course.ports.api.ICourseServiceAPI;
import co.grow.plan.academic.register.application.academicplan.course.ports.spi.ICourseRepositorySPI;
import co.grow.plan.academic.register.application.academicplan.course.services.CourseService;
import co.grow.plan.academic.register.application.academicplan.period.ports.api.IPeriodServiceAPI;
import co.grow.plan.academic.register.application.academicplan.period.ports.spi.IPeriodRepositorySPI;
import co.grow.plan.academic.register.application.academicplan.period.services.PeriodService;
import co.grow.plan.academic.register.application.academicplan.subject.ports.api.ISubjectServiceAPI;
import co.grow.plan.academic.register.application.academicplan.subject.ports.spi.ISubjectRepositorySPI;
import co.grow.plan.academic.register.application.academicplan.subject.services.SubjectService;
import co.grow.plan.academic.register.application.admissions.identificationtype.ports.api.IIdentificationTypeServiceAPI;
import co.grow.plan.academic.register.application.admissions.identificationtype.ports.spi.IIdentificationTypeRepositorySPI;
import co.grow.plan.academic.register.application.admissions.identificationtype.services.IdentificationTypeService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AcademicRegisterServicesApplication {
    public static void main(String[] args) {
        SpringApplication.run(AcademicRegisterServicesApplication.class, args);
    }

    @Bean
    ICourseServiceAPI courseServiceAPI(ICourseRepositorySPI courseRepositorySPI) {
        return new CourseService(courseRepositorySPI);
    }

    @Bean
    IIdentificationTypeServiceAPI identificationTypeServiceAPI (
        IIdentificationTypeRepositorySPI identificationTypeRepositorySPI) {
        return new IdentificationTypeService(
            identificationTypeRepositorySPI
        );
    }

    @Bean
    ISubjectServiceAPI subjectServiceAPI(ISubjectRepositorySPI subjectRepositorySPI) {
        return new SubjectService(subjectRepositorySPI);
    }

    @Bean
    IPeriodServiceAPI periodServiceAPI(IPeriodRepositorySPI periodRepositorySPI) {
        return new PeriodService(periodRepositorySPI);
    }
}
