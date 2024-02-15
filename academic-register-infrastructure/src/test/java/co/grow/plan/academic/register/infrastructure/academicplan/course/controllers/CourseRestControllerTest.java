package co.grow.plan.academic.register.infrastructure.academicplan.course.controllers;

import co.grow.plan.academic.register.application.shared.tests.services.PropertyError;
import co.grow.plan.academic.register.infrastructure.AcademicRegisterServicesApplication;
import co.grow.plan.academic.register.infrastructure.academicplan.course.dtos.CourseCreationalDto;
import co.grow.plan.academic.register.infrastructure.academicplan.course.dtos.CourseFullDto;
import co.grow.plan.academic.register.infrastructure.shared.tests.generics.controllers.BasicRestControllerForBasicEntityTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@TestPropertySource("/application-test.properties")
@SpringBootTest(classes = AcademicRegisterServicesApplication.class)
@AutoConfigureMockMvc
public class CourseRestControllerTest extends BasicRestControllerForBasicEntityTest<
        CourseFullDto,
        CourseCreationalDto
    > {

    @Autowired
    public CourseRestControllerTest(
        JdbcTemplate jdbcTemplate,
        MockMvc mockMvc,
        ObjectMapper objectMapper)
    {
        super(jdbcTemplate, mockMvc, objectMapper);
    }


    @Override
    protected String getRestartAutoincrementSentence() {
        return "ALTER TABLE course ALTER COLUMN id RESTART WITH 4";
    }

    @Override
    protected String getFirstExampleInsertSentence() {
        return "insert into course (id, name, version) values(1, 'Software Development', 0)";
    }

    @Override
    protected String getSecondExampleInsertSentence() {
        return "insert into course (id, name, version) values(2, 'Microprocessors', 1)";
    }

    @Override
    protected String getThirdExampleInsertSentence() {
        return "insert into course (id, name, version) values(3, 'Pure Maths', 0)";
    }

    @Override
    protected String getDeleteAllSentence() {
        return "delete from course";
    }

    @Override
    protected String getBaseResourceURL() {
        return "/v1/academic-plans/courses";
    }

    @Override
    protected List<CourseFullDto> getListOfEntities() {
        return List.of(
            new CourseFullDto(1, "Software Development", 0L),
            new CourseFullDto(2, "Microprocessors", 1L),
            new CourseFullDto(3, "Pure Maths", 0L)
        );
    }

    @Override
    protected CourseCreationalDto getCreationalDto(PropertyError propertyError) {
        String name = "Painting";

        switch (propertyError) {
            case NULL_NAME -> name = null;
            case DUPLICATED_NAME -> name = "Software Development";
        }
        return new CourseCreationalDto(name);
    }

    @Override
    protected CourseFullDto getCreatedEntity() {
        return new CourseFullDto(4, "Painting", 0L);
    }

    @Override
    protected String getIdentifiedResourceURL() {
        return getBaseResourceURL() + "/{id}";
    }

    @Override
    protected int getWrongResourceId() {
        return 8;
    }

    @Override
    protected int getExistingResourceId() {
        return 3;
    }

    @Override
    protected CourseFullDto getPersistedEntity() {
        return new CourseFullDto(3, "Pure Maths", 0L);
    }

    @Override
    protected CourseFullDto getFullDtoToUpdate(PropertyError propertyError) {
        Integer id = getExistingResourceId();
        String name = "Pure Physics";
        Long version = 0L;

        switch (propertyError) {
            case EMPTY_NAME -> name = "   ";
            case NULL_NAME -> name = null;
            case DUPLICATED_NAME -> name = "Software Development";
            case WRONG_ID -> id = getWrongResourceId();
            case WRONG_VERSION -> version = 17L;
        }
        return new CourseFullDto(id, name, version);
    }

    @Override
    protected CourseFullDto getUpdatedEntity() {
        return new CourseFullDto(
            getExistingResourceId(), "Pure Physics", 1L
        );
    }
}
