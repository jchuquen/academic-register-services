package co.grow.plan.academic.register.admissions.identificationtype.application;

import co.grow.plan.academic.register.shared.exceptions.ApiMissingInformationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IdentificationTypeNewDtoTest {
    @Test
    @DisplayName("IdentificationTypeNewDtoTest - Must create object " +
        "and return properties correctly")
    public void testCreationAndPublicGetters() {
        IdentificationTypeNewDto identificationTypeNewDto =
            new IdentificationTypeNewDto("CC");

        assertEquals("CC", identificationTypeNewDto.getName());
    }

    @Test
    @DisplayName("IdentificationTypeNewDtoTest - Should throw ApiMissingInformationException " +
        "when name is null")
    public void testValidateObjectWhenNameNull() {
        IdentificationTypeNewDto identificationTypeNewDto =
            new IdentificationTypeNewDto();

        assertThrows(
            ApiMissingInformationException.class,
            () -> identificationTypeNewDto.validate()
        );
    }

    @Test
    @DisplayName("IdentificationTypeNewDtoTest - Should throw ApiMissingInformationException " +
        "when name is empty")
    public void testValidateObjectWhenNameEmpty() {
        IdentificationTypeNewDto identificationTypeNewDto =
            new IdentificationTypeNewDto("  ");

        assertThrows(
            ApiMissingInformationException.class,
            () -> identificationTypeNewDto.validate()
        );
    }

    @Test
    @DisplayName("IdentificationTypeNewDtoTest - Should not throw exception " +
        "when name is not null and not empty")
    public void testValidateObjectWhenNameIsOk() {
        IdentificationTypeNewDto identificationTypeNewDto =
            new IdentificationTypeNewDto("Some Name");

        assertDoesNotThrow(
            () -> identificationTypeNewDto.validate()
        );
    }
}
