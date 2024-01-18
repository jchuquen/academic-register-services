package co.grow.plan.academic.register.infrastructure.admissions.identificationtype.controllers;

import co.grow.plan.academic.register.AcademicRegisterServicesApplication;
import co.grow.plan.academic.register.infrastructure.admissions.identificationtype.dtos.IdentificationTypeCreationalDto;
import co.grow.plan.academic.register.infrastructure.admissions.identificationtype.dtos.IdentificationTypeFullDto;
import co.grow.plan.academic.register.shared.application.generics.services.PropertyError;
import co.grow.plan.academic.register.shared.infrastructure.generics.controllers.BasicRestControllerForBasicEntityTest;
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
public class IdentificationTypeRestControllerTest extends BasicRestControllerForBasicEntityTest<
        IdentificationTypeFullDto,
        IdentificationTypeCreationalDto
    > {

    @Autowired
    public IdentificationTypeRestControllerTest (
        JdbcTemplate jdbcTemplate,
        MockMvc mockMvc,
        ObjectMapper objectMapper)
    {
        super(jdbcTemplate, mockMvc, objectMapper);
    }

    @Override
    protected String getRestartAutoincrementSentence() {
        return "ALTER TABLE identification_type ALTER COLUMN id RESTART WITH 4";
    }

    @Override
    protected String getFirstExampleInsertSentence() {
        return "insert into identification_type (id, name, version) values(1, 'CC', 0)";
    }

    @Override
    protected String getSecondExampleInsertSentence() {
        return "insert into identification_type (id, name, version) values(2, 'TI', 1)";
    }

    @Override
    protected String getThirdExampleInsertSentence() {
        return "insert into identification_type (id, name, version) values(3, 'RC', 0)";
    }

    @Override
    protected String getDeleteAllSentence() {
        return "delete from identification_type";
    }

    @Override
    protected String getBaseResourceURL() {
        return "/v1/admissions/identification-types";
    }

    @Override
    protected List<IdentificationTypeFullDto> getListOfEntities() {
        return List.of(
            new IdentificationTypeFullDto(1, "CC", 0L),
            new IdentificationTypeFullDto(2, "TI", 1L),
            new IdentificationTypeFullDto(3, "RC", 0L)
        );
    }

    @Override
    protected IdentificationTypeCreationalDto getCreationalDto(PropertyError propertyError) {
        String name = "CE";

        switch (propertyError) {
            case NULL_NAME -> name = null;
            case DUPLICATED_NAME -> name = "CC";
        }
        return new IdentificationTypeCreationalDto(name);
    }

    @Override
    protected IdentificationTypeFullDto getCreatedEntity() {
        return new IdentificationTypeFullDto(4, "CE", 0L);
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
    protected IdentificationTypeFullDto getPersistedEntity() {
        return new IdentificationTypeFullDto(3, "RC", 0L);
    }

    @Override
    protected IdentificationTypeFullDto getFullDtoToUpdate(PropertyError propertyError) {
        Integer id = getExistingResourceId();
        String name = "RR";
        Long version = 0L;

        switch (propertyError) {
            case EMPTY_NAME -> name = "   ";
            case NULL_NAME -> name = null;
            case DUPLICATED_NAME -> name = "CC";
            case WRONG_ID -> id = getWrongResourceId();
            case WRONG_VERSION -> version = 17L;
        }
        return new IdentificationTypeFullDto(id, name, version);
    }

    @Override
    protected IdentificationTypeFullDto getUpdatedEntity() {
        return new IdentificationTypeFullDto(
            getExistingResourceId(), "RR", 1L
        );
    }
}
