package co.grow.plan.academic.register.academicplan.subject.application;

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
public class SubjectServiceDBTest {

    // Inserts
    private final String insertSubjectMaths =
        "insert into subject (id, name, version) values(1, 'Maths', 0)";
    private final String insertSubjectPhysics =
        "insert into subject (id, name, version) values(2, 'Physics', 1)";
    private final String insertSubjectSocialStudies =
        "insert into subject (id, name, version) values(3, 'Social Studies', 0)";

    //Deletes
    private final String deleteAllSubjects =
        "delete from subject";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ISubjectService subjectService;

    @BeforeEach
    public void setupDatabase() {
        jdbcTemplate.execute(insertSubjectMaths);
        jdbcTemplate.execute(insertSubjectPhysics);
        jdbcTemplate.execute(insertSubjectSocialStudies);
    }

    @Test
    @DisplayName("SubjectServiceDBTest - List - Must return a list of many objects")
    public void testListSubjectsManyObjects() {

        List<SubjectDto> expectedList =
            List.of(
                new SubjectDto(1, "Maths", 0),
                new SubjectDto(2, "Physics", 1),
                new SubjectDto(3, "Social Studies", 0)
            );

        List<SubjectDto> currentList = subjectService.list();

        assertEquals(expectedList.size(), currentList.size());

        SubjectDto expected;
        SubjectDto current;
        for (int i = 0; i < expectedList.size(); i++) {
            expected = expectedList.get(i);
            current = currentList.get(i);

            assertEntityWithDto(expected, current);
        }
    }

    @Test
    @DisplayName("SubjectServiceDBTest - List - Must return an empty list")
    public void testListSubjectsEmptyList() {

        jdbcTemplate.execute(deleteAllSubjects);

        List<SubjectDto> expectedList = List.of();

        List<SubjectDto> currentList = subjectService.list();

        assertIterableEquals(expectedList, currentList);
    }

    @Test
    @DisplayName("SubjectServiceDBTest - Create - Must generate exception when " +
        "breaking name constrain")
    public void testCreateSubjectBreakingConstrain1() {

        SubjectNewDto subjectNewDto =
            new SubjectNewDto("Maths");

        assertThrows(
            ApiConflictException.class,
            () -> subjectService.create(subjectNewDto)
        );
    }

    @Test
    @DisplayName("SubjectServiceDBTest - Create - Must create new record and " +
        "return created information")
    public void testCreateSubject() {

        SubjectNewDto subjectNewDto =
            new SubjectNewDto("Painting");

        SubjectDto expected =
            new SubjectDto(4, "Painting", 0);

        SubjectDto current =
            subjectService.create(subjectNewDto);

        assertEntityWithDto(expected, current);
    }

    @Test
    @DisplayName("SubjectServiceDBTest - FindById - Must generate exception " +
        "when ID doesn't exist")
    public void testFindSubjectByIdIdDoesNotExist() {
        assertThrows(
            ApiNoEntityException.class,
            () -> subjectService.findById(9)
        );
    }

    @Test
    @DisplayName("SubjectServiceDBTest - FindById - Must return dto " +
        "when ID does exist")
    public void testFindSubjectById() {

        SubjectDto expected = new SubjectDto(
            3, "Social Studies", 0);

        SubjectDto subjectDto =
            subjectService.findById(expected.getId());

        assertEntityWithDto(expected, subjectDto);
    }

    @Test
    @DisplayName("SubjectServiceDBTest - UpdateSubject - " +
        "Must generate exception when ID doesn't exist")
    public void testUpdateSubjectIdDoesNotExist() {
        Integer id = 5;
        SubjectDto subjectDto = new SubjectDto(
            5, "CE", 0);

        assertThrows(
            ApiNoEntityException.class,
            () -> subjectService.update(id, subjectDto)
        );
    }

    @Test
    @DisplayName("SubjectServiceDBTest - UpdateSubject - " +
        "Must generate exception when Versions doesn't match")
    public void testUpdateSubjectVersionsDoesNotMatch() {
        Integer id = 3;

        SubjectDto subjectDto = new SubjectDto(
            3, "Social Studies", 3);

        assertThrows(
            ApiConflictException.class,
            () -> subjectService.update(
                id, subjectDto)
        );
    }

    @Test
    @DisplayName("SubjectServiceDBTest - UpdateSubject - " +
        "Must generate exception when Name is null")
    public void testUpdateSubjectNullName() {
        Integer id = 3;

        SubjectDto subjectDto = new SubjectDto(
            3, null, 0);

        assertThrows(
            ApiMissingInformationException.class,
            () -> subjectService.update(id, subjectDto)
        );
    }

    @Test
    @DisplayName("SubjectServiceDBTest - UpdateSubject - " +
        "Must generate exception when Name is empty")
    public void testUpdateSubjectEmptyName() {
        Integer id = 3;

        SubjectDto subjectDto = new SubjectDto(
            3, " ", 0);

        assertThrows(
            ApiMissingInformationException.class,
            () -> subjectService.update(id, subjectDto)
        );
    }

    @Test
    @DisplayName("SubjectServiceDBTest - UpdateSubject - " +
        "Must generate exception when Name already exists")
    public void testUpdateSubjectNameExists() {
        Integer id = 3;

        SubjectDto subjectDto = new SubjectDto(
            3, "Maths", 0);

        assertThrows(
            ApiConflictException.class,
            () -> subjectService.update(id, subjectDto)
        );
    }

    @Test
    @DisplayName("SubjectServiceDBTest - UpdateSubject - " +
        "Must update and return new persisted info")
    public void testUpdateSubject() {
        Integer id = 3;

        SubjectDto subjectDto = new SubjectDto(
            3, "Mobile", 0);

        SubjectDto current =
            subjectService.update(id, subjectDto);

        SubjectDto expected = new SubjectDto(
            3, "Mobile", 0);

        assertEntityWithDto(expected, current);
    }

    @Test
    @DisplayName("SubjectServiceDBTest - DeleteSubject - " +
        "Must delete the information found")
    public void testDeleteSubject() {
        Integer id = 3;

        subjectService.delete(id);

        assertThrows(
            ApiNoEntityException.class,
            () -> subjectService.findById(3)
        );

    }

    @AfterEach
    public void clearDatabase() {
        jdbcTemplate.execute(deleteAllSubjects);
    }

    //Utils
    private static void assertEntityWithDto(
        SubjectDto expected,
        SubjectDto current) {

        assertEquals(expected.getId(), current.getId());
        assertEquals(expected.getName(), current.getName());
        assertEquals(expected.getVersion(), current.getVersion());
    }
}
