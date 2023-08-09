package co.grow.plan.academic.register.admissions.identificationtype.infrastructure;

import co.grow.plan.academic.register.AcademicRegisterServicesApplication;
import co.grow.plan.academic.register.admissions.identificationtype.application.IIdentificationTypeService;
import co.grow.plan.academic.register.admissions.identificationtype.application.IdentificationTypeDto;
import co.grow.plan.academic.register.admissions.identificationtype.application.IdentificationTypeNewDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestPropertySource("/application-test.properties")
@SpringBootTest(classes = AcademicRegisterServicesApplication.class)
@AutoConfigureMockMvc
public class IdentificationTypeRestControllerTest {

    // Inserts
    private final String insertIdentificationTypeCC =
        "insert into identification_type (id, name, version) values(1, 'CC', 0)";
    private final String insertIdentificationTypeTI =
        "insert into identification_type (id, name, version) values(2, 'TI', 1)";
    private final String insertIdentificationTypeRC =
        "insert into identification_type (id, name, version) values(3, 'RC', 0)";

    //Deletes
    private final String deleteAllIdentificationTypes =
        "delete from identification_type";

    // Test Utilities
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    //Java Services
    @Autowired
    private IIdentificationTypeService identificationTypeService;

    @BeforeEach
    public void setupDatabase() {
        jdbcTemplate.execute(insertIdentificationTypeCC);
        jdbcTemplate.execute(insertIdentificationTypeTI);
        jdbcTemplate.execute(insertIdentificationTypeRC);
    }

    @Test
    @DisplayName("IdentificationTypeRestControllerTest - List - Must return a list of many objects")
    public void testListIdentificationTypesManyObjects() throws Exception {

        List<IdentificationTypeDto> expectedList =
            List.of(
                new IdentificationTypeDto(1, "CC", 0),
                new IdentificationTypeDto(2, "TI", 1),
                new IdentificationTypeDto(3, "RC", 0)
            );

        mockMvc.perform(get("/v1/admissions/identification-types")).
            andExpect(status().isOk()).
            andExpect(content().contentType(MediaType.APPLICATION_JSON)).
            andExpect(jsonPath("$", hasSize(3))).

            andExpect(jsonPath("$[0].id", is(1))).
            andExpect(jsonPath("$[0].name", is("CC"))).
            andExpect(jsonPath("$[0].version", is(0))).

            andExpect(jsonPath("$[1].id", is(2))).
            andExpect(jsonPath("$[1].name", is("TI"))).
            andExpect(jsonPath("$[1].version", is(1))).

            andExpect(jsonPath("$[2].id", is(3))).
            andExpect(jsonPath("$[2].name", is("RC"))).
            andExpect(jsonPath("$[2].version", is(0)));
    }

