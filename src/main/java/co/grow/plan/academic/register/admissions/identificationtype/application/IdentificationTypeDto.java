package co.grow.plan.academic.register.admissions.identificationtype.application;

public class IdentificationTypeDto extends IdentificationTypeNewDto{
    private int id;
    private long version;

    public IdentificationTypeDto() {
    }

    public IdentificationTypeDto(int id, String name, long version) {
        super(name);
        this.id = id;
        this.version = version;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "IdentificationTypeDto{" +
            "id=" + id +
            ", name='" + super.getName() + '\'' +
            ", version=" + version +
            '}';
    }
}
