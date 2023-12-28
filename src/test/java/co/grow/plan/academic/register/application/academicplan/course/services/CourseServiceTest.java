package co.grow.plan.academic.register.application.academicplan.course.services;

import co.grow.plan.academic.register.application.academicplan.course.ports.spi.ICourseRepositorySPI;
import co.grow.plan.academic.register.domain.academicplan.course.model.Course;
import co.grow.plan.academic.register.shared.application.exceptions.ApiBadInformationException;
import co.grow.plan.academic.register.shared.application.exceptions.ApiConflictException;
import co.grow.plan.academic.register.shared.application.exceptions.ApiNoEntityException;
import co.grow.plan.academic.register.shared.application.exceptions.ApiMissingInformationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
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
                new Course(1, "Software Development", 0L),
                new Course(2, "Microprocessors", 1L),
                new Course(3, "Pure Maths", 0L)
            );

        when(repositorySPI.findAll()).thenReturn(courseList);

        List<Course> expectedList =
            List.of(
                new Course(1, "Software Development", 0L),
                new Course(2, "Microprocessors", 1L),
                new Course(3, "Pure Maths", 0L)
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

        List<Course> courseList = List.of();

        when(repositorySPI.findAll()).thenReturn(courseList);

        List<Course> expectedList = List.of();

        List<Course> currentList = courseService.list();

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
                new Course(null,null, null)
            )
        );
    }

    @Test
    public void shouldGenerateExceptionWhenNameExists() {

        Course course =
            new Course(null, "Software Development", null);

        Course courseConflict =
            new Course(99, "Software Development", 15L);

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
    public void shouldCreateNewCourseAndReturnPersistedInformation() {

        Course course = new Course(
            null, "Software Development", null);

        Course expected = new Course(
            15, "Software Development", 0L);

        when(
            repositorySPI.save(course)
        ).thenReturn(expected);

        Course persisted =
            courseService.create(course);

        assertObjectProperties(expected, persisted);

        verify(repositorySPI, times(1)).
            save(any(Course.class));
    }

    @Test
    public void shouldGenerateExceptionWhenIdNullAtFindById() {
        assertThrows(
            ApiBadInformationException.class,
            () -> courseService.findById(null)
        );
    }

    @Test
    public void shouldGenerateExceptionWhenIdDoesNotExistAtFindById() {
        assertThrows(
            ApiNoEntityException.class,
            () -> courseService.findById(9)
        );
    }

    @Test
    public void shouldReturnCourseWhenIdDoesExist() {

        Course course = new Course(
            15, "Software Development", 0L);

        when(
            repositorySPI.findById(15)
        ).thenReturn(course);

        Course courseFound =
            courseService.findById(15);

        assertObjectProperties(course, courseFound);

        verify(repositorySPI, times(1)).
            findById(15);
    }

    @Test
    public void shouldGenerateExceptionWhenIdNullAtUpdating () {
        assertThrows(
            ApiBadInformationException.class,
            () -> courseService.update(
                null, null)
        );
    }

    @Test
    public void shouldGenerateExceptionWhenObjectNullAtUpdating() {
        assertThrows(
            ApiBadInformationException.class,
            () -> courseService.update(
                5, null)
        );
    }

    @Test
    public void shouldGenerateExceptionWhenIdsDoesNotMatchAtUpdating() {
        Integer id = 5;
        Course courseDto = new Course(
            15, "Software Development", 0L);

        assertThrows(
            ApiBadInformationException.class,
            () -> courseService.update(
                id, courseDto)
        );
    }

    @Test
    public void shouldGenerateExceptionWhenIdDoesNotExistAtUpdating() {
        Integer id = 5;
        Course courseDto = new Course(
            5, "Software Development", 0L);

        assertThrows(
            ApiNoEntityException.class,
            () -> courseService.update(
                id, courseDto)
        );
    }

    @Test
    public void shouldGenerateExceptionWhenVersionsDoesNotMatchAtUpdating() {
        Integer id = 5;

        Course sourceCourse = new Course(
            5, "Software Development", 1L);

        Course targetCourse = new Course(
            5, "Software Development", 3L);

        when(
            repositorySPI.findById(5)
        ).thenReturn(targetCourse);

        assertThrows(
            ApiConflictException.class,
            () -> courseService.update(
                id, sourceCourse)
        );

        verify(repositorySPI, times(1)).
            findById(5);
    }

    @Test
    public void shouldGenerateExceptionWhenNameNullAtUpdating() {
        Integer id = 5;

        Course sourceCourse = new Course(
            5, null, 1L);

        Course targetCourse = new Course(
            5, "Software Development", 1L);

        when(
            repositorySPI.findById(5)
        ).thenReturn(targetCourse);

        assertThrows(
            ApiMissingInformationException.class,
            () -> courseService.update(
                id, sourceCourse)
        );

        verify(repositorySPI, times(1)).
            findById(5);
    }

    @Test
    public void shouldGenerateExceptionWhenNameIsEmptyAtUpdating() {
        Integer id = 5;

        Course sourceCourse = new Course(
            5, " ", 1L);

        Course targetCourse = new Course(
            5, "Software Development", 1L);

        when(
            repositorySPI.findById(5)
        ).thenReturn(targetCourse);

        assertThrows(
            ApiMissingInformationException.class,
            () -> courseService.update(
                id, sourceCourse)
        );

        verify(repositorySPI, times(1)).
            findById(5);
    }

    @Test
    public void shouldGenerateExceptionWhenNameExistsAtUpdating() {
        Integer id = 5;

        Course sourceCourse = new Course(
            5, "Microprocessors", 1L);

        Course targetCourse = new Course(
            5, "Software Development", 1L);

        Course courseWithSameName = new Course(
            15, "Microprocessors", 12L);

        when(
            repositorySPI.findById(5)
        ).thenReturn(targetCourse);

        when(
            repositorySPI.getByName(sourceCourse.name())
        ).thenReturn(Optional.of(courseWithSameName));

        assertThrows(
            ApiConflictException.class,
            () -> courseService.update(
                id, sourceCourse)
        );

        verify(repositorySPI, times(1)).
            findById(5);

        verify(repositorySPI, times(1)).
            getByName(sourceCourse.name());
    }

    @Test
    public void shouldUpdateCourseAndReturnPersistedInformation() {
        Integer id = 5;

        Course sourceCourse = new Course(
            5, "Microprocessors", 1L);

        Course targetCourse = new Course(
            5, "Software Development", 1L);

        when(
            repositorySPI.findById(5)
        ).thenReturn(targetCourse);

        Course updatedCourseRepo = new Course(
            5, "Microprocessors", 2L);

        when(
            repositorySPI.save(sourceCourse)
        ).thenReturn(updatedCourseRepo);

        Course updatedCourseService =
            courseService.update(
                id, sourceCourse);

        assertObjectProperties(updatedCourseRepo, updatedCourseService);

        verify(repositorySPI, times(1)).
            findById(5);

        verify(repositorySPI, times(1)).
            save(any(Course.class));
    }

    @Test
    public void shouldGenerateExceptionWhenIdNullAtDeleting() {
        assertThrows(
            ApiBadInformationException.class,
            () -> courseService.delete(null)
        );
    }

    @Test
    public void shouldGenerateExceptionWhenIdDoesNotExistAtDeleting() {
        assertThrows(
            ApiNoEntityException.class,
            () -> courseService.delete(7)
        );
    }

    @Test
    public void shouldDeleteTheCourse() {
        Integer id = 7;

        Course course = new Course(
            7, "Software Development", 1L);

        when(
            repositorySPI.findById(id)
        ).thenReturn(course);

        courseService.delete(id);

        verify(repositorySPI, times(1)).
            findById(id);

        assertTimeoutPreemptively(Duration.ofSeconds(3),
            () -> courseService.delete(id),
            "Should execute in less than 3 seconds");
    }

    // Utilities
    private void assertObjectProperties(
        Course expected,Course current) {

        assertEquals(expected.id(), current.id());
        assertEquals(expected.name(), current.name());
        assertEquals(expected.version(), current.version());
    }
}
