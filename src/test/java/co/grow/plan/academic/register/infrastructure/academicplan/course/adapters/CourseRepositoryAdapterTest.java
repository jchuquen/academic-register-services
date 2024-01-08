package co.grow.plan.academic.register.infrastructure.academicplan.course.adapters;

import co.grow.plan.academic.register.AcademicRegisterServicesApplication;
import co.grow.plan.academic.register.domain.academicplan.course.model.Course;
import co.grow.plan.academic.register.shared.application.generics.IBasicRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@TestPropertySource("/application-test.properties")
@SpringBootTest(classes = AcademicRegisterServicesApplication.class)
public class CourseRepositoryAdapterTest {

    private static final String insertCourseSD =
        "insert into course (id, name, version) values(1, 'Software Development', 0)";
    private static final String insertCourseTI =
        "insert into course (id, name, version) values(2, 'Microprocessors', 1)";
    private static final String insertCourseRC =
        "insert into course (id, name, version) values(3, 'Pure Maths', 0)";

    private static final String deleteAllCourses =
        "delete from course";

    // AutoIncrement restarter
    private static final String restartAutoincrement =
        "ALTER TABLE course ALTER COLUMN id RESTART WITH 4";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private IBasicRepository<Course> repositoryAdapter;

    @BeforeEach
    public void setupDatabase() {
        jdbcTemplate.execute(restartAutoincrement);
        jdbcTemplate.execute(insertCourseSD);
        jdbcTemplate.execute(insertCourseTI);
        jdbcTemplate.execute(insertCourseRC);
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
            repositoryAdapter.findAll();

        assertEquals(expectedList.size(), currentList.size());
        for (int i = 0; i < expectedList.size(); i++) {
            Course expected = expectedList.get(i);
            Course current = currentList.get(i);

            assertObjectProperties(expected, current);
        }
    }

    @Test
    public void shouldReturnCourseWhenIdDoesExist() {

        Course expected = new Course(
            3, "Pure Maths", 0L);

        Course courseFound =
            repositoryAdapter.findById(expected.id());

        assertObjectProperties(expected, courseFound);
    }

    @Test
    public void shouldReturnNUllWhenIdDoesExist() {
        assertNull(repositoryAdapter.findById(7));
    }

    @Test
    public void shouldCreateNewCourseAndReturnPersistedInformation() {

        Course course =
            new Course(null, "Astronomy", null);

        Course expected =
            new Course(4, "Astronomy", 0L);

        Course current = repositoryAdapter.save(course);

        assertObjectProperties(expected, current);
    }

    @Test
    public void shouldUpdateCourseAndReturnPersistedInformation() {
        Integer id = 3;

        Course course = new Course(
            3, "RR", 0L);

        Course current =
            repositoryAdapter.save(course);

        Course expected = new Course(
            3, "RR", 1L);

        assertObjectProperties(expected, current);
    }

    @Test
    public void shouldDeleteTheCourse() {
        Integer id = 3;

        repositoryAdapter.deleteById(id);

        assertNull(repositoryAdapter.findById(3));

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
