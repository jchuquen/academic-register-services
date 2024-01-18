package co.grow.plan.academic.register.application.academicplan.subject.services;

import co.grow.plan.academic.register.AcademicRegisterServicesApplication;
import co.grow.plan.academic.register.application.academicplan.subject.ports.spi.ISubjectRepositorySPI;
import co.grow.plan.academic.register.domain.academicplan.subject.model.Subject;
import co.grow.plan.academic.register.shared.application.generics.services.BasicServiceIntegrationForBasicEntityTest;
import co.grow.plan.academic.register.shared.application.generics.services.PropertyError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

@TestPropertySource("/application-test.properties")
@SpringBootTest(classes = AcademicRegisterServicesApplication.class)
public class SubjectServiceIntegrationTest extends BasicServiceIntegrationForBasicEntityTest<
    Subject,
    ISubjectRepositorySPI,
    SubjectService
    > {

    @Autowired
    public SubjectServiceIntegrationTest(
        JdbcTemplate jdbcTemplate, SubjectService subjectService
    ) {
        super(jdbcTemplate, subjectService);
    }


    @Override
    protected String getRestartAutoincrementSentence() {
        return "ALTER TABLE subject ALTER COLUMN id RESTART WITH 4";
    }

    @Override
    protected String getFirstExampleInsertSentence() {
        return "insert into subject (id, name, version) values(1, 'Software Development', 0)";
    }

    @Override
    protected String getSecondExampleInsertSentence() {
        return "insert into subject (id, name, version) values(2, 'Microprocessors', 1)";
    }

    @Override
    protected String getThirdExampleInsertSentence() {
        return "insert into subject (id, name, version) values(3, 'Pure Maths', 0)";
    }

    @Override
    protected String getDeleteAllSentence() {
        return "delete from subject";
    }

    @Override
    protected List<Subject> getEntitiesToList() {
        return List.of(
            new Subject(1, "Software Development", 0L),
            new Subject(2, "Microprocessors", 1L),
            new Subject(3, "Pure Maths", 0L)
        );
    }

    @Override
    protected Subject getConflictedEntityAtCreate() {
        return new Subject(
            null, "Software Development", null
        );
    }

    @Override
    protected Subject getEntityToCreate() {
        return new Subject(
            null, "Astronomy", null
        );
    }

    @Override
    protected Subject getCreatedEntity() {
        return new Subject(
            4, "Astronomy", 0L
        );
    }

    @Override
    protected Subject getPersistedEntity() {
        return new Subject(
            3, "Pure Maths", 0L
        );
    }

    @Override
    protected Integer getWrongIdToUpdateOrDelete() {
        return 9;
    }

    @Override
    protected Subject getWrongEntityWithUpdatedInfo() {
        return new Subject(
            9, "Astronomy", 0L
        );
    }

    @Override
    protected Subject getConflictedEntityAtUpdating() {
        return new Subject(
            3, "Software Development", 0L
        );
    }

    @Override
    protected Subject getEntityWithUpdatedInfo(PropertyError propertyError) {
        String name = "Pure Physics";
        Long version = 0L;

        switch (propertyError) {
            case WRONG_VERSION -> version = 17L;
            case EMPTY_NAME -> name = "   ";
            case NULL_NAME -> name = null;
        }
        return new Subject(3, name, version);
    }

    @Override
    protected Integer getIdToUpdateOrDelete() {
        return 3;
    }

    @Override
    protected Subject getUpdatedEntity() {
        return new Subject(
            3, "Pure Physics", 1L
        );
    }
}

