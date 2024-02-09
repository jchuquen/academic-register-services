package co.grow.plan.academic.register.infrastructure.academicplan.subject.entities;

import co.grow.plan.academic.register.shared.infrastructure.generics.InfBasicEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "subject")
@Data
public class SubjectJpaEntity implements InfBasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;

    @Version
    private Long version;
}
