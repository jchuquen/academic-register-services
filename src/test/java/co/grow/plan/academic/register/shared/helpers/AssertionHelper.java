package co.grow.plan.academic.register.shared.helpers;

import co.grow.plan.academic.register.shared.domain.interfaces.IBasicEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AssertionHelper {
    public static <E extends IBasicEntity> void assertObjectProperties
        (E expected,E current) {
        assertEquals(expected.id(), current.id());
        assertEquals(expected.name(), current.name());
        assertEquals(expected.version(), current.version());
    }
}
