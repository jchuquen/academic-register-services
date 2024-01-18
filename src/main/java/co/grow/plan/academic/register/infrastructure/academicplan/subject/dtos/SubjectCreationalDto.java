package co.grow.plan.academic.register.infrastructure.academicplan.subject.dtos;

import co.grow.plan.academic.register.shared.infrastructure.generics.ICreationalDto;

public record SubjectCreationalDto(String name)
    implements ICreationalDto {
}
