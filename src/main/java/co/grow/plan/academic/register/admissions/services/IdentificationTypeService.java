package co.grow.plan.academic.register.admissions.services;

import co.grow.plan.academic.register.admissions.dtos.IdentificationTypeDto;
import co.grow.plan.academic.register.admissions.dtos.IdentificationTypeNewDto;
import co.grow.plan.academic.register.admissions.models.IdentificationType;
import co.grow.plan.academic.register.admissions.repositories.IdentificationTypeDao;
import co.grow.plan.academic.register.exceptions.ApiConflictException;
import co.grow.plan.academic.register.exceptions.ApiError;
import co.grow.plan.academic.register.exceptions.ApiMissingInformationException;
import co.grow.plan.academic.register.exceptions.ApiNoEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class IdentificationTypeService implements IIdentificationTypeService {

    @Autowired
    IdentificationTypeDao identificationTypeDao;


    //TODO: Use Spring validations in all methods to check incoming information
    @Override
    public List<IdentificationTypeDto> listIdentificationTypes() {
        Iterable<IdentificationType> identificationTypeList =
            identificationTypeDao.findAll();

        return listIdentificationTypeToListIdentificationTypeDto(
            identificationTypeList);
    }

    @Override
    public IdentificationTypeDto createIdentificationType(
        IdentificationTypeNewDto identificationTypeNewDto) {

        validateIdentificationTypeInfo(identificationTypeNewDto);

        IdentificationType identificationType =
            new IdentificationType(
                identificationTypeNewDto.getName()
            );

        identificationType = identificationTypeDao.save(identificationType);

        return identificationTypeToIdentificationTypeDto(identificationType);
    }

    @Override
    public IdentificationTypeDto findIdentificationTypeById(Integer id) {

        IdentificationType identificationType =
            validateIdentificationTypeIfExistsAndReturn(id);

        return identificationTypeToIdentificationTypeDto(
            identificationType);
    }

    @Override
    public IdentificationTypeDto updateIdentificationType(
        Integer id, IdentificationTypeNewDto identificationTypeNewDto) {

        IdentificationType identificationType =
            validateIdentificationTypeIfExistsAndReturn(id);

        validateIdentificationTypeInfo(identificationTypeNewDto);

        identificationType =
            identificationTypeDao.save(
                updateIdentificationTypeFromIdentificationTypeDto(
                    identificationType,
                    identificationTypeNewDto
                )
            );

        return identificationTypeToIdentificationTypeDto(identificationType);
    }

    @Override
    public void deleteIdentificationType(Integer id) {
        validateIdentificationTypeIfExistsAndReturn(id);
        identificationTypeDao.deleteById(id);
    }

    // Validations
    private void validateIdentificationTypeInfo(
        IdentificationTypeNewDto identificationTypeNewDto) {

        if (identificationTypeNewDto.getName() == null ||
            identificationTypeNewDto.getName().trim().isEmpty()) {
            throw new ApiMissingInformationException(
                new ApiError(
                    "Field 'name' is required in Information type"
                )
            );
        }

        IdentificationType identificationType =
            identificationTypeDao.getByName(identificationTypeNewDto.getName());
        if (identificationType != null) {
            throw new ApiConflictException(
                new ApiError(
                    "Identification type with same name already exists"
                )
            );
        }
    }

    private IdentificationType validateIdentificationTypeIfExistsAndReturn(
        Integer id) {

        Optional<IdentificationType> optionalIdentificationType =
            identificationTypeDao.findById(id);

        if (optionalIdentificationType.isEmpty()) {
            throw new ApiNoEntityException(
                new ApiError(
                    String.format(
                        "Identification type with id %s doesn't exist",
                        id)
                )
            );
        }
        return optionalIdentificationType.get();
    }

    // Mappers
    private List<IdentificationTypeDto> listIdentificationTypeToListIdentificationTypeDto(Iterable<IdentificationType> identificationTypeList) {
        List<IdentificationTypeDto> listDto = new ArrayList<>();
        for (IdentificationType identificationType : identificationTypeList) {
            IdentificationTypeDto identificationTypeDto =
                new IdentificationTypeDto(
                    identificationType.getId(),
                    identificationType.getName()
                );
            listDto.add(identificationTypeDto);
        }
        return listDto;
    }

    private IdentificationTypeDto identificationTypeToIdentificationTypeDto(
        IdentificationType identificationType) {

        return new IdentificationTypeDto(
            identificationType.getId(),
            identificationType.getName()
        );
    }

    private IdentificationType updateIdentificationTypeFromIdentificationTypeDto(
        IdentificationType identificationType,
        IdentificationTypeNewDto identificationTypeNewDto) {
        identificationType.setName(identificationTypeNewDto.getName());

        return identificationType;
    }
}
