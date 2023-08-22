package co.grow.plan.academic.register.academicplan.coursesubject.application;

import co.grow.plan.academic.register.shared.generics.IIdentifiableAndVersionable;

public class CourseSubjectDto
    extends CourseSubjectNewDto
    implements IIdentifiableAndVersionable {

    private int id;
    private long version;

    public CourseSubjectDto(int id, Integer courseId, Integer subjectId, long version) {
        super(courseId, subjectId);
        this.id = id;
        this.version = version;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return "CourseSubjectDto{" +
            "id=" + id +
            ", courseId=" + super.getCourseId() +
            ", subjectId=" + super.getSubjectId() +
            ", version=" + version +
            "} " + super.toString();
    }
}
