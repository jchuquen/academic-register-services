package co.grow.plan.academic.register.domain.academicplan.period.model;

import co.grow.plan.academic.register.domain.shared.exceptions.EmptyPropertyException;
import co.grow.plan.academic.register.domain.shared.interfaces.BasicEntity;

public record Period(Integer id, String name, Boolean active, Long version)
    implements BasicEntity {
    @Override
    public void validate() throws EmptyPropertyException {
        BasicEntity.super.validate();
        if (this.active() == null) {
            throw new EmptyPropertyException("active", this.getClass().getName());
        }
    }
}
