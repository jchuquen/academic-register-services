package co.grow.plan.academic.register.infrastructure.academicplan.course.entities;

import co.grow.plan.academic.register.shared.infrastructure.generics.IInfEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Data;

@Entity
@Table(name = "course")
@Data
public class CourseJpaEntity implements IInfEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;

    @Version
    private Long version;
}
