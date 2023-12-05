package temp.infrastructure.academicplan.course.entities;

import jakarta.persistence.*;
import lombok.Data;
import temp.shared.infrastructure.generics.IInfEntity;

@Entity
@Data
public class CourseEntity implements IInfEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private int id;

    @Column(unique = true, nullable = false)
    private String name;

    @Version
    private long version;
}
