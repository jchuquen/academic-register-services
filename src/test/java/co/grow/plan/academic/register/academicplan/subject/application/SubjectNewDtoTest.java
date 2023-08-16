package co.grow.plan.academic.register.academicplan.subject.application;

import co.grow.plan.academic.register.shared.exceptions.ApiMissingInformationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SubjectNewDtoTest {

    @Test
    @DisplayName("SubjectNewDtoTest - Must create object " +
        "and return properties correctly")
    public void testCreateSubjectDtoAndValidateGetters() {
        SubjectNewDto subjectNewDto = new SubjectNewDto("Maths");

        assertEquals("Maths", subjectNewDto.getName());
    }

    @Test
    @DisplayName("SubjectNewDtoTest - Should throw ApiMissingInformationException " +
        "when name is null")
    public void testValidateObjectWhenNameNull() {
        SubjectNewDto subjectNewDto = new SubjectNewDto();

        assertThrows(
            ApiMissingInformationException.class,
            () -> subjectNewDto.validate()
        );
    }

    @Test
    @DisplayName("SubjectNewDtoTest - Should throw ApiMissingInformationException " +
        "when name is empty")
    public void testValidateObjectWhenNameEmpty() {
        SubjectNewDto subjectNewDto = new SubjectNewDto("  ");

        assertThrows(
            ApiMissingInformationException.class,
            () -> subjectNewDto.validate()
        );
    }

    @Test
    @DisplayName("SubjectNewDtoTest - Should not throw exception " +
        "when name is not null and not empty")
    public void testValidateObjectWhenNameIsOk() {
        SubjectNewDto subjectNewDto = new SubjectNewDto("Some Name");

        assertDoesNotThrow(
            () -> subjectNewDto.validate()
        );
    }
}
