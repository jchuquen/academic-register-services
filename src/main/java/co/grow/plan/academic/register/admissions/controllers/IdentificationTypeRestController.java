package co.grow.plan.academic.register.admissions.controllers;

import co.grow.plan.academic.register.admissions.dtos.IdentificationTypeDto;
import co.grow.plan.academic.register.admissions.dtos.IdentificationTypeNewDto;
import co.grow.plan.academic.register.admissions.interfaces.IIdentificationTypeRest;
import co.grow.plan.academic.register.admissions.services.IdentificacionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IdentificationTypeRestController implements IIdentificationTypeRest {

    @Autowired
    private IdentificacionTypeService identificacionTypeService;

    @Override
    public ResponseEntity<List<IdentificationTypeDto>> listIdentificationTypes() {
        return null;
    }

    @Override
    public ResponseEntity<IdentificationTypeDto> createIdentificationType(IdentificationTypeNewDto identificationTypeNewDto) {
        return null;
    }

    @Override
    public ResponseEntity<IdentificationTypeDto> findIdentificationTypeById(Integer id) {
        return null;
    }

    @Override
    public ResponseEntity<IdentificationTypeDto> updateIdentificationType(IdentificationTypeNewDto identificationTypeNewDto) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteIdentificationType(Integer id) {
        return null;
    }

    /*@PostMapping("/api/rest/identificationType")
    public IdentificationType createIdentificacionType(@RequestParam String name) {
        return identificacionTypeService.createIdentificationType(name);
    }*/

}
