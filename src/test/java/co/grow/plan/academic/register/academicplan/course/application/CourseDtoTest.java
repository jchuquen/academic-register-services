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
        CourseDto courseDto =
            new CourseDto(4, "Software Development", 2);

        assertEquals(4, courseDto.getId());
        assertEquals("Software Development", courseDto.getName());
        assertEquals(2, courseDto.getVersion());
    }

    @Test
    @DisplayName("CourseDtoTest - Should throw ApiMissingInformationException " +
        "when name is null")
    public void testValidateObjectWhenNameNull() {
        CourseDto courseDto = new CourseDto();

        assertThrows(
            ApiMissingInformationException.class,
            courseDto::validate
        );
    }

    @Test
    @DisplayName("CourseDtoTest - Should throw ApiMissingInformationException " +
        "when name is empty")
    public void testValidateObjectWhenNameEmpty() {
        CourseDto courseDto = new CourseDto(1, "   ", 0);

        assertThrows(
            ApiMissingInformationException.class,
            courseDto::validate
        );
    }

    @Test
    @DisplayName("CourseDtoTest - Should not throw exception " +
        "when name is not null and not empty")
    public void testValidateObjectWhenNameIsOk() {
        CourseDto courseDto = new CourseDto(1, "Software Development", 0);

        assertDoesNotThrow(
            courseDto::validate
        );
    }
}
