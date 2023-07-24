package co.grow.plan.academic.register.admissions.dtos;

public class IdentificationTypeDto {
    private int id;
    private String name;

    public IdentificationTypeDto() {
    }

    public IdentificationTypeDto(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "IdentificationTypeDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
