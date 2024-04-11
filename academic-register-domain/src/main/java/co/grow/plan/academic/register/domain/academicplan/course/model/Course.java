package co.grow.plan.academic.register.domain.academicplan.course.model;

import co.grow.plan.academic.register.domain.shared.interfaces.BasicEntity;

public record Course (Integer id, String name, Long version)
    implements BasicEntity {
}
