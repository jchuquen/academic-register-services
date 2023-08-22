package co.grow.plan.academic.register.academicplan.coursesubject.application;

import co.grow.plan.academic.register.shared.exceptions.ApiError;
import co.grow.plan.academic.register.shared.exceptions.ApiMissingInformationException;
import co.grow.plan.academic.register.shared.generics.INoIdentifiableAndVersionable;
import co.grow.plan.academic.register.shared.generics.IValidable;

public class CourseSubjectNewDto
    implements INoIdentifiableAndVersionable, IValidable {

    private Integer courseId;
    private Integer subjectId;

    public CourseSubjectNewDto() {
    }

    public CourseSubjectNewDto(Integer courseId, Integer subjectId) {
        this.courseId = courseId;
        this.subjectId = subjectId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    @Override
    public void validate() {
        if (courseId == null ||
            courseId.equals(0)) {
            throw new ApiMissingInformationException(
                new ApiError(
                    "Course ID is required for Course - Subject relationship"
                )
            );
        }

        if (subjectId == null ||
            subjectId.equals(0)) {
            throw new ApiMissingInformationException(
                new ApiError(
                    "Subject ID is required for Course - Subject relationship"
                )
            );
        }
    }

    @Override
    public String toString() {
        return "CourseSubjectNewDto{" +
            "courseId=" + courseId +
            ", subjectId=" + subjectId +
            '}';
    }
}
