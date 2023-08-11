package co.grow.plan.academic.register.admissions.identificationtype.application;

import co.grow.plan.academic.register.admissions.identificationtype.domain.IdentificationType;
import co.grow.plan.academic.register.admissions.identificationtype.domain.IdentificationTypeDao;
import co.grow.plan.academic.register.shared.exceptions.ApiConflictException;
import co.grow.plan.academic.register.shared.exceptions.ApiError;
import co.grow.plan.academic.register.shared.exceptions.ApiNoEntityException;
import co.grow.plan.academic.register.shared.helpers.ValidationsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class IdentificationTypeService implements IIdentificationTypeService {

    @Autowired
    private IdentificationTypeDao identificationTypeDao;

    @Autowired
    private IIdentificationTypeMapper mapper;


    //TODO: Use Spring validations in all methods to check incoming information
    @Override
    public List<IdentificationTypeDto> listIdentificationTypes() {
        Iterable<IdentificationType> identificationTypeList =
            identificationTypeDao.findAll();

        return mapper.enityListToFullDtoList(identificationTypeList);
    }

    @Override
    public IdentificationTypeDto createIdentificationType(
        IdentificationTypeNewDto identificationTypeNewDto) {

        ValidationsHelper.validateNotNull(identificationTypeNewDto,
            "IdentificationType Object");

        identificationTypeNewDto.validateInfo();

        validateConstrains(null, identificationTypeNewDto);

        IdentificationType identificationType =
            mapper.noIdAndVersionDtoToEntity(identificationTypeNewDto);

        identificationType = identificationTypeDao.save(identificationType);

        return mapper.entityToFullDto(identificationType);
    }

    @Override
    public IdentificationTypeDto findIdentificationTypeById(Integer id) {

        ValidationsHelper.validateNotNull(id, "ID");

        IdentificationType identificationType =
            validateInstanceIfExistsAndReturn(id);

        return mapper.entityToFullDto(identificationType);
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
            validateInstanceIfExistsAndReturn(id);

        ValidationsHelper.validateVersionsMatchOrException(
            identificationTypeDto.getVersion(), identificationType.getVersion());

        identificationTypeDto.validateInfo();

        validateConstrains(id, identificationTypeDto);

        identificationType =
            identificationTypeDao.save(
                mapper.updateEntityFromNoIdAndVersionDto(identificationType,
                    mapper.fullDtoToNoIdAndVersionDto(identificationTypeDto))
            );

        return mapper.entityToFullDto(identificationType);
    }

    @Override
    public void deleteIdentificationType(Integer id) {
        ValidationsHelper.validateNotNull(id, "ID");
        validateInstanceIfExistsAndReturn(id);
        identificationTypeDao.deleteById(id);
    }

    // Validations
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

    private IdentificationType validateInstanceIfExistsAndReturn(
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
}
