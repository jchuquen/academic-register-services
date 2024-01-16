package co.grow.plan.academic.register.infrastructure.academicplan.course.adapters;

import co.grow.plan.academic.register.AcademicRegisterServicesApplication;
import co.grow.plan.academic.register.domain.academicplan.course.model.Course;
import co.grow.plan.academic.register.shared.infrastructure.generics.adapters.BasicRepositoryAdapterTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

@TestPropertySource("/application-test.properties")
@SpringBootTest(classes = AcademicRegisterServicesApplication.class)
public class CourseRepositoryAdapterTest extends
    BasicRepositoryAdapterTest<
        Course,
        CourseRepositoryAdapter
    > {

    @Autowired
    public CourseRepositoryAdapterTest(
        JdbcTemplate jdbcTemplate,
        CourseRepositoryAdapter repositoryAdapter
    ) {
        super(jdbcTemplate, repositoryAdapter);
    }

    @Override
    protected String getRestartAutoincrementSentence() {
        return "ALTER TABLE course ALTER COLUMN id RESTART WITH 4";
    }

    @Override
    protected String getFirstExampleInsertSentence() {
        return "insert into course (id, name, version) values(1, 'Software Development', 0)";
    }

    @Override
    protected String getSecondExampleInsertSentence() {
        return "insert into course (id, name, version) values(2, 'Microprocessors', 1)";
    }

    @Override
    protected String getThirdExampleInsertSentence() {
        return "insert into course (id, name, version) values(3, 'Pure Maths', 0)";
    }

    @Override
    protected String getDeleteAllSentence() {
        return "delete from course";
    }

    @Override
    protected List<Course> getEntitiesToList() {
        return List.of(
            new Course(1, "Software Development", 0L),
            new Course(2, "Microprocessors", 1L),
            new Course(3, "Pure Maths", 0L)
        );
    }

    @Override
    protected Course getPersistedEntity() {
        return new Course(
            3, "Pure Maths", 0L
        );
    }

    @Override
    protected Integer getWrongIdToUpdateOrDelete() {
        return 7;
    }

    @Override
    protected Course getEntityToCreate() {
        return new Course(null, "Astronomy", null);
    }

    @Override
    protected Course getCreatedEntity() {
        return new Course(4, "Astronomy", 0L);
    }

    @Override
    protected Integer getIdToUpdateOrDelete() {
        return 3;
    }

    @Override
    protected Course getEntityWithUpdatedInfo() {
        return new Course(
            3, "RR", 0L
        );
    }

    @Override
    protected Course getUpdatedEntity() {
        return new Course(
            3, "RR", 1L
        );
    }
}
