package co.grow.plan.academic.register.academicplan.subject.application;

import co.grow.plan.academic.register.shared.generics.IBasicService;

import java.util.List;

public interface ISubjectService
    extends IBasicService<SubjectDto, SubjectNewDto> {

    List<SubjectDto> list();

    SubjectDto create(
        SubjectNewDto dto);

    SubjectDto findById (Integer id);

    SubjectDto update(
        Integer id,
        SubjectDto dto);

    void delete(Integer id);
}
