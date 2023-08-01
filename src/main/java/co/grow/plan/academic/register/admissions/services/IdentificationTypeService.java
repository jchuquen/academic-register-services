package co.grow.plan.academic.register.admissions.services;

import co.grow.plan.academic.register.admissions.dtos.IdentificationTypeDto;
import co.grow.plan.academic.register.admissions.dtos.IdentificationTypeNewDto;
import co.grow.plan.academic.register.admissions.models.IdentificationType;
import co.grow.plan.academic.register.admissions.repositories.IdentificationTypeDao;
import co.grow.plan.academic.register.shared.exceptions.ApiConflictException;
import co.grow.plan.academic.register.shared.exceptions.ApiError;
import co.grow.plan.academic.register.shared.exceptions.ApiMissingInformationException;
import co.grow.plan.academic.register.shared.exceptions.ApiNoEntityException;
import co.grow.plan.academic.register.shared.helpers.ValidationsHelper;
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
        validateConstrains(null, identificationTypeNewDto);

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
        Integer id, IdentificationTypeDto identificationTypeDto) {

        ValidationsHelper.validateIdsMatchingOrException(
            id, identificationTypeDto.getId());

        IdentificationType identificationType =
            validateIdentificationTypeIfExistsAndReturn(id);

        validateVersionsMatchOrException(
            identificationTypeDto, identificationType);

        validateIdentificationTypeInfo(identificationTypeDto);

        validateConstrains(id, identificationTypeDto);

        validateVersionsMatchOrException(identificationTypeDto,
            identificationType);

        identificationType =
            identificationTypeDao.save(
                updateIdentificationTypeFromIdentificationTypeDto(
                    identificationType,
                    identificationTypeDto
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
    }

    private void validateConstrains(Integer id,
        IdentificationTypeNewDto identificationTypeNewDto) {

        IdentificationType identificationType =
            identificationTypeDao.getByName(identificationTypeNewDto.getName());

        if (id == null) { // It's creating
            if (identificationType != null) {
                throw new ApiConflictException(
                    new ApiError(
                        "Identification type with same name already exists"
                    )
                );
            }

        } else { // It's updating
            if (identificationType != null &&
                !id.equals(identificationType.getId())) {
                throw new ApiConflictException(
                    new ApiError(
                        "Identification type with same name already exists"
                    )
                );
            }
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

    private void validateVersionsMatchOrException(
        IdentificationTypeDto identificationTypeDto,
        IdentificationType identificationType) {

        if (identificationTypeDto.getVersion() !=
            identificationType.getVersion()) {
            throw new ApiConflictException(
                new ApiError(
                    String.format(
                        "Information version is different. Try to refresh it")
                )
            );
        }
    }

    // Mappers
    private List<IdentificationTypeDto> listIdentificationTypeToListIdentificationTypeDto(
        Iterable<IdentificationType> identificationTypeList) {

        List<IdentificationTypeDto> listDto = new ArrayList<>();
        for (IdentificationType identificationType : identificationTypeList) {
            listDto.add(
                identificationTypeToIdentificationTypeDto(identificationType));
        }
        return listDto;
    }

    private IdentificationTypeDto identificationTypeToIdentificationTypeDto(
        IdentificationType identificationType) {

        return new IdentificationTypeDto(
            identificationType.getId(),
            identificationType.getName(),
            identificationType.getVersion()
        );
    }

    private IdentificationType updateIdentificationTypeFromIdentificationTypeDto(
        IdentificationType identificationType,
        IdentificationTypeDto identificationTypeDto) {

        identificationType.setName(identificationTypeDto.getName());

        return identificationType;
    }
}
