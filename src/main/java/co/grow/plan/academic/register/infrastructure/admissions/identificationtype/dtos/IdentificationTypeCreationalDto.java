package co.grow.plan.academic.register.infrastructure.admissions.identificationtype.dtos;

import co.grow.plan.academic.register.shared.infrastructure.generics.ICreationalDto;

public record IdentificationTypeCreationalDto(String name)
    implements ICreationalDto {
}