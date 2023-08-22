package co.grow.plan.academic.register.academicplan.course.application;

import co.grow.plan.academic.register.academicplan.course.domain.Course;
import co.grow.plan.academic.register.academicplan.course.domain.ICourseDao;
import co.grow.plan.academic.register.shared.exceptions.ApiBadInformationException;
import co.grow.plan.academic.register.shared.exceptions.ApiConflictException;
import co.grow.plan.academic.register.shared.exceptions.ApiMissingInformationException;
import co.grow.plan.academic.register.shared.exceptions.ApiNoEntityException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestPropertySource("/application-test.properties")
@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {
    
    @Mock
    private ICourseDao courseDao;

    @Mock
    private ICourseMapper courseMapper;

    @InjectMocks
    private CourseService courseService;

    @Test
    @DisplayName("CourseServiceTest - List - Must return a list of many objects")
    public void testListCoursesManyObjects() {

        List<Course> courseList =
            List.of(
                new Course(1, "Software Development", 0),
                new Course(2, "Microprocessors", 1),
                new Course(3, "Pure Maths", 0)
            );

        when(courseDao.findAll()).thenReturn(courseList);

        List<CourseDto> expectedList =
            List.of(
                new CourseDto(1, "Software Development", 0),
                new CourseDto(2, "Microprocessors", 1),
                new CourseDto(3, "Pure Maths", 0)
            );

        when(
            courseMapper.entityListToIdentifiableAndVersionableDtoList(
                any(Iterable.class)
            )
        ).thenReturn(expectedList);

        List<CourseDto> currentList =
            courseService.list();

        assertEquals(expectedList.size(), currentList.size());

        CourseDto expected;
        CourseDto current;

        for (int i = 0; i < expectedList.size(); i++) {
            expected = expectedList.get(i);
            current = currentList.get(i);

            assertEntityWithDto(expected, current);
        }

        verify(courseDao, times(1)).findAll();
    }

    @Test
    @DisplayName("CourseServiceTest - List - Must return an empty list")
    public void testListCoursesEmptyList() {

        List<Course> courseList =
            List.of();

        when(courseDao.findAll()).thenReturn(courseList);

        List<CourseDto> expectedList =
            List.of();

        List<CourseDto> currentList =
            courseService.list();

        assertIterableEquals(expectedList, currentList);
        verify(courseDao, times(1)).findAll();
    }

    @Test
    @DisplayName("CourseServiceTest - Create - Must generate exception when " +
        "Course parameter is null")
    public void testCreateCourseBadRequest1() {
        assertThrows(
            ApiBadInformationException.class,
            () -> courseService.create(null)
        );
    }

    @Test
    @DisplayName("CourseServiceTest - Create - Must generate exception when " +
        "name is null")
    public void testCreateCourseBadRequest2() {
        assertThrows(
            ApiMissingInformationException.class,
            () -> courseService.create(
                new CourseNewDto(null)
            )
        );
    }

    @Test
    @DisplayName("CourseServiceTest - Create - Must generate exception when " +
        "breaking name constraint")
    public void testCreateCourseBreakingConstrain1() {

        CourseNewDto courseNewDto =
            new CourseNewDto("Software Development");

        Course courseConflict =
            new Course(99, "Software Development", 15);

        when(
            courseDao.getByName(courseNewDto.getName())
        ).thenReturn(Optional.of(courseConflict));

        assertThrows(
            ApiConflictException.class,
            () -> courseService.create(
                courseNewDto
            )
        );

        verify(courseDao, times(1)).
            getByName(courseNewDto.getName());
    }

    @Test
    @DisplayName("CourseServiceTest - Create - Must create new record and " +
        "return created information")
    public void testCreateCourse() {

        CourseNewDto courseNewDto =
            new CourseNewDto("Software Development");

        Course courseNoPersisted =
            new Course(courseNewDto.getName());

        when(
            courseMapper.noIdentifiableAndVersionableDtoToEntity(
                courseNewDto
            )
        ).thenReturn(courseNoPersisted);

        Course course = new Course(
            15, "Software Development", 0);

        when(
            courseDao.save(any(Course.class))
        ).thenReturn(course);

        CourseDto expected =
            new CourseDto(15, "Software Development", 0);

        when(
            courseMapper.entityToIdentifiableAndVersionableDto(any(Course.class))
        ).thenReturn(expected);

        CourseDto current =
            courseService.create(courseNewDto);

        assertEntityWithDto(expected, current);

        verify(courseDao, times(1)).
            save(any(Course.class));
    }

    @Test
    @DisplayName("CourseServiceTest - FindById - Must generate exception " +
        "when ID is null")
    public void testFindCourseByIdNullId() {
        assertThrows(
            ApiBadInformationException.class,
            () -> courseService.findById(null)
        );
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

        Course course = new Course(
            15, "Software Development", 0);

        when(
            courseDao.findById(15)
        ).thenReturn(Optional.of(course));

        CourseDto expected = new CourseDto(
            15, "Software Development", 0);

        when(
            courseMapper.entityToIdentifiableAndVersionableDto(
                any(Course.class)
            )
        ).thenReturn(expected);

        CourseDto courseDto =
            courseService.findById(15);

        assertEntityWithDto(expected, courseDto);

        verify(courseDao, times(1)).
            findById(15);
    }

    @Test
    @DisplayName("CourseServiceTest - UpdateCourse - " +
        "Must generate exception when ID is null")
    public void testUpdateCourseIdNull() {
        assertThrows(
            ApiBadInformationException.class,
            () -> courseService.update(
                null, null)
        );
    }

    @Test
    @DisplayName("CourseServiceTest - UpdateCourse - " +
        "Must generate exception when Course object is null")
    public void testUpdateCourseObjectNull() {
        assertThrows(
            ApiBadInformationException.class,
            () -> courseService.update(
                5, null)
        );
    }

    @Test
    @DisplayName("CourseServiceTest - UpdateCourse - " +
        "Must generate exception when IDs doesn't match")
    public void testUpdateCourseIdsDoesNotMatch() {
        Integer id = 5;
        CourseDto courseDto = new CourseDto(
            15, "Software Development", 0);

        assertThrows(
            ApiBadInformationException.class,
            () -> courseService.update(
                id, courseDto)
        );
    }

    @Test
    @DisplayName("CourseServiceTest - UpdateCourse - " +
        "Must generate exception when ID doesn't exist")
    public void testUpdateCourseIdDoesNotExist() {
        Integer id = 5;
        CourseDto courseDto = new CourseDto(
            5, "Software Development", 0);

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
        Integer id = 5;

        CourseDto courseDto = new CourseDto(
            5, "Software Development", 1);

        Course course = new Course(
            5, "Software Development", 3);

        when(
            courseDao.findById(5)
        ).thenReturn(Optional.of(course));

        assertThrows(
            ApiConflictException.class,
            () -> courseService.update(
                id, courseDto)
        );

        verify(courseDao, times(1)).
            findById(5);
    }

    @Test
    @DisplayName("CourseServiceTest - UpdateCourse - " +
        "Must generate exception when Name is null")
    public void testUpdateCourseNullName() {
        Integer id = 5;

        CourseDto courseDto = new CourseDto(
            5, null, 1);

        Course course = new Course(
            5, "Software Development", 1);

        when(
            courseDao.findById(5)
        ).thenReturn(Optional.of(course));

        assertThrows(
            ApiMissingInformationException.class,
            () -> courseService.update(
                id, courseDto)
        );

        verify(courseDao, times(1)).
            findById(5);
    }

    @Test
    @DisplayName("CourseServiceTest - UpdateCourse - " +
        "Must generate exception when Name is empty")
    public void testUpdateCourseEmptyName() {
        Integer id = 5;

        CourseDto courseDto = new CourseDto(
            5, " ", 1);

        Course course = new Course(
            5, "Software Development", 1);

        when(
            courseDao.findById(5)
        ).thenReturn(Optional.of(course));

        assertThrows(
            ApiMissingInformationException.class,
            () -> courseService.update(
                id, courseDto)
        );

        verify(courseDao, times(1)).
            findById(5);
    }

    @Test
    @DisplayName("CourseServiceTest - UpdateCourse - " +
        "Must generate exception when Name already exists")
    public void testUpdateCourseNameExists() {
        Integer id = 5;

        CourseDto courseDto = new CourseDto(
            5, "Microprocessors", 1);

        Course course = new Course(
            5, "Software Development", 1);

        Course courseExists = new Course(
            15, "Microprocessors", 12);

        when(
            courseDao.findById(5)
        ).thenReturn(Optional.of(course));

        when(
            courseDao.getByName(courseDto.getName())
        ).thenReturn(Optional.of(courseExists));

        assertThrows(
            ApiConflictException.class,
            () -> courseService.update(
                id, courseDto)
        );

        verify(courseDao, times(1)).
            findById(5);

        verify(courseDao, times(1)).
            getByName(courseDto.getName());
    }

    @Test
    @DisplayName("CourseServiceTest - UpdateCourse - " +
        "Must update and return new persisted info")
    public void testUpdateCourse() {
        Integer id = 5;

        CourseDto courseDto = new CourseDto(
            5, "Microprocessors", 1);

        Course course = new Course(
            5, "Software Development", 1);

        when(
            courseDao.findById(5)
        ).thenReturn(Optional.of(course));

        CourseNewDto courseNewDto =
            new CourseNewDto(courseDto.getName());

        when(
            courseMapper.identifiableAndVersionableDtoToNoIdentifiableAndVersionableDto(
                courseDto
            )
        ).thenReturn(courseNewDto);

        Course courseUpdatedNoPersisted = new Course(
            5, "Microprocessors", 1);

        when(
            courseMapper.updateEntityFromNoIdentifiableAndVersionableDto(
                any(Course.class),
                any(CourseNewDto.class)
            )
        ).thenReturn(courseUpdatedNoPersisted);

        CourseDto expected = new CourseDto(
            5, "Microprocessors", 2);

        when(
            courseMapper.entityToIdentifiableAndVersionableDto(any(Course.class))
        ).thenReturn(expected);

        Course newCourse = new Course(
            5, "Microprocessors", 2);

        when(
            courseDao.save(any(Course.class))
        ).thenReturn(newCourse);

        CourseDto current =
            courseService.update(
                id, courseDto);

        assertEntityWithDto(expected, current);

        verify(courseDao, times(1)).
            findById(5);

        verify(courseDao, times(1)).
            save(any(Course.class));
    }

    @Test
    @DisplayName("CourseServiceTest - DeleteCourse - " +
        "Must generate exception when ID is null")
    public void testDeleteCourseIdNull() {
        assertThrows(
            ApiBadInformationException.class,
            () -> courseService.delete(null)
        );
    }

    @Test
    @DisplayName("CourseServiceTest - DeleteCourse - " +
        "Must generate exception when ID doesn't exist")
    public void testDeleteCourseIdDoesNotExist() {
        assertThrows(
            ApiNoEntityException.class,
            () -> courseService.delete(7)
        );
    }

    @Test
    @DisplayName("CourseServiceTest - DeleteCourse - " +
        "Must delete the information found")
    public void testDeleteCourse() {
        Integer id = 7;

        Course course = new Course(
            7, "Software Development", 1);

        when(
            courseDao.findById(id)
        ).thenReturn(Optional.of(course));

        courseService.delete(id);

        verify(courseDao, times(1)).
            findById(id);

        assertTimeoutPreemptively(Duration.ofSeconds(3),
            () -> courseService.delete(id),
            "Should execute in less than 3 seconds");
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
