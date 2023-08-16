package co.grow.plan.academic.register.academicplan.subject.application;

import co.grow.plan.academic.register.shared.exceptions.ApiError;
import co.grow.plan.academic.register.shared.exceptions.ApiMissingInformationException;
import co.grow.plan.academic.register.shared.generics.INoIdentifiableAndVersionable;
import co.grow.plan.academic.register.shared.generics.IValidable;

public class SubjectNewDto
    implements INoIdentifiableAndVersionable, IValidable {
    private String name;

    public SubjectNewDto() {
    }
    public SubjectNewDto(String name) {
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
        return "SubjectNewDto{" +
            "name='" + name + '\'' +
            '}';
    }
}
