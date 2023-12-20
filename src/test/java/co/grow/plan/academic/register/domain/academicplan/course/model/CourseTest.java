package co.grow.plan.academic.register.domain.academicplan.course.model;

import co.grow.plan.academic.register.shared.domain.exceptions.ApiMissingInformationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CourseTest {

    @Test
    public void shouldThrowExceptionWhenNameIsNull () {
        Course course = new Course(1, null, 0);

        assertThrows(
            ApiMissingInformationException.class,
            course::validate
        );
    }

    @Test
    public void shouldThrowExceptionWhenNameIsEmpty() {
        Course course = new Course(1, "   ", 0);

        assertThrows(
            ApiMissingInformationException.class,
            course::validate
        );
    }

    @Test
    public void shouldNotThrowExceptionWhenNameNotNullAndNotEmpty() {
        Course course = new Course(1, "Software Development", 0);

        assertDoesNotThrow(
            course::validate
        );
    }
}
