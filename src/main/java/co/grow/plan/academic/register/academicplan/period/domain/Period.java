package co.grow.plan.academic.register.academicplan.period.domain;

import co.grow.plan.academic.register.shared.generics.IIdentifiableAndVersionable;

import javax.persistence.*;

@Entity
public class Period implements IIdentifiableAndVersionable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean active;

    @Version
    private long version;

    public Period() {
    }

    public Period(String name, boolean active) {
        this.name = name;
        this.active = active;
    }

    public Period(int id, String name, boolean active, long version) {
        this.id = id;
        this.name = name;
        this.active = active;
        this.version = version;
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

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Period{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", active=" + active +
            ", version=" + version +
            '}';
    }
}
