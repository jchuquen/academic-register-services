package co.grow.plan.academic.register.academicplan.subject.application;

import co.grow.plan.academic.register.AcademicRegisterServicesApplication;
import co.grow.plan.academic.register.academicplan.subject.domain.Subject;
import co.grow.plan.academic.register.academicplan.subject.domain.SubjectDao;
import co.grow.plan.academic.register.shared.exceptions.ApiBadInformationException;
import co.grow.plan.academic.register.shared.exceptions.ApiConflictException;
import co.grow.plan.academic.register.shared.exceptions.ApiMissingInformationException;
import co.grow.plan.academic.register.shared.exceptions.ApiNoEntityException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestPropertySource("/application-test.properties")
@SpringBootTest(classes = AcademicRegisterServicesApplication.class)
public class SubjectServiceTest {

    @MockBean
    private SubjectDao subjectDao;

    @Autowired
    private ISubjectService subjectService;

    @Test
    @DisplayName("SubjectServiceTest - List - Must return a list of many objects")
    public void testListManySubjects() {

        List<Subject> subjectList =
            List.of(
                new Subject(1, "Maths", 0),
                new Subject(2, "Physics", 1),
                new Subject(3, "Social Studies", 0)
            );

        when(subjectDao.findAll()).thenReturn(subjectList);

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

        verify(subjectDao, times(1)).findAll();
    }

    @Test
    @DisplayName("SubjectServiceTest - List - Must return an empty list")
    public void testListSubjectsEmptyList() {

        List<Subject> subjectList = List.of();

        when(subjectDao.findAll()).thenReturn(subjectList);

        List<SubjectDto> expectedList = List.of();

        List<SubjectDto> currentList = subjectService.list();

        assertIterableEquals(expectedList, currentList);
        verify(subjectDao, times(1)).findAll();
    }

    @Test
    @DisplayName("SubjectServiceTest - Create - Must generate exception when " +
        "Subject parameter is null")
    public void testCreateSubjectBadRequest1() {
        assertThrows(
            ApiBadInformationException.class,
            () -> subjectService.create(null)
        );
    }

    @Test
    @DisplayName("SubjectServiceTest - Create - Must generate exception when " +
        "name is null")
    public void testCreateSubjectBadRequest2() {
        assertThrows(
            ApiMissingInformationException.class,
            () -> subjectService.create(new SubjectNewDto(null)
            )
        );
    }

    @Test
    @DisplayName("SubjectServiceTest - Create - Must generate exception when " +
        "breaking name constraint")
    public void testCreateSubjectBreakingConstrain1() {

        SubjectNewDto subjectNewDto =
            new SubjectNewDto("Maths");

        Subject subjectConflict =
            new Subject(99, "Maths", 15);

        when(
            subjectDao.getByName(subjectNewDto.getName())
        ).thenReturn(Optional.of(subjectConflict));

        assertThrows(
            ApiConflictException.class,
            () -> subjectService.create(subjectNewDto)
        );

        verify(subjectDao, times(1)).
            getByName(subjectNewDto.getName());
    }

    @Test
    @DisplayName("SubjectServiceTest - Create - Must create new record and " +
        "return created information")
    public void testCreateSubject() {

        SubjectNewDto subjectNewDto =
            new SubjectNewDto("Maths");

        Subject subject = new Subject(
            15, "Maths", 0);

        SubjectDto expected =
            new SubjectDto(15, "Maths", 0);

        when(
            subjectDao.save(any(Subject.class))
        ).thenReturn(subject);

        SubjectDto current =
            subjectService.create(subjectNewDto);

        assertEntityWithDto(expected, current);

        verify(subjectDao, times(1)).
            save(any(Subject.class));
    }

    @Test
    @DisplayName("SubjectServiceTest - FindById - Must generate exception " +
        "when ID is null")
    public void testFindSubjectByIdNullId() {
        assertThrows(
            ApiBadInformationException.class,
            () -> subjectService.findById(null)
        );
    }

    @Test
    @DisplayName("SubjectServiceTest - FindById - Must generate exception " +
        "when ID doesn't exist")
    public void testFindSubjectByIdIdDoesNotExist() {
        assertThrows(
            ApiNoEntityException.class,
            () -> subjectService.findById(9)
        );
    }

    @Test
    @DisplayName("SubjectServiceTest - FindById - Must return dto " +
        "when ID does exist")
    public void testFindSubjectById() {
        Subject subject = new Subject(
            15, "Maths", 0);

        SubjectDto expected = new SubjectDto(
            15, "Maths", 0);

        when(
            subjectDao.findById(15)
        ).thenReturn(Optional.of(subject));

        SubjectDto subjectDto =
            subjectService.findById(15);

        assertEntityWithDto(expected, subjectDto);

        verify(subjectDao, times(1)).
            findById(15);
    }

    @Test
    @DisplayName("SubjectServiceTest - UpdateSubject - " +
        "Must generate exception when ID is null")
    public void testUpdateSubjectIdNull() {
        assertThrows(
            ApiBadInformationException.class,
            () -> subjectService.update(null, null)
        );
    }

    @Test
    @DisplayName("SubjectServiceTest - UpdateSubject - " +
        "Must generate exception when Subject object is null")
    public void testUpdateSubjectObjectNull() {
        assertThrows(
            ApiBadInformationException.class,
            () -> subjectService.update(5, null)
        );
    }

