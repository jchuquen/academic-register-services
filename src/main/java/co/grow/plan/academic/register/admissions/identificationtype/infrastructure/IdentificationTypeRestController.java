package co.grow.plan.academic.register.admissions.identificationtype.infrastructure;

import co.grow.plan.academic.register.admissions.identificationtype.application.IdentificationTypeDto;
import co.grow.plan.academic.register.admissions.identificationtype.application.IdentificationTypeNewDto;
import co.grow.plan.academic.register.admissions.identificationtype.application.IIdentificationTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IdentificationTypeRestController implements IIdentificationTypeRest {

    @Autowired
    private IIdentificationTypeService identificationTypeService;

    @Override
    public ResponseEntity<List<IdentificationTypeDto>> listIdentificationTypes() {
        List<IdentificationTypeDto> listIdentificationTypes =
                identificationTypeService.listIdentificationTypes();

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
                identificationTypeService.createIdentificationType(
                        identificationTypeNewDto),
                HttpStatus.CREATED
        );
    }

    @Override
    public ResponseEntity<IdentificationTypeDto> findIdentificationTypeById(Integer id) {

        IdentificationTypeDto identificationTypeDto =
                identificationTypeService.findIdentificationTypeById(id);

        if (identificationTypeDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(identificationTypeDto,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<IdentificationTypeDto> updateIdentificationType(
            Integer id,
            IdentificationTypeDto identificationTypeDto) {

        return new ResponseEntity<>(
            identificationTypeService.updateIdentificationType(
                id, identificationTypeDto),
            HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<Void> deleteIdentificationType(Integer id) {
        identificationTypeService.deleteIdentificationType(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
