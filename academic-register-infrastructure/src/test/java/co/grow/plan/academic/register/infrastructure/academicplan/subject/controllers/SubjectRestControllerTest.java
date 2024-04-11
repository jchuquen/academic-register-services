package co.grow.plan.academic.register.infrastructure.academicplan.subject.controllers;

import co.grow.plan.academic.register.application.shared.tests.services.PropertyError;
import co.grow.plan.academic.register.infrastructure.AcademicRegisterServicesApplication;
import co.grow.plan.academic.register.infrastructure.academicplan.subject.dtos.SubjectCreationalDto;
import co.grow.plan.academic.register.infrastructure.academicplan.subject.dtos.SubjectFullDto;
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
public class SubjectRestControllerTest extends BasicRestControllerForBasicEntityTest<
        SubjectFullDto,
        SubjectCreationalDto
    > {

    @Autowired
    public SubjectRestControllerTest(
        JdbcTemplate jdbcTemplate,
        MockMvc mockMvc,
        ObjectMapper objectMapper)
    {
        super(jdbcTemplate, mockMvc, objectMapper);
    }


    @Override
    protected String getRestartAutoincrementSentence() {
        return "ALTER TABLE subject ALTER COLUMN id RESTART WITH 4";
    }

    @Override
    protected String getFirstExampleInsertSentence() {
        return "insert into subject (id, name, version) values(1, 'Software Development', 0)";
    }

    @Override
    protected String getSecondExampleInsertSentence() {
        return "insert into subject (id, name, version) values(2, 'Microprocessors', 1)";
    }

    @Override
    protected String getThirdExampleInsertSentence() {
        return "insert into subject (id, name, version) values(3, 'Pure Maths', 0)";
    }

    @Override
    protected String getDeleteAllSentence() {
        return "delete from subject";
    }

    @Override
    protected String getBaseResourceURL() {
        return "/v1/academic-plans/subjects";
    }

    @Override
    protected List<SubjectFullDto> getListOfEntities() {
        return List.of(
            new SubjectFullDto(1, "Software Development", 0L),
            new SubjectFullDto(2, "Microprocessors", 1L),
            new SubjectFullDto(3, "Pure Maths", 0L)
        );
    }

    @Override
    protected SubjectCreationalDto getCreationalDto(PropertyError propertyError) {
        String name = "Painting";

        switch (propertyError) {
            case NULL_NAME -> name = null;
            case DUPLICATED_NAME -> name = "Software Development";
        }
        return new SubjectCreationalDto(name);
    }

    @Override
    protected SubjectFullDto getCreatedEntity() {
        return new SubjectFullDto(4, "Painting", 0L);
    }

    @Override
    protected String getIdentifiedResourceURL() {
        return getBaseResourceURL() + "/{id}";
    }

    @Override
    protected Integer getWrongResourceId() {
        return 8;
    }

    @Override
    protected Integer getExistingResourceId() {
        return 3;
    }

    @Override
    protected SubjectFullDto getPersistedEntity() {
        return new SubjectFullDto(3, "Pure Maths", 0L);
    }

    @Override
    protected SubjectFullDto getFullDtoToUpdate(PropertyError propertyError) {
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
        return new SubjectFullDto(id, name, version);
    }

    @Override
    protected SubjectFullDto getUpdatedEntity() {
        return new SubjectFullDto(
            getExistingResourceId(), "Pure Physics", 1L
        );
    }
}
