package co.grow.plan.academic.register.academicplan.subject.infrastructure;

import co.grow.plan.academic.register.academicplan.subject.application.ISubjectService;
import co.grow.plan.academic.register.academicplan.subject.application.SubjectDto;
import co.grow.plan.academic.register.academicplan.subject.application.SubjectNewDto;
import co.grow.plan.academic.register.shared.generics.BasicRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/academic-plans/subjects")
public class SubjectRestController
    extends BasicRestController<
    SubjectDto,
    SubjectNewDto,
    ISubjectService
    >
    implements ISubjectRest {

    @Autowired
    public SubjectRestController(ISubjectService service) {
        super(service);
    }
}
