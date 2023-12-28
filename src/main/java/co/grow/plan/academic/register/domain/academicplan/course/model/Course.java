package co.grow.plan.academic.register.domain.academicplan.course.model;

import co.grow.plan.academic.register.shared.domain.interfaces.IBasicEntity;

public record Course (Integer id, String name, Long version)
    implements IBasicEntity{
}
