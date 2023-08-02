package co.grow.plan.academic.register.admissions.identificationtype.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity()
public class IdentificationType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private int id;

    @Column(unique = true, nullable = false)
    private String name;

    @Version
    private long version;

    public IdentificationType() {
    }

    public IdentificationType(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "IdentificationType{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", version=" + version +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IdentificationType that = (IdentificationType) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
