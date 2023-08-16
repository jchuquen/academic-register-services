package co.grow.plan.academic.register.academicplan.course.application;

import co.grow.plan.academic.register.shared.exceptions.ApiMissingInformationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CourseDtoTest {

    @Test
    @DisplayName("CourseDtoTest - Must create object " +
        "and return properties correctly")
    public void testCreateCourseAndValidateGetters() {
        CourseDto subjectDto =
            new CourseDto(4, "Software Development", 2);

        assertEquals(4, subjectDto.getId());
        assertEquals("Software Development", subjectDto.getName());
        assertEquals(2, subjectDto.getVersion());
    }

    @Test
    @DisplayName("CourseDtoTest - Should throw ApiMissingInformationException " +
        "when name is null")
    public void testValidateObjectWhenNameNull() {
        CourseDto subjectDto = new CourseDto();

        assertThrows(
            ApiMissingInformationException.class,
            () -> subjectDto.validate()
        );
    }

    @Test
    @DisplayName("CourseDtoTest - Should throw ApiMissingInformationException " +
        "when name is empty")
    public void testValidateObjectWhenNameEmpty() {
        CourseDto subjectDto = new CourseDto(1, "   ", 0);

        assertThrows(
            ApiMissingInformationException.class,
            () -> subjectDto.validate()
        );
    }

    @Test
    @DisplayName("CourseDtoTest - Should not throw exception " +
        "when name is not null and not empty")
    public void testValidateObjectWhrenNameIsOk() {
        CourseDto subjectDto = new CourseDto(1, "Software Development", 0);

        assertDoesNotThrow(
            () -> subjectDto.validate()
        );
    }
}
