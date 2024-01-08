package co.grow.plan.academic.register.infrastructure.academicplan.course.repositories;

import co.grow.plan.academic.register.infrastructure.academicplan.course.entities.CourseJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseJpaRepository
    extends JpaRepository<CourseJpaEntity, Integer>
{
    Optional<CourseJpaEntity> getByName(String name);
}
