package co.grow.plan.academic.register.security.models;

import co.grow.plan.academic.register.admissions.models.Person;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
public abstract class User extends Person {
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean isActive;


    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Rol rol;

    public User() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + super.getId() +
                ", identificationType=" + super.getIdentificationType() +
                ", identificationNumber='" + super.getIdentificationNumber() + '\'' +
                ", firstName='" + super.getFirstName() + '\'' +
                ", lastName='" + super.getLastName() + '\'' +
                ", phone='" + super.getPhone() + '\'' +
                ", emailAddress='" + super.getEmailAddress() + '\'' +
                ", address='" + super.getAddress() + '\'' +
                ", course=" + super.getCourse() + + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
