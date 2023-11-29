package temp.infrastructure.academicplan.course;

import co.grow.plan.academic.register.shared.generics.IIdentifiableAndVersionable;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class CourseEntity implements IIdentifiableAndVersionable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private int id;

    @Column(unique = true, nullable = false)
    private String name;

    @Version
    private long version;

    public CourseEntity() {
    }

    public CourseEntity(String name) {
        this.name = name;
    }
}
