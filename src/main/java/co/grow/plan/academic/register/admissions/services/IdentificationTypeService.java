package co.grow.plan.academic.register.admissions.services;

import co.grow.plan.academic.register.admissions.dtos.IdentificationTypeDto;
import co.grow.plan.academic.register.admissions.dtos.IdentificationTypeNewDto;
import co.grow.plan.academic.register.admissions.models.IdentificationType;
import co.grow.plan.academic.register.admissions.repositories.IdentificationTypeDao;
import co.grow.plan.academic.register.exceptions.ApiError;
import co.grow.plan.academic.register.exceptions.ApiException;
import co.grow.plan.academic.register.exceptions.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class IdentificationTypeService implements IIdentificationTypeService{

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

    private void validateIdentificationTypeInfo(
            IdentificationTypeNewDto identificationTypeNewDto) {

        if (identificationTypeNewDto.getName() == null ||
                identificationTypeNewDto.getName().trim().isEmpty()) {
            throw new ApiException(new ApiError(ErrorCode.MISSING_INFORMATION,
                "Field 'name' is required in Information type"));
        }

        IdentificationType identificationType =
                identificationTypeDao.getByName(identificationTypeNewDto.getName());
        if (identificationType != null) {
            throw new ApiException(new ApiError(ErrorCode.CONFLICT,
                "Identification type with same name already exists"));
        }
    }

    @Override
    public IdentificationTypeDto findIdentificationTypeById(Integer id) {
        Optional<IdentificationType> optionalIdentificationType =
                identificationTypeDao.findById(id);
        if (optionalIdentificationType.isEmpty()) {
            return null;
        }
        else {
            return identificationTypeToIdentificationTypeDto(
                    optionalIdentificationType.get());
        }
    }

    @Override
    public IdentificationTypeDto updateIdentificationType(
            Integer id,
            IdentificationTypeNewDto identificationTypeNewDto) {

        Optional<IdentificationType> optionalIdentificationType =
                identificationTypeDao.findById(id);

        if (optionalIdentificationType.isEmpty()) {
            return null;
        }

        IdentificationType identificationType =
                identificationTypeDao.save(
                        updateIdentificationTypeFromIdentificationTypeDto(
                                optionalIdentificationType.get(),
                                identificationTypeNewDto
                        )
                );

        return identificationTypeToIdentificationTypeDto(identificationType);
    }

    @Override
    public void deleteIdentificationType(Integer id) {
        Optional<IdentificationType> optionalIdentificationType =
                identificationTypeDao.findById(id);

        //TODO: Pending to throw 404 error when no resource
        if (optionalIdentificationType.isEmpty()) {
            return;
        }

        identificationTypeDao.deleteById(id);
    }


    // Mappers
    private List<IdentificationTypeDto> listIdentificationTypeToListIdentificationTypeDto(Iterable<IdentificationType> identificationTypeList) {
        List<IdentificationTypeDto> listDto = new ArrayList<>();
        Iterator<IdentificationType> iterator = identificationTypeList.iterator();
        while (iterator.hasNext()) {
            IdentificationType identificationType = iterator.next();
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

        IdentificationTypeDto identificationTypeDto =
                new IdentificationTypeDto(
                        identificationType.getId(),
                        identificationType.getName()
                );
        return identificationTypeDto;
    }

    private IdentificationType updateIdentificationTypeFromIdentificationTypeDto(
            IdentificationType identificationType,
            IdentificationTypeNewDto identificationTypeNewDto) {
        identificationType.setName(identificationTypeNewDto.getName());

        return identificationType;
    }
}
