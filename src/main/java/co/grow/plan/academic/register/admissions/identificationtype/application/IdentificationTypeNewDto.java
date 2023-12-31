package co.grow.plan.academic.register.admissions.identificationtype.application;

import co.grow.plan.academic.register.shared.exceptions.ApiError;
import co.grow.plan.academic.register.shared.exceptions.ApiMissingInformationException;
import co.grow.plan.academic.register.shared.generics.INoIdentifiableAndVersionable;
import co.grow.plan.academic.register.shared.generics.IValidable;

public class IdentificationTypeNewDto
    implements INoIdentifiableAndVersionable, IValidable {

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
    public void validate() {
        if (name == null ||
            name.trim().isEmpty()) {
            throw new ApiMissingInformationException(
                new ApiError(
                    "Field 'name' is required for Information type"
                )
            );
        }
    }

    @Override
    public String toString() {
        return "IdentificationTypeNewDto{" +
                "name='" + name + '\'' +
                '}';
    }
}
