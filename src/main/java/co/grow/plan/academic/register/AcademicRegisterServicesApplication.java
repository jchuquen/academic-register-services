package co.grow.plan.academic.register;

import co.grow.plan.academic.register.application.academicplan.course.ports.api.ICourseServiceAPI;
import co.grow.plan.academic.register.application.academicplan.course.ports.spi.ICourseRepositorySPI;
import co.grow.plan.academic.register.application.academicplan.course.services.CourseService;
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
}
