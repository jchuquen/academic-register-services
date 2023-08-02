package co.grow.plan.academic.register.admissions.administrative.domain;

import co.grow.plan.academic.register.admissions.identificationtype.domain.IdentificationType;
import co.grow.plan.academic.register.security.user.domain.User;

import javax.persistence.Entity;

@Entity
public final class Administrative extends User {
    public Administrative(int id, IdentificationType identificationType,
                   String identificationNumber, String firstName,
                   String lastName, String phone, String emailAddress,
                   String address, String password, boolean isActive) {
        super(id, identificationType, identificationNumber, firstName,
                lastName, phone, emailAddress, address, password, isActive);
    }
}
