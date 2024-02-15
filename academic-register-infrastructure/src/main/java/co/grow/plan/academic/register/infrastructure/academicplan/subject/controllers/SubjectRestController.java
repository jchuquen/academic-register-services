package co.grow.plan.academic.register.infrastructure.academicplan.subject.controllers;

import co.grow.plan.academic.register.infrastructure.academicplan.subject.dtos.SubjectCreationalDto;
import co.grow.plan.academic.register.infrastructure.academicplan.subject.dtos.SubjectFullDto;
import co.grow.plan.academic.register.infrastructure.shared.generics.BasicRestController;

public interface SubjectRestController
    extends BasicRestController<SubjectFullDto, SubjectCreationalDto> {
}
