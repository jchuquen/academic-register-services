package co.grow.plan.academic.register.admissions.identificationtype.application;

import java.util.List;

public interface IIdentificationTypeService {
    List<IdentificationTypeDto> listIdentificationTypes();

    IdentificationTypeDto createIdentificationType(
            IdentificationTypeNewDto identificationTypeNewDto);

    IdentificationTypeDto findIdentificationTypeById (Integer id);

    IdentificationTypeDto updateIdentificationType(
            Integer id,
            IdentificationTypeDto identificationTypeDto);
    void deleteIdentificationType(Integer id);
}
