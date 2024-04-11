package co.grow.plan.academic.register.domain.shared.helpers;

import co.grow.plan.academic.register.domain.shared.interfaces.BasicEntity;
import co.grow.plan.academic.register.domain.shared.interfaces.Entity;
import org.junit.jupiter.api.Assertions;

public class AssertionHelper {
    public static <E extends Entity> void assertObjectProperties
        (E expected,E current) {
        Assertions.assertEquals(expected.id(), current.id());
        Assertions.assertEquals(expected.version(), current.version());
    }

    public static <E extends BasicEntity> void assertObjectProperties
        (E expected,E current) {
        Assertions.assertEquals(expected.id(), current.id());
        Assertions.assertEquals(expected.name(), current.name());
        Assertions.assertEquals(expected.version(), current.version());
    }
}
