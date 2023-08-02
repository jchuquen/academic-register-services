package co.grow.plan.academic.register.academicplan.coursesubject.domain;

import co.grow.plan.academic.register.academicplan.course.domain.Course;
import co.grow.plan.academic.register.academicplan.subject.domain.Subject;
import co.grow.plan.academic.register.admissions.teacher.domain.Teacher;

import javax.persistence.*;
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
public class CourseSubject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private int id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Course course;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Subject subject;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private Teacher teacher;

    public CourseSubject() {
    }

    public CourseSubject(Course course, Subject subject, Teacher teacher) {
        this.course = course;
        this.subject = subject;
        this.teacher = teacher;
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
    public String toString() {
        return "CourseSubject{" +
                "id=" + id +
                ", course=" + course +
                ", subject=" + subject +
                ", teacher=" + teacher +
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
