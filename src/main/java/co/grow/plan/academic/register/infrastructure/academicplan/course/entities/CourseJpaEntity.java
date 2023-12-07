package co.grow.plan.academic.register.infrastructure.academicplan.course.entities;

import co.grow.plan.academic.register.shared.infrastructure.generics.IInfEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class CourseJpaEntity implements IInfEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private int id;

    @Column(unique = true, nullable = false)
    private String name;

    @Version
    private long version;
}
