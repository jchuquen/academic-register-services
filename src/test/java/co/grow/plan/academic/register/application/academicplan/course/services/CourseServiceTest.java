package co.grow.plan.academic.register.application.academicplan.course.services;

import co.grow.plan.academic.register.application.academicplan.course.ports.spi.ICourseRepositorySPI;
import co.grow.plan.academic.register.domain.academicplan.course.model.Course;
import co.grow.plan.academic.register.shared.application.exceptions.ApiBadInformationException;
import co.grow.plan.academic.register.shared.application.exceptions.ApiConflictException;
import co.grow.plan.academic.register.shared.domain.exceptions.ApiMissingInformationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public final class CourseServiceTest {

    @Mock
    private ICourseRepositorySPI repositorySPI;

    @InjectMocks
    private CourseService courseService;

    @Test
    public void shouldReturnAListOfCourses() {

        List<Course> courseList =
            List.of(
                new Course(1, "Software Development", 0),
                new Course(2, "Microprocessors", 1),
                new Course(3, "Pure Maths", 0)
            );

        when(repositorySPI.findAll()).thenReturn(courseList);

        List<Course> expectedList =
            List.of(
                new Course(1, "Software Development", 0),
                new Course(2, "Microprocessors", 1),
                new Course(3, "Pure Maths", 0)
            );


        var currentList = courseService.list();

        assertEquals(expectedList.size(), currentList.size());

        Course expected;
        Course current;

        for (int i = 0; i < expectedList.size(); i++) {
            expected = expectedList.get(i);
            current = currentList.get(i);

            assertObjectProperties(expected, current);
        }

        verify(repositorySPI, times(1)).findAll();
    }

    @Test
    public void shouldReturnAnEmptyListOfCourses() {

        List<Course> courseList =
            List.of();

        when(repositorySPI.findAll()).thenReturn(courseList);

        List<Course> expectedList =
            List.of();

        List<Course> currentList =
            courseService.list();

        assertIterableEquals(expectedList, currentList);
        verify(repositorySPI, times(1)).findAll();
    }

    @Test
    public void shouldGenerateExceptionWhenCourseIsNull() {
        assertThrows(
            ApiBadInformationException.class,
            () -> courseService.create(null)
        );
    }

    @Test
    public void shouldGenerateExceptionWhenNameIsNull() {
        assertThrows(
            ApiMissingInformationException.class,
            () -> courseService.create(
                new Course(1,null, 0)
            )
        );
    }

    @Test
    public void shouldGenerateExceptionWhenNameExists() {

        Course course =
            new Course(0, "Software Development", 0);

        Course courseConflict =
            new Course(99, "Software Development", 15);

        when(
            repositorySPI.getByName(course.name())
        ).thenReturn(Optional.of(courseConflict));

        assertThrows(
            ApiConflictException.class,
            () -> courseService.create(
                course
            )
        );

        verify(repositorySPI, times(1)).
            getByName(course.name());
    }

    @Test
    @DisplayName("CourseServiceTest - Create - Must create new record and " +
        "return created information")
    public void shouldCreateNewCourseAndReturnPersistedInformation() {

        Course course = new Course(
            1, "Software Development", 0
        );

        Course expected = new Course(
            15, "Software Development", 0);

        when(
            repositorySPI.save(any(Course.class))
        ).thenReturn(expected);

        Course persisted =
            courseService.create(course);

        assertObjectProperties(expected, persisted);

        verify(repositorySPI, times(1)).
            save(any(Course.class));
    }

    // Utilities
    private static void assertObjectProperties(
        Course expected,Course current) {

        assertEquals(expected.id(), current.id());
        assertEquals(expected.name(), current.name());
        assertEquals(expected.version(), current.version());
    }
}
