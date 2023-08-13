package co.grow.plan.academic.register.admissions.identificationtype.application;

import java.util.List;

public interface IIdentificationTypeService {
    List<IdentificationTypeDto> list();

    IdentificationTypeDto create(
            IdentificationTypeNewDto identificationTypeNewDto);

    IdentificationTypeDto findById (Integer id);

    IdentificationTypeDto update(
            Integer id,
            IdentificationTypeDto identificationTypeDto);

    void delete(Integer id);
}
