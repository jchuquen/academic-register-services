package co.grow.plan.academic.register.admissions.services;

import co.grow.plan.academic.register.admissions.dtos.IdentificationTypeDto;
import co.grow.plan.academic.register.admissions.models.IdentificationType;
import co.grow.plan.academic.register.admissions.repositories.IdentificationTypeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional
public class IdentificacionTypeService implements IIdentificationTypeService{

    @Autowired
    IdentificationTypeDao identificationTypeDao;

    @Override
    public List<IdentificationTypeDto> listIdentificationTypes() {
        Iterable<IdentificationType> identificationTypeList =
                identificationTypeDao.findAll();

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

    //TODO: Validate name required
    /*public IdentificationType createIdentificationType(String name) {
        //TODO: Validate name not duplicated
        return identificationTypeDao.save(new IdentificationType(name));
    }*/
}
