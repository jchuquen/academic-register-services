package co.grow.plan.academic.register.academicplan.course.application;

import co.grow.plan.academic.register.shared.generics.IIdentifiableAndVersionable;

public class CourseDto
    extends CourseNewDto
    implements IIdentifiableAndVersionable {

    private int id;

    private long version;

    public CourseDto() {
        this.id = id;
        this.version = version;
    }

    public CourseDto(int id, String name, long version) {
        super(name);
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
        return "CourseDto{" +
            "id=" + id +
            ", name='" + super.getName() + '\'' +
            ", version=" + version +
            "} " + super.toString();
    }
}
