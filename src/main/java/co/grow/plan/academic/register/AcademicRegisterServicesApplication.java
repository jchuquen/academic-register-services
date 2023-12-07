package co.grow.plan.academic.register;

import co.grow.plan.academic.register.application.academicplan.course.ports.api.ICourseServiceAPI;
import co.grow.plan.academic.register.application.academicplan.course.ports.spi.ICourseRepositorySPI;
import co.grow.plan.academic.register.application.academicplan.course.services.CourseService;
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
}
