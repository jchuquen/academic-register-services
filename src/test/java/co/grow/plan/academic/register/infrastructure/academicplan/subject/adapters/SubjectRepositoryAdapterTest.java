package co.grow.plan.academic.register.infrastructure.academicplan.subject.adapters;

import co.grow.plan.academic.register.AcademicRegisterServicesApplication;
import co.grow.plan.academic.register.domain.academicplan.subject.model.Subject;
import co.grow.plan.academic.register.infrastructure.academicplan.subject.entities.SubjectJpaEntity;
import co.grow.plan.academic.register.infrastructure.academicplan.subject.mappers.InfrastructureVsDomainSubjectEntityMapper;
import co.grow.plan.academic.register.infrastructure.academicplan.subject.repositories.SubjectJpaRepository;
import co.grow.plan.academic.register.shared.infrastructure.generics.adapters.BasicRepositoryAdapterForBasicEntityTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

@TestPropertySource("/application-test.properties")
@SpringBootTest(classes = AcademicRegisterServicesApplication.class)
public class SubjectRepositoryAdapterTest extends
    BasicRepositoryAdapterForBasicEntityTest<
        SubjectJpaEntity,
        Subject,
        SubjectJpaRepository,
        InfrastructureVsDomainSubjectEntityMapper,
        SubjectRepositoryAdapter
    > {

    @Autowired
    public SubjectRepositoryAdapterTest(
        JdbcTemplate jdbcTemplate,
        SubjectRepositoryAdapter repositoryAdapter
    ) {
        super(jdbcTemplate, repositoryAdapter);
    }

    @Override
    protected String getWrongName() {
        return "Physics";
    }

    @Override
    protected String getExistingName() {
        return "Pure Maths";
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
    protected Subject getPersistedEntity() {
        return new Subject(
            3, "Pure Maths", 0L
        );
    }

    @Override
    protected Integer getWrongIdToUpdateOrDelete() {
        return 7;
    }

    @Override
    protected Subject getEntityToCreate() {
        return new Subject(null, "Astronomy", null);
    }

    @Override
    protected Subject getCreatedEntity() {
        return new Subject(4, "Astronomy", 0L);
    }

    @Override
    protected Integer getIdToUpdateOrDelete() {
        return 3;
    }

    @Override
    protected Subject getEntityWithUpdatedInfo() {
        return new Subject(
            3, "RR", 0L
        );
    }

    @Override
    protected Subject getUpdatedEntity() {
        return new Subject(
            3, "RR", 1L
        );
    }
}
