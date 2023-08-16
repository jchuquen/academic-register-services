package co.grow.plan.academic.register.academicplan.course.application;

import co.grow.plan.academic.register.academicplan.subject.application.SubjectNewDto;
import co.grow.plan.academic.register.shared.exceptions.ApiMissingInformationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CourseNewDtoTest {

    @Test
    @DisplayName("CourseNewDtoTest - Must create object " +
        "and return properties correctly")
    public void testCreateCourseDtoAndValidateGetters() {
        CourseNewDto courseNewDto = new CourseNewDto("Software Development");

        assertEquals("Software Development", courseNewDto.getName());
    }

    @Test
    @DisplayName("CourseNewDtoTest - Should throw ApiMissingInformationException " +
        "when name is null")
    public void testValidateObjectWhenNameNull() {
        CourseNewDto courseNewDto = new CourseNewDto();

        assertThrows(
            ApiMissingInformationException.class,
            () -> courseNewDto.validate()
        );
    }

    @Test
    @DisplayName("CourseNewDtoTest - Should throw ApiMissingInformationException " +
        "when name is empty")
    public void testValidateObjectWhenNameEmpty() {
        CourseNewDto courseNewDto = new CourseNewDto("  ");

        assertThrows(
            ApiMissingInformationException.class,
            () -> courseNewDto.validate()
        );
    }

    @Test
    @DisplayName("CourseNewDtoTest - Should not throw exception " +
        "when name is not null and not empty")
    public void testValidateObjectWhenNameIsOk() {
        CourseNewDto courseNewDto = new CourseNewDto("Some Name");

        assertDoesNotThrow(
            () -> courseNewDto.validate()
        );
    }
}
