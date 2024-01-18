package co.grow.plan.academic.register.application.admissions.identificationtype.services;

import co.grow.plan.academic.register.AcademicRegisterServicesApplication;
import co.grow.plan.academic.register.application.admissions.identificationtype.ports.spi.IIdentificationTypeRepositorySPI;
import co.grow.plan.academic.register.domain.admissions.identificationtype.model.IdentificationType;
import co.grow.plan.academic.register.shared.application.generics.services.BasicServiceIntegrationForBasicEntityTest;
import co.grow.plan.academic.register.shared.application.generics.services.PropertyError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

@TestPropertySource("/application-test.properties")
@SpringBootTest(classes = AcademicRegisterServicesApplication.class)
public class IdentificationTypeServiceIntegrationTest extends
    BasicServiceIntegrationForBasicEntityTest<
            IdentificationType,
            IIdentificationTypeRepositorySPI,
            IdentificationTypeService
        > {

    @Autowired
    public IdentificationTypeServiceIntegrationTest(
        JdbcTemplate jdbcTemplate,
        IdentificationTypeService identificationTypeService
    ) {
        super(jdbcTemplate, identificationTypeService);
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
    protected IdentificationType getConflictedEntityAtCreate() {
        return new IdentificationType(
            null, "CC", null
        );
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
    protected IdentificationType getPersistedEntity() {
        return new IdentificationType(
            3, "RC", 0L
        );
    }

    @Override
    protected Integer getIdToUpdateOrDelete() {
        return 3;
    }

    @Override
    protected Integer getWrongIdToUpdateOrDelete() {
        return 5;
    }

    @Override
    protected IdentificationType getWrongEntityWithUpdatedInfo() {
        return new IdentificationType(
            5, "CE", 0L
        );
    }

    @Override
    protected IdentificationType getEntityWithUpdatedInfo(PropertyError propertyError) {
        String name = "RR";
        Long version = 0L;

        switch (propertyError) {
            case WRONG_VERSION -> version = 17L;
            case EMPTY_NAME -> name = "   ";
            case NULL_NAME -> name = null;
        }
        return new IdentificationType(3, name, version);
    }

    @Override
    protected IdentificationType getConflictedEntityAtUpdating() {
        return new IdentificationType(
            3, "CC", 0L);
    }



    @Override
    protected IdentificationType getUpdatedEntity() {
        return new IdentificationType(
            3, "RR", 1L);
    }
}

