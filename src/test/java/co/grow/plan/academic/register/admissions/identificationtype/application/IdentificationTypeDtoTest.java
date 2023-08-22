package co.grow.plan.academic.register.admissions.identificationtype.application;

import co.grow.plan.academic.register.shared.exceptions.ApiMissingInformationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IdentificationTypeDtoTest {
    @Test
    @DisplayName("IdentificationTypeDtoTest - Must create object " +
        "and return properties correctly")
    public void testCreationAndPublicGetters() {
        IdentificationTypeDto identificationTypeDto =
            new IdentificationTypeDto(1, "CC", 0);

        assertEquals(1, identificationTypeDto.getId());
        assertEquals("CC", identificationTypeDto.getName());
        assertEquals(0, identificationTypeDto.getVersion());
    }

    @Test
    @DisplayName("IdentificationTypeDtoTest - Should throw ApiMissingInformationException " +
        "when name is null")
    public void testValidateObjectWhenNameNull() {
        IdentificationTypeDto identificationTypeDto =
            new IdentificationTypeDto();

        assertThrows(
            ApiMissingInformationException.class,
            identificationTypeDto::validate
        );
    }

    @Test
    @DisplayName("IdentificationTypeDtoTest - Should throw ApiMissingInformationException " +
        "when name is empty")
    public void testValidateObjectWhenNameEmpty() {
        IdentificationTypeDto identificationTypeDto =
            new IdentificationTypeDto(1, "   ", 0);

        assertThrows(
            ApiMissingInformationException.class,
            identificationTypeDto::validate
        );
    }

    @Test
    @DisplayName("IdentificationTypeDtoTest - Should not throw exception " +
        "when name is not null and not empty")
    public void testValidateObjectWhenNameIsOk() {
        IdentificationTypeDto identificationTypeDto =
            new IdentificationTypeDto(1, "Some Name", 0);

        assertDoesNotThrow(
            identificationTypeDto::validate
        );
    }
}
