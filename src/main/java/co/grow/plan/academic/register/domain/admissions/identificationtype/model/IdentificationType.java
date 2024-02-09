package co.grow.plan.academic.register.domain.admissions.identificationtype.model;

import co.grow.plan.academic.register.shared.domain.interfaces.BasicEntity;

public record IdentificationType(Integer id, String name, Long version)
    implements BasicEntity {
}
