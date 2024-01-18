package co.grow.plan.academic.register.application.academicplan.subject.services;


import co.grow.plan.academic.register.application.academicplan.subject.ports.api.ISubjectServiceAPI;
import co.grow.plan.academic.register.application.academicplan.subject.ports.spi.ISubjectRepositorySPI;
import co.grow.plan.academic.register.domain.academicplan.subject.model.Subject;
import co.grow.plan.academic.register.shared.application.generics.services.BasicServiceForBasicEntity;

public final class SubjectService
    extends BasicServiceForBasicEntity<Subject, ISubjectRepositorySPI>
    implements ISubjectServiceAPI {

    public SubjectService(ISubjectRepositorySPI repository) {
        super(repository);
    }
}
