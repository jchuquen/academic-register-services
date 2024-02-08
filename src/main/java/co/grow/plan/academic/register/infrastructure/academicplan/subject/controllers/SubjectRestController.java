package co.grow.plan.academic.register.infrastructure.academicplan.subject.controllers;

import co.grow.plan.academic.register.application.academicplan.subject.ports.api.ISubjectServiceAPI;
import co.grow.plan.academic.register.domain.academicplan.subject.model.Subject;
import co.grow.plan.academic.register.infrastructure.academicplan.subject.dtos.SubjectCreationalDto;
import co.grow.plan.academic.register.infrastructure.academicplan.subject.dtos.SubjectFullDto;
import co.grow.plan.academic.register.infrastructure.academicplan.subject.mappers.InfrastructureDtoVsDomainSubjectMapper;
import co.grow.plan.academic.register.shared.infrastructure.generics.BasicRestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/academic-plans/subjects")
public class SubjectRestController
    extends BasicRestController<
    SubjectFullDto,
    SubjectCreationalDto,
    Subject,
    ISubjectServiceAPI,
    InfrastructureDtoVsDomainSubjectMapper
    >
    implements ISubjectRestController {

    public SubjectRestController(ISubjectServiceAPI service, InfrastructureDtoVsDomainSubjectMapper mapper) {
        super(service, mapper);
    }
}
