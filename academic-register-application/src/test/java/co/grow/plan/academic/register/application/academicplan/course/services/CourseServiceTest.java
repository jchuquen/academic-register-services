package co.grow.plan.academic.register.application.academicplan.course.services;

import co.grow.plan.academic.register.application.academicplan.course.ports.spi.CourseRepositorySPI;
import co.grow.plan.academic.register.application.shared.tests.services.BasicServiceForBasicEntityTest;
import co.grow.plan.academic.register.application.shared.tests.services.PropertyError;
import co.grow.plan.academic.register.domain.academicplan.course.model.Course;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest extends BasicServiceForBasicEntityTest<
    Course,
    CourseRepositorySPI,
    CourseService
    > {

    @Mock
    private CourseRepositorySPI repositorySPI;

    @InjectMocks
    private CourseService courseService;

    @Override
    protected CourseRepositorySPI getRepository() {
        return repositorySPI;
    }

    @Override
    protected CourseService getService() {
        return courseService;
    }

    @Override
    protected List<Course> getMockedEntitiesToList() {
        return List.of(
            new Course(1, "Software Development", 0L),
            new Course(2, "Microprocessors", 1L),
            new Course(3, "Pure Maths", 0L)
        );
    }

    @Override
    protected Course getEntityWithNullFields() {
        return new Course(null,null, null);
    }

    @Override
    protected Course getEntityToCreate() {
        return new Course(null, "Software Development", null);
    }

    @Override
    protected Course getPersistedEntity() {
        return new Course(1, "Software Development", 0L);
    }

    @Override
    protected Integer getIdToUpdateOrDelete() {
        return 1;
    }

    @Override
    protected Integer getWrongIdToUpdateOrDelete() {
        return 22;
    }

    @Override
    protected Course getEntityWithUpdatedInfo(PropertyError propertyError) {
        String name = "Software Architecture";
        Long version = 0L;

        switch (propertyError) {
            case WRONG_VERSION -> version = 17L;
            case EMPTY_NAME -> name = "   ";
            case NULL_NAME -> name = null;
        }
        return new Course(1, name, version);
    }

    @Override
    protected Course getEntityWithDuplicatedName() {
        return new Course(13, "Software Architecture", 5L);
    }

    @Override
    protected Course getUpdatedEntity() {
        return new Course(1, "Software Architecture", 1L);
    }
}
