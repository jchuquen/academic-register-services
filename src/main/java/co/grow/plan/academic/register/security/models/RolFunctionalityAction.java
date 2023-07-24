package co.grow.plan.academic.register.security.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {
                                "rol_id",
                                "functionality_id",
                                "action_id"
                        }
                )
        }
)
public class RolFunctionalityAction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Rol rol;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Functionality functionality;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Action action;

    public RolFunctionalityAction() {
    }

    public RolFunctionalityAction(Rol rol, Functionality functionality, Action action) {
        this.rol = rol;
        this.functionality = functionality;
        this.action = action;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Functionality getFunctionality() {
        return functionality;
    }

    public void setFunctionality(Functionality functionality) {
        this.functionality = functionality;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "RolFunctionalityAction{" +
                "id=" + id +
                ", rol=" + rol +
                ", functionality=" + functionality +
                ", action=" + action +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RolFunctionalityAction that = (RolFunctionalityAction) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
