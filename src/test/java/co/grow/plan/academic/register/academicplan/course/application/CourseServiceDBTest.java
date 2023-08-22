package co.grow.plan.academic.register.academicplan.course.application;


import co.grow.plan.academic.register.AcademicRegisterServicesApplication;
import co.grow.plan.academic.register.shared.exceptions.ApiConflictException;
import co.grow.plan.academic.register.shared.exceptions.ApiMissingInformationException;
import co.grow.plan.academic.register.shared.exceptions.ApiNoEntityException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource("/application-test.properties")
@SpringBootTest(classes = AcademicRegisterServicesApplication.class)
public class CourseServiceDBTest {

    // Inserts
    private static final String insertCourseSD =
        "insert into course (id, name, version) values(1, 'Software Development', 0)";
    private static final String insertCourseTI =
        "insert into course (id, name, version) values(2, 'Microprocessors', 1)";
    private static final String insertCourseRC =
        "insert into course (id, name, version) values(3, 'Pure Maths', 0)";

    //Deletes
    private static final String deleteAllCourses =
        "delete from course";

    // AutoIncrement restarter
    private static final String restartAutoincrement =
        "ALTER TABLE course ALTER COLUMN id RESTART WITH 1";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ICourseService courseService;

    @BeforeEach
    public void setupDatabase() {
        jdbcTemplate.execute(restartAutoincrement);
        jdbcTemplate.execute(insertCourseSD);
        jdbcTemplate.execute(insertCourseTI);
        jdbcTemplate.execute(insertCourseRC);
    }

    @Test
    @DisplayName("CourseServiceTest - List - Must return a list of many objects")
    public void testListCoursesManyObjects() {

        List<CourseDto> expectedList =
            List.of(
                new CourseDto(1, "Software Development", 0),
                new CourseDto(2, "Microprocessors", 1),
                new CourseDto(3, "Pure Maths", 0)
            );

        List<CourseDto> currentList =
            courseService.list();

        assertEquals(expectedList.size(), currentList.size());
        for (int i = 0; i < expectedList.size(); i++) {
            CourseDto expected = expectedList.get(i);
            CourseDto current = currentList.get(i);

            assertEntityWithDto(expected, current);
        }
    }

    @Test
    @DisplayName("CourseServiceTest - List - Must return an empty list")
    public void testListCoursesEmptyList() {

        jdbcTemplate.execute(deleteAllCourses);

        List<CourseDto> expectedList =
            List.of();

        List<CourseDto> currentList =
            courseService.list();

        assertIterableEquals(expectedList, currentList);
    }

    @Test
    @DisplayName("CourseServiceTest - Create - Must generate exception when " +
        "breaking name constrain")
    public void testCreateCourseBreakingConstrain1() {

        CourseNewDto courseNewDto =
            new CourseNewDto("Software Development");

        assertThrows(
            ApiConflictException.class,
            () -> courseService.create(
                courseNewDto
            )
        );
    }

    @Test
    @DisplayName("CourseServiceTest - Create - Must create new record and " +
        "return created information")
    public void testCreateCourse() {

        CourseNewDto courseNewDto =
            new CourseNewDto("Astronomy");

        CourseDto expected =
            new CourseDto(4, "Astronomy", 0);

        CourseDto current =
            courseService.create(courseNewDto);

        assertEntityWithDto(expected, current);
    }

    @Test
    @DisplayName("CourseServiceTest - FindById - Must generate exception " +
        "when ID doesn't exist")
    public void testFindCourseByIdIdDoesNotExist() {
        assertThrows(
            ApiNoEntityException.class,
            () -> courseService.findById(9)
        );
    }

    @Test
    @DisplayName("CourseServiceTest - FindById - Must return dto " +
        "when ID does exist")
    public void testFindCourseById() {

        CourseDto expected = new CourseDto(
            3, "Pure Maths", 0);

        CourseDto courseDto =
            courseService.findById(expected.getId());

        assertEntityWithDto(expected, courseDto);
    }

    @Test
    @DisplayName("CourseServiceTest - UpdateCourse - " +
        "Must generate exception when ID doesn't exist")
    public void testUpdateCourseIdDoesNotExist() {
        Integer id = 5;
        CourseDto courseDto = new CourseDto(
            5, "Astronomy", 0);

        assertThrows(
            ApiNoEntityException.class,
            () -> courseService.update(
                id, courseDto)
        );
    }

    @Test
    @DisplayName("CourseServiceTest - UpdateCourse - " +
        "Must generate exception when Versions doesn't match")
    public void testUpdateCourseVersionsDoesNotMatch() {
        Integer id = 3;

        CourseDto courseDto = new CourseDto(
            3, "Pure Maths", 3);

        assertThrows(
            ApiConflictException.class,
            () -> courseService.update(
                id, courseDto)
        );
    }

    @Test
    @DisplayName("CourseServiceTest - UpdateCourse - " +
        "Must generate exception when Name is null")
    public void testUpdateCourseNullName() {
        Integer id = 3;

        CourseDto courseDto = new CourseDto(
            3, null, 0);

        assertThrows(
            ApiMissingInformationException.class,
            () -> courseService.update(
                id, courseDto)
        );
    }

    @Test
    @DisplayName("CourseServiceTest - UpdateCourse - " +
        "Must generate exception when Name is empty")
    public void testUpdateCourseEmptyName() {
        Integer id = 3;

        CourseDto courseDto = new CourseDto(
            3, " ", 0);

        assertThrows(
            ApiMissingInformationException.class,
            () -> courseService.update(
                id, courseDto)
        );
    }

    @Test
    @DisplayName("CourseServiceTest - UpdateCourse - " +
        "Must generate exception when Name already exists")
    public void testUpdateCourseNameExists() {
        Integer id = 3;

        CourseDto courseDto = new CourseDto(
            3, "Software Development", 0);

        assertThrows(
            ApiConflictException.class,
            () -> courseService.update(
                id, courseDto)
        );
    }

    @Test
    @DisplayName("CourseServiceTest - UpdateCourse - " +
        "Must update and return new persisted info")
    public void testUpdateCourse() {
        Integer id = 3;

        CourseDto courseDto = new CourseDto(
            3, "RR", 0);

        CourseDto current =
            courseService.update(
                id, courseDto);

        CourseDto expected = new CourseDto(
            3, "RR", 1);

        assertEntityWithDto(expected, current);
    }

    @Test
    @DisplayName("CourseServiceTest - DeleteCourse - " +
        "Must delete the information")
    public void testDeleteCourse() {
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

    //Utils
    private static void assertEntityWithDto(
        CourseDto expected,
        CourseDto current) {

        assertEquals(expected.getId(), current.getId());
        assertEquals(expected.getName(), current.getName());
        assertEquals(expected.getVersion(), current.getVersion());
    }
}
