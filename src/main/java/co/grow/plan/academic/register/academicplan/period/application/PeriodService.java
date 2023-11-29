package co.grow.plan.academic.register.academicplan.period.application;

import co.grow.plan.academic.register.academicplan.period.domain.IPeriodDao;
import co.grow.plan.academic.register.academicplan.period.domain.Period;
import co.grow.plan.academic.register.shared.exceptions.ApiConflictException;
import co.grow.plan.academic.register.shared.exceptions.ApiError;
import co.grow.plan.academic.register.shared.generics.BasicService;
import co.grow.plan.academic.register.shared.generics.IValidable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PeriodService
    extends BasicService<
        Period,
        IPeriodDao,
        PeriodDto,
        PeriodNewDto,
        IPeriodMapper
    >
    implements IPeriodService {

    public PeriodService(
        IPeriodDao dao, IPeriodMapper mapper) {
        super(dao, mapper);
    }

    // Validations
    @Override
    protected void validateConstrains(Integer id, IValidable dto) {

        PeriodNewDto periodNewDto = (PeriodNewDto) dto;

        Optional<Period> optionalPeriod =
            super.dao.getByName(periodNewDto.getName());

        if (id == null) { // It's creating
            if (optionalPeriod.isPresent()) {
                throw new ApiConflictException(
                    new ApiError(
                        "Period with same name already exists"
                    )
                );
            }

        } else { // It's updating
            if (optionalPeriod.isPresent() &&
                !id.equals(optionalPeriod.get().getId())) {
                throw new ApiConflictException(
                    new ApiError(
                        "Period with same name already exists"
                    )
                );
            }
        }
    }
}
