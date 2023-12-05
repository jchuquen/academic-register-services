package temp.infrastructure.academicplan.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import temp.infrastructure.academicplan.course.entities.CourseEntity;

import java.util.Optional;

@Repository
public interface CourseJPARepository extends JpaRepository<CourseEntity, Integer> {
    Optional<CourseEntity> getByName(String name);
}
