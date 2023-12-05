package temp.domain.academicplan.course.model;

import temp.shared.application.exceptions.ApiError;
import temp.shared.application.exceptions.ApiMissingInformationException;
import temp.shared.domain.interfaces.IEntity;

public record Course (int id, String name, long version)
    implements IEntity {
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
