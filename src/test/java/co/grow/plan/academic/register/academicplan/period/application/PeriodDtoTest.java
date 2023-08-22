package co.grow.plan.academic.register.academicplan.period.application;

import co.grow.plan.academic.register.shared.exceptions.ApiMissingInformationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PeriodDtoTest {

    @Test
    @DisplayName("PeriodDtoTest - Must create object " +
        "and return properties correctly")
    public void testCreatePeriodAndValidateGetters() {
        PeriodDto periodDto =
            new PeriodDto(4, "2023/I", true, 2);

        assertEquals(4, periodDto.getId());
        assertEquals("2023/I", periodDto.getName());
        assertEquals(true, periodDto.getActive());
        assertEquals(2, periodDto.getVersion());
    }

    @Test
    @DisplayName("PeriodDtoTest - Should throw ApiMissingInformationException " +
        "when name is null")
    public void testValidateObjectWhenNameNull() {
        PeriodDto periodDto =
            new PeriodDto(4, null, false, 2);

        assertThrows(
            ApiMissingInformationException.class,
            periodDto::validate
        );
    }

    @Test
    @DisplayName("PeriodDtoTest - Should throw ApiMissingInformationException " +
        "when isActive is null")
    public void testValidateObjectWhenIsActiveNull() {
        PeriodDto periodDto =
            new PeriodDto(4, "2023/I", null, 2);

        assertThrows(
            ApiMissingInformationException.class,
            periodDto::validate
        );
    }

    @Test
    @DisplayName("PeriodDtoTest - Should throw ApiMissingInformationException " +
        "when name is empty")
    public void testValidateObjectWhenNameEmpty() {
        PeriodDto periodDto =
            new PeriodDto(1, "   ", false , 0);

        assertThrows(
            ApiMissingInformationException.class,
            periodDto::validate
        );
    }

    @Test
    @DisplayName("PeriodDtoTest - Should not throw exception " +
        "when name and isActive are not null and not empty")
    public void testValidateObjectWhenNameAndIsActiveAreOk() {
        PeriodDto periodDto = new PeriodDto(1, "2023/I", true, 0);

        assertDoesNotThrow(
            () -> periodDto.validate()
        );
    }
}
