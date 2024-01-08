package co.grow.plan.academic.register.infrastructure.admissions.identificationtype.controllers;

import co.grow.plan.academic.register.infrastructure.admissions.identificationtype.dtos.IdentificationTypeCreationalDto;
import co.grow.plan.academic.register.infrastructure.admissions.identificationtype.dtos.IdentificationTypeFullDto;
import co.grow.plan.academic.register.shared.infrastructure.generics.IBasicRestController;

public interface IIdentificationTypeRestController
    extends IBasicRestController<
        IdentificationTypeFullDto,
        IdentificationTypeCreationalDto
    > {
}
