package co.grow.plan.academic.register.academicplan.period.application;

import co.grow.plan.academic.register.shared.generics.IIdentifiableAndVersionable;

public class PeriodDto
    extends PeriodNewDto
    implements IIdentifiableAndVersionable {
    private int id;
    private long version;

    public PeriodDto() {
    }

    public PeriodDto(int id, String name, Boolean active, long version) {
        super(name, active);
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
        return "PeriodDto{" +
            "id=" + id +
            ", name='" + super.getName() + '\'' +
            ", active=" + super.getActive() +
            ", version=" + version +
            '}';
    }
}
