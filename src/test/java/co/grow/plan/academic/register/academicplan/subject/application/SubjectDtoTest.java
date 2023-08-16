package co.grow.plan.academic.register.academicplan.subject.application;

import co.grow.plan.academic.register.shared.exceptions.ApiMissingInformationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SubjectDtoTest {

    @Test
    @DisplayName("SubjectDtoTest - Must create object " +
        "and return properties correctly")
    public void testCreateSubjectAndValidateGetters() {
        SubjectDto subjectDto =
            new SubjectDto(4, "Maths", 2);

        assertEquals(4, subjectDto.getId());
        assertEquals("Maths", subjectDto.getName());
        assertEquals(2, subjectDto.getVersion());
    }

    @Test
    @DisplayName("SubjectDtoTest - Should throw ApiMissingInformationException " +
        "when name is null")
    public void testValidateObjectWhenNameNull() {
        SubjectDto subjectDto = new SubjectDto();

        assertThrows(
            ApiMissingInformationException.class,
            () -> subjectDto.validate()
        );
    }

    @Test
    @DisplayName("SubjectDtoTest - Should throw ApiMissingInformationException " +
        "when name is empty")
    public void testValidateObjectWhenNameEmpty() {
        SubjectDto subjectDto = new SubjectDto(1, "   ", 0);

        assertThrows(
            ApiMissingInformationException.class,
            () -> subjectDto.validate()
        );
    }

    @Test
    @DisplayName("SubjectDtoTest - Should not throw exception " +
        "when name is not null and not empty")
    public void testValidateObjectWhrenNameIsOk() {
        SubjectDto subjectDto = new SubjectDto(1, "Some Name", 0);

        assertDoesNotThrow(
            () -> subjectDto.validate()
        );
    }
}
