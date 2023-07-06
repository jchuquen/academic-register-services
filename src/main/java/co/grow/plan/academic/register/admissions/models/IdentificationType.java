package co.grow.plan.academic.register.admissions.models;

import javax.persistence.*;

@Entity
public class IdentificationType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String name;

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

    @Override
    public String toString() {
        return "IdentificationType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
