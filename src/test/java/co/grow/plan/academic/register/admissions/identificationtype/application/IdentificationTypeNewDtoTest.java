package co.grow.plan.academic.register.admissions.identificationtype.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class IdentificationTypeNewDtoTest {
    @Test
    @DisplayName("testCreationAndPublicGetters - Must create object " +
        "and return properties correctly")
    public void testCreationAndPublicGetters() {
        IdentificationTypeNewDto identificationTypeNewDto =
            new IdentificationTypeNewDto("CC");

        assertEquals("CC", identificationTypeNewDto.getName());
    }
}