    @Test
    @DisplayName("IdentificationTypeRestControllerTest - List - Must return an empty list")
    public void testListIdentificationTypesEmptyList() throws Exception {

        jdbcTemplate.execute(deleteAllIdentificationTypes);


        mockMvc.perform(get("/v1/admissions/identification-types")).
            andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("IdentificationTypeRestControllerTest - Create - Must generate exception when " +
        "IdentificationType parameter is null")
    public void testCreateIdentificationTypeBadRequest1() throws Exception {

        mockMvc.perform(
            post("/v1/admissions/identification-types").
                contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("IdentificationTypeRestControllerTest - Create - Must generate exception when " +
        "name is null")
    public void testCreateIdentificationTypeBadRequest2() throws Exception {

        IdentificationTypeNewDto identificationTypeNewDto =
            new IdentificationTypeNewDto(null);

        mockMvc.perform(
            post("/v1/admissions/identification-types").
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(identificationTypeNewDto))
        ).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("IdentificationTypeRestControllerTest - Create - Must generate exception when " +
        "breaking name constraint")
    public void testCreateIdentificationTypeBreakingConstrain1() throws Exception {

        IdentificationTypeNewDto identificationTypeNewDto =
            new IdentificationTypeNewDto("CC");

        mockMvc.perform(
            post("/v1/admissions/identification-types").
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(identificationTypeNewDto))
        ).andExpect(status().isConflict());
    }

    @Test
    @DisplayName("IdentificationTypeRestControllerTest - Create - Must create new record and " +
        "return created information")
    public void testCreateIdentificationType() throws Exception{

        IdentificationTypeNewDto identificationTypeNewDto =
            new IdentificationTypeNewDto("CE");

        mockMvc.perform(
            post("/v1/admissions/identification-types").
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(identificationTypeNewDto))
        ).andExpect(status().isCreated()).
            andExpect(jsonPath("$.id", is(4))).
            andExpect(jsonPath("$.name", is("CE"))).
            andExpect(jsonPath("$.version", is(0)));

    }

    @Test
    @DisplayName("IdentificationTypeRestControllerTest - FindById - Must generate exception " +
        "when ID doesn't exist")
    public void testFindIdentificationTypeByIdIdDoesNotExist() throws Exception {
        mockMvc.perform(get("/v1/admissions/identification-types/{id}", 8)).
            andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("IdentificationTypeRestControllerTest - FindById - Must return dto " +
        "when ID does exist")
    public void testFindIdentificationTypeById() throws Exception {
        mockMvc.perform(get("/v1/admissions/identification-types/{id}", 2)).
            andExpect(status().isOk()).
            andExpect(jsonPath("$.id", is(2))).
            andExpect(jsonPath("$.name", is("TI"))).
            andExpect(jsonPath("$.version", is(1)));
    }

    @Test
    @DisplayName("IdentificationTypeRestControllerTest - UpdateIdentificationType - " +
        "Must generate exception when IdentificationType object is null")
    public void testUpdateIdentificationTypeObjectNull() throws Exception {
        mockMvc.perform(
                put("/v1/admissions/identification-types/{id}", 3).
                    contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("IdentificationTypeRestControllerTest - UpdateIdentificationType - " +
        "Must generate exception when IDs doesn't match")
    public void testUpdateIdentificationTypeIdsDoesNotMatch() throws Exception {
        Integer id = 5;
        IdentificationTypeDto identificationTypeDto = new IdentificationTypeDto(
            15, "CC", 0);

        mockMvc.perform(
            put("/v1/admissions/identification-types/{id}", id).
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(identificationTypeDto))
        ).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("IdentificationTypeRestControllerTest - UpdateIdentificationType - " +
        "Must generate exception when ID doesn't exist")
    public void testUpdateIdentificationTypeIdDoesNotExist() throws Exception {
        Integer id = 5;
        IdentificationTypeDto identificationTypeDto = new IdentificationTypeDto(
            5, "CC", 0);

        mockMvc.perform(
            put("/v1/admissions/identification-types/{id}", id).
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(identificationTypeDto))
        ).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("IdentificationTypeRestControllerTest - UpdateIdentificationType - " +
        "Must generate exception when Versions doesn't match")
    public void testUpdateIdentificationTypeVersionsDoesNotMatch() throws Exception {
        Integer id = 3;

        IdentificationTypeDto identificationTypeDto = new IdentificationTypeDto(
            3, "RD", 3);

        mockMvc.perform(
            put("/v1/admissions/identification-types/{id}", id).
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(identificationTypeDto))
        ).andExpect(status().isConflict());
    }

    @Test
    @DisplayName("IdentificationTypeRestControllerTest - UpdateIdentificationType - " +
        "Must generate exception when Name is null")
    public void testUpdateIdentificationTypeNullName() throws Exception {
        Integer id = 3;

        IdentificationTypeDto identificationTypeDto = new IdentificationTypeDto(
            3, null, 3);

        mockMvc.perform(
            put("/v1/admissions/identification-types/{id}", id).
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(identificationTypeDto))
        ).andExpect(status().isConflict());
    }

    @Test
    @DisplayName("IdentificationTypeRestControllerTest - UpdateIdentificationType - " +
        "Must generate exception when Name is empty")
    public void testUpdateIdentificationTypeEmptyName() throws Exception {
        Integer id = 3;

        IdentificationTypeDto identificationTypeDto = new IdentificationTypeDto(
            3, "   ", 3);

        mockMvc.perform(
            put("/v1/admissions/identification-types/{id}", id).
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(identificationTypeDto))
        ).andExpect(status().isConflict());
    }

    @Test
    @DisplayName("IdentificationTypeRestControllerTest - UpdateIdentificationType - " +
        "Must generate exception when Name already exists")
    public void testUpdateIdentificationTypeNameExists() throws Exception{
        Integer id = 3;

        IdentificationTypeDto identificationTypeDto = new IdentificationTypeDto(
            3, "CC", 3);

        mockMvc.perform(
            put("/v1/admissions/identification-types/{id}", id).
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(identificationTypeDto))
        ).andExpect(status().isConflict());
    }

    @Test
    @DisplayName("IdentificationTypeRestControllerTest - UpdateIdentificationType - " +
        "Must update and return new persisted info")
    public void testUpdateIdentificationType() throws Exception {
        Integer id = 2;

        IdentificationTypeDto identificationTypeDto = new IdentificationTypeDto(
            2, "TT", 1);

        mockMvc.perform(
            put("/v1/admissions/identification-types/{id}", id).
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(identificationTypeDto))
        ).andExpect(status().isOk()).
            andExpect(jsonPath("$.id", is(2))).
            andExpect(jsonPath("$.name", is("TT"))).
            andExpect(jsonPath("$.version", is(1)));
    }

    @Test
    @DisplayName("IdentificationTypeRestControllerTest - DeleteIdentificationType - " +
        "Must generate exception when ID doesn't exist")
    public void testDeleteIdentificationTypeIdDoesNotExist() throws Exception{
        mockMvc.perform(
            delete("/v1/admissions/identification-types/{id}", 9)
        ).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("IdentificationTypeRestControllerTest - DeleteIdentificationType - " +
        "Must delete the identification type")
    public void testDeleteIdentificationType() throws Exception {
        mockMvc.perform(
            delete("/v1/admissions/identification-types/{id}", 2)
        ).andExpect(status().isOk());

        mockMvc.perform(
            get("/v1/admissions/identification-types/{id}", 2)
        ).andExpect(status().isNotFound());
    }

    @AfterEach
    public void clearDatabase() {
        jdbcTemplate.execute(deleteAllIdentificationTypes);
    }
}
