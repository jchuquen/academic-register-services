package co.grow.plan.academic.register.admissions.identificationtype.application;

import co.grow.plan.academic.register.admissions.identificationtype.domain.IdentificationType;
import co.grow.plan.academic.register.admissions.identificationtype.domain.IIdentificationTypeDao;
import co.grow.plan.academic.register.shared.exceptions.ApiConflictException;
import co.grow.plan.academic.register.shared.exceptions.ApiError;
import co.grow.plan.academic.register.shared.generics.BasicService;
import co.grow.plan.academic.register.shared.generics.IValidable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IdentificationTypeService
    extends BasicService<
        IdentificationType,
        IIdentificationTypeDao,
        IdentificationTypeDto,
        IdentificationTypeNewDto,
        IIdentificationTypeMapper
    >
    implements IIdentificationTypeService {

    @Autowired
    public IdentificationTypeService(
        IIdentificationTypeDao dao, IIdentificationTypeMapper mapper) {
        super(dao, mapper);
    }

    // Validations
    @Override
    protected void validateConstrains(Integer id, IValidable dto) {

        IdentificationTypeNewDto identificationTypeNewDto = (IdentificationTypeNewDto) dto;

        Optional<IdentificationType> optionalIdentificationType =
            super.dao.getByName(identificationTypeNewDto.getName());

        if (id == null) { // It's creating
            if (optionalIdentificationType.isPresent()) {
                throw new ApiConflictException(
                    new ApiError(
                        "Identification type with same name already exists"
                    )
                );
            }

        } else { // It's updating
            if (optionalIdentificationType.isPresent() &&
                !id.equals(optionalIdentificationType.get().getId())) {
                throw new ApiConflictException(
                    new ApiError(
                        "Identification type with same name already exists"
                    )
                );
            }
        }
    }
}
