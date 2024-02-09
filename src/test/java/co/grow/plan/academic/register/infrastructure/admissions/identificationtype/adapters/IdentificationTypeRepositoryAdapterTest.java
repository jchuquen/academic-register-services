package co.grow.plan.academic.register.infrastructure.admissions.identificationtype.adapters;

import co.grow.plan.academic.register.AcademicRegisterServicesApplication;
import co.grow.plan.academic.register.domain.admissions.identificationtype.model.IdentificationType;
import co.grow.plan.academic.register.infrastructure.admissions.identificationtype.entities.IdentificationTypeJpaEntity;
import co.grow.plan.academic.register.infrastructure.admissions.identificationtype.mappers.InfrastructureVsDomainIdentificationTypeEntityMapper;
import co.grow.plan.academic.register.infrastructure.admissions.identificationtype.repositories.IdentificationTypeJpaRepository;
import co.grow.plan.academic.register.shared.infrastructure.generics.adapters.BasicRepositoryAdapterForBasicEntityTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

@TestPropertySource("/application-test.properties")
@SpringBootTest(classes = AcademicRegisterServicesApplication.class)
public class IdentificationTypeRepositoryAdapterTest extends
    BasicRepositoryAdapterForBasicEntityTest<
            IdentificationTypeJpaEntity,
            IdentificationType,
            IdentificationTypeJpaRepository,
        InfrastructureVsDomainIdentificationTypeEntityMapper,
            IdentificationTypeRepositoryAdapter
        > {

    @Autowired
    public IdentificationTypeRepositoryAdapterTest(
        JdbcTemplate jdbcTemplate,
        IdentificationTypeRepositoryAdapter repositoryAdapter
    ) {
        super(jdbcTemplate, repositoryAdapter);
    }

    @Override
    protected String getWrongName() {
        return "TP";
    }

    @Override
    protected String getExistingName() {
        return "RC";
    }

    @Override
    protected String getRestartAutoincrementSentence() {
        return "ALTER TABLE identification_type ALTER COLUMN id RESTART WITH 4";
    }

    @Override
    protected String getFirstExampleInsertSentence() {
        return "insert into identification_type (id, name, version) values(1, 'CC', 0)";
    }

    @Override
    protected String getSecondExampleInsertSentence() {
        return "insert into identification_type (id, name, version) values(2, 'TI', 1)";
    }

    @Override
    protected String getThirdExampleInsertSentence() {
        return "insert into identification_type (id, name, version) values(3, 'RC', 0)";
    }

    @Override
    protected String getDeleteAllSentence() {
        return "delete from identification_type";
    }

    @Override
    protected List<IdentificationType> getEntitiesToList() {
        return List.of(
            new IdentificationType(1, "CC", 0L),
            new IdentificationType(2, "TI", 1L),
            new IdentificationType(3, "RC", 0L)
        );
    }

    @Override
    protected IdentificationType getPersistedEntity() {
        return new IdentificationType(
            3, "RC", 0L
        );
    }

    @Override
    protected Integer getWrongIdToUpdateOrDelete() {
        return 7;
    }

    @Override
    protected IdentificationType getEntityToCreate() {
        return new IdentificationType(
            null, "CE", null
        );
    }

    @Override
    protected IdentificationType getCreatedEntity() {
        return new IdentificationType(
            4, "CE", 0L
        );
    }

    @Override
    protected Integer getIdToUpdateOrDelete() {
        return 3;
    }

    @Override
    protected IdentificationType getEntityWithUpdatedInfo() {
        return new IdentificationType(3, "RR", 0L);
    }

    @Override
    protected IdentificationType getUpdatedEntity() {
        return new IdentificationType(
            3, "RR", 1L
        );
    }
}
