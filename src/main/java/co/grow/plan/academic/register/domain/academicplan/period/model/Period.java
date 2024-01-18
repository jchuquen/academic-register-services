package co.grow.plan.academic.register.domain.academicplan.period.model;

import co.grow.plan.academic.register.shared.domain.exceptions.EmptyPropertyException;
import co.grow.plan.academic.register.shared.domain.interfaces.IBasicEntity;

public record Period(Integer id, String name, Boolean active, Long version)
    implements IBasicEntity{
    @Override
    public void validate() throws EmptyPropertyException {
        IBasicEntity.super.validate();
        if (this.active() == null) {
            throw new EmptyPropertyException("active", this.getClass().getName());
        }
    }
}
