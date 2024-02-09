package co.grow.plan.academic.register.infrastructure.academicplan.period.entities;

import co.grow.plan.academic.register.shared.infrastructure.generics.InfBasicEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "period")
@Data
public class PeriodJpaEntity implements InfBasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean active;

    @Version
    private Long version;
}
