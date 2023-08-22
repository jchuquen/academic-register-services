package co.grow.plan.academic.register.academicplan.period.application;

import co.grow.plan.academic.register.shared.exceptions.ApiError;
import co.grow.plan.academic.register.shared.exceptions.ApiMissingInformationException;
import co.grow.plan.academic.register.shared.generics.INoIdentifiableAndVersionable;
import co.grow.plan.academic.register.shared.generics.IValidable;

public class PeriodNewDto
    implements INoIdentifiableAndVersionable, IValidable {

    private String name;

    private Boolean active;

    public PeriodNewDto() {
    }

    public PeriodNewDto(String name, Boolean active) {
        this.name = name;
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public void validate() {
        if (name == null ||
            name.trim().isEmpty()) {
            throw new ApiMissingInformationException(
                new ApiError(
                    "Field 'name' is required for Period"
                )
            );
        }

        if (active == null ) {
            throw new ApiMissingInformationException(
                new ApiError(
                    "Field 'active' is required for Period"
                )
            );
        }
    }

    @Override
    public String toString() {
        return "PeriodNewDto{" +
            "name='" + name + '\'' +
            ", active=" + active +
            '}';
    }
}
