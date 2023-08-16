package co.grow.plan.academic.register.academicplan.subject.application;

import co.grow.plan.academic.register.academicplan.subject.domain.Subject;
import co.grow.plan.academic.register.academicplan.subject.domain.SubjectDao;
import co.grow.plan.academic.register.shared.exceptions.ApiConflictException;
import co.grow.plan.academic.register.shared.exceptions.ApiError;
import co.grow.plan.academic.register.shared.generics.BasicService;
import co.grow.plan.academic.register.shared.generics.IValidable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubjectService
    extends BasicService<
        Subject,
        SubjectDao,
        SubjectDto,
        SubjectNewDto,
        ISubjectMapper
        >
    implements ISubjectService {

    private final String CONSTRAIN_NAME_ERROR = "Subject with same name already exists";

    @Autowired
    public SubjectService(
        SubjectDao dao, ISubjectMapper mapper) {
        super(dao, mapper);
    }

    // Validations
    @Override
    protected void validateConstrains(Integer id, IValidable dto) {

        SubjectNewDto subjectDto = (SubjectNewDto) dto;

        Optional<Subject> optionalSubject =
            super.dao.getByName(subjectDto.getName());

        if (id == null) { // It's creating
            if (!optionalSubject.isEmpty()) {
                throw new ApiConflictException(
                    new ApiError(CONSTRAIN_NAME_ERROR)
                );
            }

        } else { // It's updating
            if (!optionalSubject.isEmpty() &&
                !id.equals(optionalSubject.get().getId())) {
                throw new ApiConflictException(
                    new ApiError(CONSTRAIN_NAME_ERROR)
                );
            }
        }
    }
}
