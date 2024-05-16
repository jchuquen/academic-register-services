package co.grow.plan.academic.register.domain.academicplan.period.model;

import co.grow.plan.academic.register.domain.shared.exceptions.EmptyPropertyException;
import co.grow.plan.academic.register.domain.shared.interfaces.BasicEntity;

import java.util.logging.Level;
import java.util.logging.Logger;

public record Period(Integer id, String name, Boolean active, Long version)
    implements BasicEntity {

    private static final Logger logger = Logger.getLogger(Period.class.getName());

    @Override
    public void validate() throws EmptyPropertyException {
        logger.log(Level.INFO, "Validating period instance");
        BasicEntity.super.validate();
        if (this.active() == null) {
            throw new EmptyPropertyException("active", this.getClass().getName());
        }
    }
}
