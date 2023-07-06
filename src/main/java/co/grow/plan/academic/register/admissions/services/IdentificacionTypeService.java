package co.grow.plan.academic.register.admissions.services;

import co.grow.plan.academic.register.admissions.models.IdentificationType;
import co.grow.plan.academic.register.admissions.repositories.IdentificationTypeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class IdentificacionTypeService {

    @Autowired
    IdentificationTypeDao identificationTypeDao;

    //TODO: Validate name required
    public IdentificationType createIdentificationType(String name) {
        //TODO: Validate name not duplicated
        return identificationTypeDao.save(new IdentificationType(name));
    }
}
