package co.grow.plan.academic.register.admissions.identificationtype.infrastructure;

import co.grow.plan.academic.register.admissions.identificationtype.application.IdentificationTypeDto;
import co.grow.plan.academic.register.admissions.identificationtype.application.IdentificationTypeNewDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IIdentificationTypeRest {

    ResponseEntity<List<IdentificationTypeDto>> list();

    ResponseEntity<IdentificationTypeDto> create(
        IdentificationTypeNewDto identificationTypeNewDto);

    ResponseEntity<IdentificationTypeDto> findById(Integer id);

    ResponseEntity<IdentificationTypeDto> update(
        Integer id,
        IdentificationTypeDto identificationTypeDto);

    ResponseEntity<Void> delete(Integer id);
}
