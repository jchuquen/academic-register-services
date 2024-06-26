package co.grow.plan.academic.register.infrastructure.admissions.identificationtype.entities;

import co.grow.plan.academic.register.infrastructure.shared.generics.InfBasicEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "identification_type")
@Data
public class IdentificationTypeJpaEntity implements InfBasicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;

    @Version
    private Long version;
}
