package co.grow.plan.academic.register.admissions.identificationtype.application;

import co.grow.plan.academic.register.shared.generics.IBasicService;

import java.util.List;

public interface IIdentificationTypeService
    extends IBasicService<IdentificationTypeDto, IdentificationTypeNewDto> {

    List<IdentificationTypeDto> list();

    IdentificationTypeDto create(
            IdentificationTypeNewDto identificationTypeNewDto);

    IdentificationTypeDto findById (Integer id);

    IdentificationTypeDto update(
            Integer id,
            IdentificationTypeDto identificationTypeDto);

    void delete(Integer id);
}
