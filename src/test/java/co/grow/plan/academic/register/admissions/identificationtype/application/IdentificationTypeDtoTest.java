package co.grow.plan.academic.register.admissions.identificationtype.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IdentificationTypeDtoTest {
    @Test
    @DisplayName("testCreationAndPublicGetters - Must create object " +
        "and return properties correctly")
    public void testCreationAndPublicGetters() {
        IdentificationTypeDto identificationTypeDto =
            new IdentificationTypeDto(1, "CC", 0);

        assertEquals(1, identificationTypeDto.getId());
        assertEquals("CC", identificationTypeDto.getName());
        assertEquals(0, identificationTypeDto.getVersion());
    }
}
