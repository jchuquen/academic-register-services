package co.grow.plan.academic.register.admissions.identificationtype.infrastructure;

import co.grow.plan.academic.register.admissions.identificationtype.application.IdentificationTypeDto;
import co.grow.plan.academic.register.admissions.identificationtype.application.IdentificationTypeNewDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface IIdentificationTypeRest {

    @GetMapping(
            value = "/v1/admissions/identification-types",
            produces = { "application/json", "application/xml" }
    )
    ResponseEntity<List<IdentificationTypeDto>> listIdentificationTypes();

    @PostMapping(
            value = "/v1/admissions/identification-types",
            produces = { "application/json", "application/xml" },
            consumes = { "application/json", "application/xml", "application/x-www-form-urlencoded" }
    )
    ResponseEntity<IdentificationTypeDto> createIdentificationType(
            @RequestBody IdentificationTypeNewDto identificationTypeNewDto);

    @GetMapping(
            value = "/v1/admissions/identification-types/{id}",
            produces = { "application/json", "application/xml" }
    )
    ResponseEntity<IdentificationTypeDto> findIdentificationTypeById(
            @PathVariable("id") Integer id);


    @PutMapping(
            value = "/v1/admissions/identification-types/{id}",
            produces = { "application/json", "application/xml" },
            consumes = { "application/json", "application/xml", "application/x-www-form-urlencoded" }
    )
    ResponseEntity<IdentificationTypeDto> updateIdentificationType(
            @PathVariable("id") Integer id,
            @RequestBody IdentificationTypeDto identificationTypeDto);

    @DeleteMapping(
            value = "/v1/admissions/identification-types/{id}",
            produces = { "application/json", "application/xml" }
    )
    ResponseEntity<Void> deleteIdentificationType(@PathVariable("id") Integer id);
}
