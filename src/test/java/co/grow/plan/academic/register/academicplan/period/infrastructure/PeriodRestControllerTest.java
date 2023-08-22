package co.grow.plan.academic.register.academicplan.period.infrastructure;

import co.grow.plan.academic.register.AcademicRegisterServicesApplication;
import co.grow.plan.academic.register.academicplan.period.application.PeriodDto;
import co.grow.plan.academic.register.academicplan.period.application.PeriodNewDto;
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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestPropertySource("/application-test.properties")
@SpringBootTest(classes = AcademicRegisterServicesApplication.class)
@AutoConfigureMockMvc
public class PeriodRestControllerTest {

    // Inserts
    private static final String insertPeriod2023_3 =
        "insert into period (id, name, active, version) values(1, '2023/III', true, 0)";
    private static final String insertPeriod2021_4 =
        "insert into period (id, name, active, version) values(2, '2021/IV', false, 1)";
    private static final String insertPeriod2023_2 =
        "insert into period (id, name, active, version) values(3, '2023/II', false, 0)";

    //Deletes
    private static final String deleteAllPeriods =
        "delete from period";

    // AutoIncrement restarter
    private static final String restartAutoincrement =
        "ALTER TABLE period ALTER COLUMN id RESTART WITH 1";

    // Test Utilities
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setupDatabase() {
        jdbcTemplate.execute(restartAutoincrement);
        jdbcTemplate.execute(insertPeriod2023_3);
        jdbcTemplate.execute(insertPeriod2021_4);
        jdbcTemplate.execute(insertPeriod2023_2);
    }

    @Test
    @DisplayName("PeriodRestControllerTest - List - Must return a list of many objects")
    public void testListPeriodsManyObjects() throws Exception {

        mockMvc.perform(get("/v1/academic-plans/periods")).
            andExpect(status().isOk()).
            andExpect(content().contentType(MediaType.APPLICATION_JSON)).
            andExpect(jsonPath("$", hasSize(3))).

            andExpect(jsonPath("$[0].id", is(1))).
            andExpect(jsonPath("$[0].name", is("2023/III"))).
            andExpect(jsonPath("$[0].active", is(true))).
            andExpect(jsonPath("$[0].version", is(0))).

            andExpect(jsonPath("$[1].id", is(2))).
            andExpect(jsonPath("$[1].name", is("2021/IV"))).
            andExpect(jsonPath("$[1].active", is(false))).
            andExpect(jsonPath("$[1].version", is(1))).

            andExpect(jsonPath("$[2].id", is(3))).
            andExpect(jsonPath("$[2].name", is("2023/II"))).
            andExpect(jsonPath("$[2].active", is(false))).
            andExpect(jsonPath("$[2].version", is(0)));
    }

