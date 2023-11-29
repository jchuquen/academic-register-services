package temp.domain.academicplan.course.model;

import temp.shared.exceptions.ApiError;
import temp.shared.exceptions.ApiMissingInformationException;
import temp.shared.generics.IEntity;
import temp.shared.generics.IValidable;

public record Course (int id, String name, long version)
    implements IEntity, IValidable {
    @Override
    public void validate() {
        if (this.name == null || this.name.isBlank()) {
            throw new ApiMissingInformationException(
                new ApiError(
                    "Field 'name' is required in 'Course' entity"
                )
            );
        }
    }
}
