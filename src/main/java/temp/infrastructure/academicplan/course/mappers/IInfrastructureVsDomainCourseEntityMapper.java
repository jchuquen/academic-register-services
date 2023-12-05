package temp.infrastructure.academicplan.course.mappers;

import org.mapstruct.Mapper;
import temp.domain.academicplan.course.model.Course;
import temp.infrastructure.academicplan.course.entities.CourseEntity;
import temp.shared.infrastructure.generics.IBasicInfrastructureVsDomainEntityMapper;

@Mapper(componentModel = "spring")
public interface IInfrastructureVsDomainCourseEntityMapper
    extends IBasicInfrastructureVsDomainEntityMapper<
        Course,
        CourseEntity
        > {
}
