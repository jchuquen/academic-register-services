package co.grow.plan.academic.register.admissions.models;

import co.grow.plan.academic.register.security.models.User;

import javax.persistence.Entity;

@Entity
public final class Teacher extends User {
    public Teacher(int id, IdentificationType identificationType,
                          String identificationNumber, String firstName,
                          String lastName, String phone, String emailAddress,
                          String address, String password, boolean isActive) {
        super(id, identificationType, identificationNumber, firstName,
                lastName, phone, emailAddress, address, password, isActive);
    }
}
