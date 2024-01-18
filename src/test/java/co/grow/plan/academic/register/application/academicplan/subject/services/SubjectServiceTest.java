package co.grow.plan.academic.register.application.academicplan.subject.services;

import co.grow.plan.academic.register.application.academicplan.subject.ports.spi.ISubjectRepositorySPI;
import co.grow.plan.academic.register.domain.academicplan.subject.model.Subject;
import co.grow.plan.academic.register.shared.application.generics.services.BasicServiceForBasicEntityTest;
import co.grow.plan.academic.register.shared.application.generics.services.PropertyError;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class SubjectServiceTest extends BasicServiceForBasicEntityTest<
    Subject,
    ISubjectRepositorySPI,
    SubjectService
    > {

    @Mock
    private ISubjectRepositorySPI repositorySPI;

    @InjectMocks
    private SubjectService subjectService;

    @Override
    protected ISubjectRepositorySPI getRepository() {
        return repositorySPI;
    }

    @Override
    protected SubjectService getService() {
        return subjectService;
    }

    @Override
    protected List<Subject> getMockedEntitiesToList() {
        return List.of(
            new Subject(1, "Software Development", 0L),
            new Subject(2, "Microprocessors", 1L),
            new Subject(3, "Pure Maths", 0L)
        );
    }

    @Override
    protected Subject getEntityWithNullFields() {
        return new Subject(null,null, null);
    }

    @Override
    protected Subject getEntityToCreate() {
        return new Subject(null, "Software Development", null);
    }

    @Override
    protected Subject getPersistedEntity() {
        return new Subject(1, "Software Development", 0L);
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
    protected Subject getEntityWithUpdatedInfo(PropertyError propertyError) {
        String name = "Software Architecture";
        Long version = 0L;

        switch (propertyError) {
            case WRONG_VERSION -> version = 17L;
            case EMPTY_NAME -> name = "   ";
            case NULL_NAME -> name = null;
        }
        return new Subject(1, name, version);
    }

    @Override
    protected Subject getEntityWithDuplicatedName() {
        return new Subject(13, "Software Architecture", 5L);
    }

    @Override
    protected Subject getUpdatedEntity() {
        return new Subject(1, "Software Architecture", 1L);
    }
}
