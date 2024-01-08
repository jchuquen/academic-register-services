package co.grow.plan.academic.register.application.academicplan.course.services;

import co.grow.plan.academic.register.AcademicRegisterServicesApplication;
import co.grow.plan.academic.register.application.academicplan.course.ports.api.ICourseServiceAPI;
import co.grow.plan.academic.register.domain.academicplan.course.model.Course;
import co.grow.plan.academic.register.shared.application.exceptions.ApiConflictException;
import co.grow.plan.academic.register.shared.application.exceptions.ApiMissingInformationException;
import co.grow.plan.academic.register.shared.application.exceptions.ApiNoEntityException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource("/application-test.properties")
@SpringBootTest(classes = AcademicRegisterServicesApplication.class)
public class CourseServiceIntegrationTest {

    // Inserts
    private static final String insertCourseSD =
        "insert into course (id, name, version) values(1, 'Software Development', 0)";
    private static final String insertCourseMP =
        "insert into course (id, name, version) values(2, 'Microprocessors', 1)";
    private static final String insertCoursePM =
        "insert into course (id, name, version) values(3, 'Pure Maths', 0)";

    //Deletes
    private static final String deleteAllCourses =
        "delete from course";

    // AutoIncrement restarter
    private static final String restartAutoincrement =
        "ALTER TABLE course ALTER COLUMN id RESTART WITH 4";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ICourseServiceAPI courseService;

    @BeforeEach
    public void setupDatabase() {
        jdbcTemplate.execute(restartAutoincrement);
        jdbcTemplate.execute(insertCourseSD);
        jdbcTemplate.execute(insertCourseMP);
        jdbcTemplate.execute(insertCoursePM);
    }

    @Test
    public void shouldReturnAListOfCourses() {

        List<Course> expectedList =
            List.of(
                new Course(1, "Software Development", 0L),
                new Course(2, "Microprocessors", 1L),
                new Course(3, "Pure Maths", 0L)
            );

        List<Course> currentList =
            courseService.list();

        assertEquals(expectedList.size(), currentList.size());
        for (int i = 0; i < expectedList.size(); i++) {
            Course expected = expectedList.get(i);
            Course current = currentList.get(i);

            assertObjectProperties(expected, current);
        }
    }

    @Test
    public void shouldReturnAnEmptyListOfCourses() {

        jdbcTemplate.execute(deleteAllCourses);

        List<Course> expectedList =
            List.of();

        List<Course> currentList =
            courseService.list();

        assertIterableEquals(expectedList, currentList);
    }

    @Test
    public void shouldGenerateExceptionWhenNameExists() {

        Course course =
            new Course(null, "Software Development", null);

        assertThrows(
            ApiConflictException.class,
            () -> courseService.create(course)
        );
    }

    @Test
    public void shouldCreateNewCourseAndReturnPersistedInformation() {

        Course course =
            new Course(null, "Astronomy", null);

        Course expected =
            new Course(4, "Astronomy", 0L);

        Course current = courseService.create(course);

        assertObjectProperties(expected, current);
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

        Course expected = new Course(
            3, "Pure Maths", 0L);

        Course courseFound =
            courseService.findById(expected.id());

        assertObjectProperties(expected, courseFound);
    }

    @Test
    public void shouldGenerateExceptionWhenIdDoesNotExistAtUpdating() {
        Integer id = 5;
        Course course = new Course(
            5, "Astronomy", 0L);

        assertThrows(
            ApiNoEntityException.class,
            () -> courseService.update(
                id, course)
        );
    }

    @Test
    public void shouldGenerateExceptionWhenVersionsDoesNotMatchAtUpdating() {
        Integer id = 3;

        Course course = new Course(
            3, "Pure Maths", 3L);

        assertThrows(
            ApiConflictException.class,
            () -> courseService.update(
                id, course)
        );
    }

    @Test
    public void shouldGenerateExceptionWhenNameNullAtUpdating() {
        Integer id = 3;

        Course courseDto = new Course(
            3, null, 0L);

        assertThrows(
            ApiMissingInformationException.class,
            () -> courseService.update(
                id, courseDto)
        );
    }

    @Test
    public void shouldGenerateExceptionWhenNameIsEmptyAtUpdating() {
        Integer id = 3;

        Course course = new Course(
            3, " ", 0L);

        assertThrows(
            ApiMissingInformationException.class,
            () -> courseService.update(
                id, course)
        );
    }

    @Test
    public void shouldGenerateExceptionWhenNameExistsAtUpdating() {
        Integer id = 3;

        Course courseDto = new Course(
            3, "Software Development", 0L);

        assertThrows(
            ApiConflictException.class,
            () -> courseService.update(
                id, courseDto)
        );
    }

    @Test
    public void shouldUpdateCourseAndReturnPersistedInformation() {
        Integer id = 3;

        Course course = new Course(
            3, "RR", 0L);

        Course current =
            courseService.update(
                id, course);

        Course expected = new Course(
            3, "RR", 1L);

        assertObjectProperties(expected, current);
    }

    @Test
    public void shouldDeleteTheCourse() {
        Integer id = 3;

        courseService.delete(id);

        assertThrows(
            ApiNoEntityException.class,
            () -> courseService.findById(3)
        );

    }

    @AfterEach
    public void clearDatabase() {
        jdbcTemplate.execute(deleteAllCourses);
    }

    // Utilities
    private void assertObjectProperties(
        Course expected,Course current) {

        assertEquals(expected.id(), current.id());
        assertEquals(expected.name(), current.name());
        assertEquals(expected.version(), current.version());
    }
}

