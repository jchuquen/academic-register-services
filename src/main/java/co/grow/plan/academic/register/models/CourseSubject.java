package co.grow.plan.academic.register.models;

import javax.persistence.*;

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
    private int id;

    @ManyToOne
    @Column(nullable = false)
    private Course course;

    @ManyToOne
    @Column(nullable = false)
    private Subject subject;

    public CourseSubject() {
    }

    public CourseSubject(Course course, Subject subject) {
        this.course = course;
        this.subject = subject;
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
}
