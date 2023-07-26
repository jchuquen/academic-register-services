package co.grow.plan.academic.register.admissions.services;

import co.grow.plan.academic.register.admissions.dtos.IdentificationTypeDto;
import co.grow.plan.academic.register.admissions.dtos.IdentificationTypeNewDto;

import java.util.List;

public interface IIdentificationTypeService {
    List<IdentificationTypeDto> listIdentificationTypes();

    IdentificationTypeDto createIdentificationType(
            IdentificationTypeNewDto identificationTypeNewDto);

    IdentificationTypeDto findIdentificationTypeById (Integer id);

    IdentificationTypeDto updateIdentificationType(
            Integer id,
            IdentificationTypeNewDto identificationTypeNewDto);
    void deleteIdentificationType(Integer id);
}
