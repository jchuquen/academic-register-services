package co.grow.plan.academic.register.admissions.teacher.domain;

import co.grow.plan.academic.register.admissions.identificationtype.domain.IdentificationType;
import co.grow.plan.academic.register.security.user.domain.User;

import jakarta.persistence.Entity;

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
