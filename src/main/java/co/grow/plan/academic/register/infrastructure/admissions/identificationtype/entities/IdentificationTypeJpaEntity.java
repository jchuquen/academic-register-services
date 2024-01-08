package co.grow.plan.academic.register.infrastructure.admissions.identificationtype.entities;

import co.grow.plan.academic.register.shared.infrastructure.generics.IInfEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "identification_type")
@Data
public class IdentificationTypeJpaEntity implements IInfEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;

    @Version
    private Long version;
}