    @Test
    @DisplayName("SubjectServiceTest - UpdateSubject - " +
        "Must generate exception when IDs doesn't match")
    public void testUpdateSubjectIdsDoesNotMatch() {
        Integer id = 5;
        SubjectDto subjectDto = new SubjectDto(
            15, "Maths", 0);

        assertThrows(
            ApiBadInformationException.class,
            () -> subjectService.update(id, subjectDto)
        );
    }

    @Test
    @DisplayName("SubjectServiceTest - UpdateSubject - " +
        "Must generate exception when ID doesn't exist")
    public void testUpdateSubjectIdDoesNotExist() {
        Integer id = 5;
        SubjectDto subjectDto = new SubjectDto(
            5, "Maths", 0);

        assertThrows(
            ApiNoEntityException.class,
            () -> subjectService.update(id, subjectDto)
        );
    }

    @Test
    @DisplayName("SubjectServiceTest - UpdateSubject - " +
        "Must generate exception when Versions doesn't match")
    public void testUpdateSubjectVersionsDoesNotMatch() {
        Integer id = 5;

        SubjectDto subjectDto = new SubjectDto(5, "Maths", 1);

        Subject subject = new Subject(5, "Maths", 3);

        when(
            subjectDao.findById(5)
        ).thenReturn(Optional.of(subject));

        assertThrows(
            ApiConflictException.class,
            () -> subjectService.update(id, subjectDto)
        );

        verify(subjectDao, times(1)).
            findById(5);
    }

    @Test
    @DisplayName("SubjectServiceTest - UpdateSubject - " +
        "Must generate exception when Name is null")
    public void testUpdateSubjectNullName() {
        Integer id = 5;

        SubjectDto subjectDto = new SubjectDto(
            5, null, 1);

        Subject subject = new Subject(
            5, "Maths", 1);

        when(
            subjectDao.findById(5)
        ).thenReturn(Optional.of(subject));

        assertThrows(
            ApiMissingInformationException.class,
            () -> subjectService.update(id, subjectDto)
        );

        verify(subjectDao, times(1)).
            findById(5);
    }

    @Test
    @DisplayName("SubjectServiceTest - UpdateSubject - " +
        "Must generate exception when Name is empty")
    public void testUpdateSubjectEmptyName() {
        Integer id = 5;

        SubjectDto subjectDto = new SubjectDto(5, " ", 1);

        Subject subject = new Subject(5, "Maths", 1);

        when(
            subjectDao.findById(5)
        ).thenReturn(Optional.of(subject));

        assertThrows(
            ApiMissingInformationException.class,
            () -> subjectService.update(id, subjectDto)
        );

        verify(subjectDao, times(1)).
            findById(5);
    }

    @Test
    @DisplayName("SubjectServiceTest - UpdateSubject - " +
        "Must generate exception when Name already exists")
    public void testUpdateSubjectNameExists() {
        Integer id = 5;

        SubjectDto subjectDto = new SubjectDto(
            5, "Physics", 1);

        Subject subject = new Subject(
            5, "Maths", 1);

        Subject subjectExists = new Subject(
            15, "Physics", 12);

        when(
            subjectDao.findById(5)
        ).thenReturn(Optional.of(subject));

        when(
            subjectDao.getByName(subjectDto.getName())
        ).thenReturn(Optional.of(subjectExists));

        assertThrows(
            ApiConflictException.class,
            () -> subjectService.update(id, subjectDto)
        );

        verify(subjectDao, times(1)).
            findById(5);

        verify(subjectDao, times(1)).
            getByName(subjectDto.getName());
    }

    @Test
    @DisplayName("SubjectServiceTest - UpdateSubject - " +
        "Must update and return new persisted info")
    public void testUpdateSubject() {
        Integer id = 5;

        SubjectDto subjectDto = new SubjectDto(
            5, "Physics", 1);

        Subject subject = new Subject(
            5, "Maths", 1);

        Subject newSubject = new Subject(
            5, "Physics", 2);

        SubjectDto expected = new SubjectDto(
            5, "Physics", 2);

        when(
            subjectDao.findById(5)
        ).thenReturn(Optional.of(subject));

        when(
            subjectDao.save(any(Subject.class))
        ).thenReturn(newSubject);

        SubjectDto current =
            subjectService.update(id, subjectDto);

        assertEntityWithDto(expected, current);

        verify(subjectDao, times(1)).
            findById(5);

        verify(subjectDao, times(1)).
            save(any(Subject.class));
    }

    @Test
    @DisplayName("SubjectServiceTest - DeleteSubject - " +
        "Must generate exception when ID is null")
    public void testDeleteSubjectIdNull() {
        assertThrows(
            ApiBadInformationException.class,
            () -> subjectService.delete(null)
        );
    }

    @Test
    @DisplayName("SubjectServiceTest - DeleteSubject - " +
        "Must generate exception when ID doesn't exist")
    public void testDeleteSubjectIdDoesNotExist() {
        assertThrows(
            ApiNoEntityException.class,
            () -> subjectService.delete(7)
        );
    }

    @Test
    @DisplayName("SubjectServiceTest - DeleteSubject - " +
        "Must delete the information found")
    public void testDeleteSubject() {
        Integer id = 7;

        Subject subject = new Subject(
            7, "Maths", 1);

        when(
            subjectDao.findById(id)
        ).thenReturn(Optional.of(subject));

        subjectService.delete(id);

        verify(subjectDao, times(1)).
            findById(id);

        assertTimeoutPreemptively(Duration.ofSeconds(3),
            () -> subjectService.delete(id),
            "Should execute in less than 3 seconds");
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
