package co.grow.plan.academic.register.application.academicplan.subject.services;


import co.grow.plan.academic.register.application.academicplan.subject.ports.api.SubjectServiceAPI;
import co.grow.plan.academic.register.application.academicplan.subject.ports.spi.SubjectRepositorySPI;
import co.grow.plan.academic.register.domain.academicplan.subject.model.Subject;
import co.grow.plan.academic.register.shared.application.generics.services.BasicServiceForBasicEntity;

public final class SubjectService
    extends BasicServiceForBasicEntity<Subject, SubjectRepositorySPI>
    implements SubjectServiceAPI {

    public SubjectService(SubjectRepositorySPI repository) {
        super(repository);
    }
}
