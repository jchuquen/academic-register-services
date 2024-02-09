package co.grow.plan.academic.register.domain.academicplan.subject.model;

import co.grow.plan.academic.register.shared.domain.interfaces.BasicEntity;

public record Subject(Integer id, String name, Long version)
    implements BasicEntity {
}