    @Test
    @DisplayName("PeriodRestControllerTest - List - Must return an empty list")
    public void testListPeriodsEmptyList() throws Exception {

        jdbcTemplate.execute(deleteAllPeriods);


        mockMvc.perform(get("/v1/academic-plans/periods")).
            andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("PeriodRestControllerTest - Create - Must generate exception when " +
        "Period parameter is null")
    public void testCreatePeriodBadRequest1() throws Exception {

        mockMvc.perform(
            post("/v1/academic-plans/periods").
                contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PeriodRestControllerTest - Create - Must generate exception when " +
        "name is null")
    public void testCreatePeriodBadRequest2() throws Exception {

        PeriodNewDto periodNewDto =
            new PeriodNewDto(null, false);

        mockMvc.perform(
            post("/v1/academic-plans/periods").
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(periodNewDto))
        ).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PeriodRestControllerTest - Create - Must generate exception when " +
        "isActive is null")
    public void testCreatePeriodBadRequest3() throws Exception {

        PeriodNewDto periodNewDto =
            new PeriodNewDto("2023/III", null);

        mockMvc.perform(
            post("/v1/academic-plans/periods").
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(periodNewDto))
        ).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PeriodRestControllerTest - Create - Must generate exception when " +
        "breaking name constraint")
    public void testCreatePeriodBreakingConstrain1() throws Exception {

        PeriodNewDto periodNewDto =
            new PeriodNewDto("2023/III", true);

        mockMvc.perform(
            post("/v1/academic-plans/periods").
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(periodNewDto))
        ).andExpect(status().isConflict());
    }

    @Test
    @DisplayName("PeriodRestControllerTest - Create - Must create new record and " +
        "return created information")
    public void testCreatePeriod() throws Exception{

        PeriodNewDto periodNewDto =
            new PeriodNewDto("2024/II", false);

        mockMvc.perform(
            post("/v1/academic-plans/periods").
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(periodNewDto))
        ).andExpect(status().isCreated()).
            andExpect(jsonPath("$.id", is(4))).
            andExpect(jsonPath("$.name", is("2024/II"))).
            andExpect(jsonPath("$.active", is(false))).
            andExpect(jsonPath("$.version", is(0)));

    }

    @Test
    @DisplayName("PeriodRestControllerTest - FindById - Must generate exception " +
        "when ID doesn't exist")
    public void testFindPeriodByIdIdDoesNotExist() throws Exception {
        mockMvc.perform(get("/v1/academic-plans/periods/{id}", 8)).
            andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PeriodRestControllerTest - FindById - Must return dto " +
        "when ID does exist")
    public void testFindPeriodById() throws Exception {
        mockMvc.perform(get("/v1/academic-plans/periods/{id}", 2)).
            andExpect(status().isOk()).
            andExpect(jsonPath("$.id", is(2))).
            andExpect(jsonPath("$.name", is("2021/IV"))).
            andExpect(jsonPath("$.active", is(false))).
            andExpect(jsonPath("$.version", is(1)));
    }

    @Test
    @DisplayName("PeriodRestControllerTest - UpdatePeriod - " +
        "Must generate exception when Period object is null")
    public void testUpdatePeriodObjectNull() throws Exception {
        mockMvc.perform(
                put("/v1/academic-plans/periods/{id}", 3).
                    contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PeriodRestControllerTest - UpdatePeriod - " +
        "Must generate exception when IDs doesn't match")
    public void testUpdatePeriodIdsDoesNotMatch() throws Exception {
        Integer id = 5;
        PeriodDto periodDto = new PeriodDto(
            15, "2023/III", true, 0);

        mockMvc.perform(
            put("/v1/academic-plans/periods/{id}", id).
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(periodDto))
        ).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PeriodRestControllerTest - UpdatePeriod - " +
        "Must generate exception when ID doesn't exist")
    public void testUpdatePeriodIdDoesNotExist() throws Exception {
        Integer id = 5;
        PeriodDto periodDto = new PeriodDto(
            5, "2023/III", true, 0);

        mockMvc.perform(
            put("/v1/academic-plans/periods/{id}", id).
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(periodDto))
        ).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PeriodRestControllerTest - UpdatePeriod - " +
        "Must generate exception when Versions doesn't match")
    public void testUpdatePeriodVersionsDoesNotMatch() throws Exception {
        Integer id = 3;

        PeriodDto periodDto = new PeriodDto(
            3, "2024/II", false , 3);

        mockMvc.perform(
            put("/v1/academic-plans/periods/{id}", id).
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(periodDto))
        ).andExpect(status().isConflict());
    }

    @Test
    @DisplayName("PeriodRestControllerTest - UpdatePeriod - " +
        "Must generate exception when Name is null")
    public void testUpdatePeriodNullName() throws Exception {
        Integer id = 3;

        PeriodDto periodDto = new PeriodDto(
            3, null, false , 3);

        mockMvc.perform(
            put("/v1/academic-plans/periods/{id}", id).
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(periodDto))
        ).andExpect(status().isConflict());
    }

    @Test
    @DisplayName("PeriodRestControllerTest - UpdatePeriod - " +
        "Must generate exception when isActive is null")
    public void testUpdatePeriodNullIsActive() throws Exception {
        Integer id = 3;

        PeriodDto periodDto = new PeriodDto(
            3, "2024/II", null , 3);

        mockMvc.perform(
            put("/v1/academic-plans/periods/{id}", id).
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(periodDto))
        ).andExpect(status().isConflict());
    }

    @Test
    @DisplayName("PeriodRestControllerTest - UpdatePeriod - " +
        "Must generate exception when Name is empty")
    public void testUpdatePeriodEmptyName() throws Exception {
        Integer id = 3;

        PeriodDto periodDto = new PeriodDto(
            3, "   ", false, 3);

        mockMvc.perform(
            put("/v1/academic-plans/periods/{id}", id).
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(periodDto))
        ).andExpect(status().isConflict());
    }

    @Test
    @DisplayName("PeriodRestControllerTest - UpdatePeriod - " +
        "Must generate exception when Name already exists")
    public void testUpdatePeriodNameExists() throws Exception{
        Integer id = 3;

        PeriodDto periodDto = new PeriodDto(
            3, "2023/III", false, 3);

        mockMvc.perform(
            put("/v1/academic-plans/periods/{id}", id).
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(periodDto))
        ).andExpect(status().isConflict());
    }

    @Test
    @DisplayName("PeriodRestControllerTest - UpdatePeriod - " +
        "Must update and return new persisted info")
    public void testUpdatePeriod() throws Exception {
        Integer id = 2;

        PeriodDto periodDto = new PeriodDto(
            2, "2021/VI", true , 1);

        mockMvc.perform(
            put("/v1/academic-plans/periods/{id}", id).
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(periodDto))
        ).andExpect(status().isOk()).
            andExpect(jsonPath("$.id", is(2))).
            andExpect(jsonPath("$.name", is("2021/VI"))).
            andExpect(jsonPath("$.active", is(true))).
            andExpect(jsonPath("$.version", is(2)));
    }

    @Test
    @DisplayName("PeriodRestControllerTest - DeletePeriod - " +
        "Must generate exception when ID doesn't exist")
    public void testDeletePeriodIdDoesNotExist() throws Exception{
        mockMvc.perform(
            delete("/v1/academic-plans/periods/{id}", 9)
        ).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PeriodRestControllerTest - DeletePeriod - " +
        "Must delete the information")
    public void testDeletePeriod() throws Exception {
        mockMvc.perform(
            delete("/v1/academic-plans/periods/{id}", 2)
        ).andExpect(status().isOk());

        mockMvc.perform(
            get("/v1/academic-plans/periods/{id}", 2)
        ).andExpect(status().isNotFound());
    }

    @AfterEach
    public void clearDatabase() {
        jdbcTemplate.execute(deleteAllPeriods);
    }
}
