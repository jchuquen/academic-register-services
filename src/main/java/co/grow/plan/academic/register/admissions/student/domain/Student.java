package co.grow.plan.academic.register.admissions.student.domain;

import co.grow.plan.academic.register.academicplan.course.domain.Course;
import co.grow.plan.academic.register.admissions.identificationtype.domain.IdentificationType;
import co.grow.plan.academic.register.security.user.domain.User;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
public final class Student extends User {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Course course;

    public Student() {
    }

    public Student(int id, IdentificationType identificationType,
                   String identificationNumber, String firstName,
                   String lastName, String phone, String emailAddress,
                   String address, String password, boolean isActive,
                   Course course) {
        super(id, identificationType, identificationNumber, firstName,
                lastName, phone, emailAddress, address, password, isActive);
        this.course = course;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
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
                ", isActive=" + super.isActive() +
                ", course=" + course +
                '}';
    }
}
