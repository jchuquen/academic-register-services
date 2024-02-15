package co.grow.plan.academic.register.application.academicplan.period.services;

import co.grow.plan.academic.register.application.academicplan.period.ports.spi.PeriodRepositorySPI;
import co.grow.plan.academic.register.application.shared.tests.services.BasicServiceForBasicEntityTest;
import co.grow.plan.academic.register.application.shared.tests.services.PropertyError;
import co.grow.plan.academic.register.domain.academicplan.period.model.Period;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class PeriodServiceTest extends BasicServiceForBasicEntityTest<
    Period,
    PeriodRepositorySPI,
    PeriodService
    > {

    @Mock
    private PeriodRepositorySPI repositorySPI;

    @InjectMocks
    private PeriodService periodService;

    @Override
    protected PeriodRepositorySPI getRepository() {
        return repositorySPI;
    }

    @Override
    protected PeriodService getService() {
        return periodService;
    }

    @Override
    protected List<Period> getMockedEntitiesToList() {
        return List.of(
            new Period(1, "2023/III", true, 0L),
            new Period(2, "2021/IV", false, 1L),
            new Period(3, "2023/II", false, 0L)
        );
    }

    @Override
    protected Period getEntityWithNullFields() {
        return new Period(null,null, null, null);
    }

    @Override
    protected Period getEntityToCreate() {
        return new Period(null, "2024/I", true, null);
    }

    @Override
    protected Period getPersistedEntity() {
        return new Period(1, "2024/I", true,  0L);
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
    protected Period getEntityWithUpdatedInfo(PropertyError propertyError) {
        String name = "2024/I";
        Long version = 0L;

        switch (propertyError) {
            case WRONG_VERSION -> version = 17L;
            case EMPTY_NAME -> name = "   ";
            case NULL_NAME -> name = null;
        }
        return new Period(1, name, false, version);
    }

    @Override
    protected Period getEntityWithDuplicatedName() {
        return new Period(13, "2024/I", true, 5L);
    }

    @Override
    protected Period getUpdatedEntity() {
        return new Period(1, "2024/I", true,  1L);
    }
}
