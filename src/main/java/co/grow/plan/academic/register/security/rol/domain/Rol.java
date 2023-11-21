package co.grow.plan.academic.register.security.rol.domain;

import jakarta.persistence.*;

@Entity
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private int id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean isForStudent;

    @Column(nullable = false)
    private boolean isForAdministrative;

    @Column(nullable = false)
    private boolean isForTeacher;

    public Rol() {
    }

    public Rol(String name, boolean isForStudent,
       boolean isForAdministrative, boolean isForTeacher) {
        this.name = name;
        this.isForStudent = isForStudent;
        this.isForAdministrative = isForAdministrative;
        this.isForTeacher = isForTeacher;
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

    public boolean isForStudent() {
        return isForStudent;
    }

    public void setForStudent(boolean forStudent) {
        isForStudent = forStudent;
    }

    public boolean isForAdministrative() {
        return isForAdministrative;
    }

    public void setForAdministrative(boolean forAdministrative) {
        isForAdministrative = forAdministrative;
    }

    public boolean isForTeacher() {
        return isForTeacher;
    }

    public void setForTeacher(boolean forTeacher) {
        isForTeacher = forTeacher;
    }

    @Override
    public String toString() {
        return "Rol{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isForStudent=" + isForStudent +
                ", isForAdministrative=" + isForAdministrative +
                ", isForTeacher=" + isForTeacher +
                '}';
    }
}
