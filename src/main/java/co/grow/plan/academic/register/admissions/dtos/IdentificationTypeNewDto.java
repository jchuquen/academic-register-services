package co.grow.plan.academic.register.admissions.dtos;

public class IdentificationTypeNewDto {

    private String name;

    public IdentificationTypeNewDto() {
    }

    public IdentificationTypeNewDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "IdentificationTypeNewDto{" +
                "name='" + name + '\'' +
                '}';
    }
}
