package co.grow.plan.academic.register.application.admissions.identificationtype.services;

import co.grow.plan.academic.register.application.admissions.identificationtype.ports.spi.IdentificationTypeRepositorySPI;
import co.grow.plan.academic.register.application.shared.tests.services.BasicServiceForBasicEntityTest;
import co.grow.plan.academic.register.application.shared.tests.services.PropertyError;
import co.grow.plan.academic.register.domain.admissions.identificationtype.model.IdentificationType;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class IdentificationTypeServiceTest extends BasicServiceForBasicEntityTest<
    IdentificationType,
    IdentificationTypeRepositorySPI,
    IdentificationTypeService
    > {

    @Mock
    private IdentificationTypeRepositorySPI repositorySPI;

    @InjectMocks
    private IdentificationTypeService identificationTypeService;

    @Override
    protected IdentificationTypeRepositorySPI getRepository() {
        return repositorySPI;
    }

    @Override
    protected IdentificationTypeService getService() {
        return identificationTypeService;
    }

    @Override
    protected List<IdentificationType> getMockedEntitiesToList() {
        return List.of(
            new IdentificationType(1, "CC", 0L),
            new IdentificationType(2, "TI", 1L),
            new IdentificationType(3, "RC", 0L)
        );
    }

    @Override
    protected IdentificationType getEntityWithNullFields() {
        return new IdentificationType(null,null, null);
    }

    @Override
    protected IdentificationType getEntityToCreate() {
        return new IdentificationType(null, "CC", null);
    }

    @Override
    protected IdentificationType getPersistedEntity() {
        return new IdentificationType(1, "CC", 0L);
    }

    @Override
    protected Integer getIdToUpdateOrDelete() {
        return 1;
    }

    @Override
    protected Integer getWrongIdToUpdateOrDelete() {
        return 22;
    }

    @Override
    protected IdentificationType getEntityWithUpdatedInfo(PropertyError propertyError) {
        String name = "CE";
        Long version = 0L;

        switch (propertyError) {
            case WRONG_VERSION -> version = 17L;
            case EMPTY_NAME -> name = "   ";
            case NULL_NAME -> name = null;
        }
        return new IdentificationType(1, name, version);
    }

    @Override
    protected IdentificationType getEntityWithDuplicatedName() {
        return new IdentificationType(13, "CE", 5L);
    }

    @Override
    protected IdentificationType getUpdatedEntity() {
        return new IdentificationType(1, "CE", 1L);
    }
}
