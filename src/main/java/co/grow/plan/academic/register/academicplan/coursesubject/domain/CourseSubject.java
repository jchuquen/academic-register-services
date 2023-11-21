package co.grow.plan.academic.register.academicplan.coursesubject.domain;

import co.grow.plan.academic.register.academicplan.course.domain.Course;
import co.grow.plan.academic.register.academicplan.subject.domain.Subject;
import co.grow.plan.academic.register.admissions.teacher.domain.Teacher;
import co.grow.plan.academic.register.shared.generics.IIdentifiableAndVersionable;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(
    uniqueConstraints = {
        @UniqueConstraint(
                columnNames = {
                        "course_id",
                        "subject_id"
                }
        )
    }
)
public class CourseSubject implements IIdentifiableAndVersionable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private int id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Course course;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY)
    private Teacher teacher;

    @Version
    private long version;

    public CourseSubject() {
    }

    public CourseSubject(Course course, Subject subject) {
        this.course = course;
        this.subject = subject;
    }

    public CourseSubject(Course course, Subject subject, Teacher teacher) {
        this.course = course;
        this.subject = subject;
        this.teacher = teacher;
    }

    public CourseSubject(int id, Course course, Subject subject,
        Teacher teacher, long version) {

        this.id = id;
        this.course = course;
        this.subject = subject;
        this.teacher = teacher;
        this.version = version;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
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
        return "CourseSubject{" +
                "id=" + id +
                ", course=" + course +
                ", subject=" + subject +
                ", teacher=" + teacher +
                ", version=" + version +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseSubject that = (CourseSubject) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
