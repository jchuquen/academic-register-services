package co.grow.plan.academic.register.admissions.identificationtype.application;

import co.grow.plan.academic.register.admissions.identificationtype.domain.IdentificationType;
import co.grow.plan.academic.register.admissions.identificationtype.domain.IdentificationTypeDao;
import co.grow.plan.academic.register.shared.exceptions.*;
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
    private IdentificationTypeDao identificationTypeDao;


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

        ValidationsHelper.validateNotNull(identificationTypeNewDto,
            "IdentificationType Object");

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
        ValidationsHelper.validateNotNull(id, "ID");

        IdentificationType identificationType =
            validateIdentificationTypeIfExistsAndReturn(id);

        return identificationTypeToIdentificationTypeDto(
            identificationType);
    }

    @Override
    public IdentificationTypeDto updateIdentificationType(
        Integer id, IdentificationTypeDto identificationTypeDto) {

        ValidationsHelper.validateNotNull(id, "ID");
        ValidationsHelper.validateNotNull(identificationTypeDto,
            "IdentificationType Object");

        ValidationsHelper.validateIdsMatchingOrException(
            id, identificationTypeDto.getId());

        IdentificationType identificationType =
            validateIdentificationTypeIfExistsAndReturn(id);

        validateVersionsMatchOrException(
            identificationTypeDto, identificationType);

        validateIdentificationTypeInfo(identificationTypeDto);

        validateConstrains(id, identificationTypeDto);

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
        ValidationsHelper.validateNotNull(id, "ID");
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

        Optional<IdentificationType> optionalIdentificationType =
            identificationTypeDao.getByName(identificationTypeNewDto.getName());

        if (id == null) { // It's creating
            if (!optionalIdentificationType.isEmpty()) {
                throw new ApiConflictException(
                    new ApiError(
                        "Identification type with same name already exists"
                    )
                );
            }

        } else { // It's updating
            if (!optionalIdentificationType.isEmpty() &&
                !id.equals(optionalIdentificationType.get().getId())) {
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

    //TODO: Change this for MappStruct
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
