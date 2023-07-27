package co.grow.plan.academic.register.admissions.controllers;

import co.grow.plan.academic.register.admissions.dtos.IdentificationTypeDto;
import co.grow.plan.academic.register.admissions.dtos.IdentificationTypeNewDto;
import co.grow.plan.academic.register.admissions.services.IIdentificationTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IdentificationTypeRestController implements IIdentificationTypeRest {

    @Autowired
    private IIdentificationTypeService identificacionTypeService;

    @Override
    public ResponseEntity<List<IdentificationTypeDto>> listIdentificationTypes() {
        List<IdentificationTypeDto> listIdentificationTypes =
                identificacionTypeService.listIdentificationTypes();

        if (listIdentificationTypes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(
                    listIdentificationTypes, HttpStatus.OK
            );
        }
    }

    @Override
    public ResponseEntity<IdentificationTypeDto> createIdentificationType(
            IdentificationTypeNewDto identificationTypeNewDto) {

        return new ResponseEntity<>(
                identificacionTypeService.createIdentificationType(
                        identificationTypeNewDto),
                HttpStatus.CREATED
        );
    }

    @Override
    public ResponseEntity<IdentificationTypeDto> findIdentificationTypeById(Integer id) {

        IdentificationTypeDto identificationTypeDto =
                identificacionTypeService.findIdentificationTypeById(id);

        if (identificationTypeDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(identificationTypeDto,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<IdentificationTypeDto> updateIdentificationType(
            Integer id,
            IdentificationTypeNewDto identificationTypeNewDto) {

        IdentificationTypeDto identificationTypeDto =
                identificacionTypeService.updateIdentificationType(
                        id, identificationTypeNewDto);

        //TODO: Pending to handle 409 error
        if (identificationTypeDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(
                identificacionTypeService.findIdentificationTypeById(id),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<Void> deleteIdentificationType(Integer id) {
        //TODO: Pending to throw 404 error when no resource
        identificacionTypeService.deleteIdentificationType(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
