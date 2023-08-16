package co.grow.plan.academic.register.academicplan.subject.infrastructure;

import co.grow.plan.academic.register.academicplan.subject.application.SubjectDto;
import co.grow.plan.academic.register.academicplan.subject.application.SubjectNewDto;
import co.grow.plan.academic.register.shared.generics.IBasicRestController;

public interface ISubjectRest
    extends IBasicRestController<SubjectDto, SubjectNewDto> {
}
