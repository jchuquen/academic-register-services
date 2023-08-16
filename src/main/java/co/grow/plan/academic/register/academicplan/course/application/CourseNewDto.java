package co.grow.plan.academic.register.academicplan.course.application;

import co.grow.plan.academic.register.shared.exceptions.ApiError;
import co.grow.plan.academic.register.shared.exceptions.ApiMissingInformationException;
import co.grow.plan.academic.register.shared.generics.INoIdentifiableAndVersionable;
import co.grow.plan.academic.register.shared.generics.IValidable;

public class CourseNewDto
    implements INoIdentifiableAndVersionable, IValidable {

    private String name;

    public CourseNewDto() {
    }
    public CourseNewDto(String name) {
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
                    "Field 'name' is required for course"
                )
            );
        }
    }

    @Override
    public String toString() {
        return "CourseNewDto{" +
            "name='" + name + '\'' +
            '}';
    }
}
