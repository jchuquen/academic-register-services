package co.grow.plan.academic.register.academicplan.period.application;

import co.grow.plan.academic.register.shared.exceptions.ApiMissingInformationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PeriodNewDtoTest {

    @Test
    @DisplayName("PeriodNewDtoTest - Must create object " +
        "and return properties correctly")
    public void testCreatePeriodAndValidateGetters() {
        PeriodNewDto periodNewDto =
            new PeriodNewDto("2023/I", true);

        assertEquals("2023/I", periodNewDto.getName());
        assertEquals(true, periodNewDto.getActive());
    }

    @Test
    @DisplayName("PeriodNewDtoTest - Should throw ApiMissingInformationException " +
        "when name is null")
    public void testValidateObjectWhenNameNull() {
        PeriodNewDto periodNewDto =
            new PeriodNewDto(null, false);

        assertThrows(
            ApiMissingInformationException.class,
            periodNewDto::validate
        );
    }

    @Test
    @DisplayName("PeriodNewDtoTest - Should throw ApiMissingInformationException " +
        "when isActive is null")
    public void testValidateObjectWhenIsActiveNull() {
        PeriodNewDto periodNewDto =
            new PeriodNewDto("2023/I", null);

        assertThrows(
            ApiMissingInformationException.class,
            periodNewDto::validate
        );
    }

    @Test
    @DisplayName("PeriodNewDtoTest - Should throw ApiMissingInformationException " +
        "when name is empty")
    public void testValidateObjectWhenNameEmpty() {
        PeriodNewDto periodNewDto =
            new PeriodNewDto("   ", false);

        assertThrows(
            ApiMissingInformationException.class,
            periodNewDto::validate
        );
    }

    @Test
    @DisplayName("PeriodNewDtoTest - Should not throw exception " +
        "when name and isActive are not null and not empty")
    public void testValidateObjectWhenNameAndIsActiveAreOk() {
        PeriodNewDto periodNewDto = new PeriodNewDto("2023/I", true);

        assertDoesNotThrow(
            periodNewDto::validate
        );
    }
}
