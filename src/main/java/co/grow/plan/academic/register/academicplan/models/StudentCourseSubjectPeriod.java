package co.grow.plan.academic.register.academicplan.models;


import co.grow.plan.academic.register.admissions.models.Student;

import javax.persistence.*;

@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {
                                "course_subject_id",
                                "student_id",
                                "period_id"
                        }
                )
        }
)
public class StudentCourseSubjectPeriod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @Column(nullable = false)
    private CourseSubject courseSubject;

    @ManyToOne
    @Column(nullable = false)
    private Student student;

    @ManyToOne
    @Column(nullable = false)
    private Period period;

    @Column(nullable = false)
    private double score;

    public StudentCourseSubjectPeriod() {
    }

    public StudentCourseSubjectPeriod(CourseSubject courseSubject, Student student, Period period, double score) {
        this.courseSubject = courseSubject;
        this.student = student;
        this.period = period;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CourseSubject getCourseSubject() {
        return courseSubject;
    }

    public void setCourseSubject(CourseSubject courseSubject) {
        this.courseSubject = courseSubject;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "StudentCourseSubjectPeriod{" +
                "id=" + id +
                ", courseSubject=" + courseSubject +
                ", student=" + student +
                ", period=" + period +
                ", score=" + score +
                '}';
    }
}
