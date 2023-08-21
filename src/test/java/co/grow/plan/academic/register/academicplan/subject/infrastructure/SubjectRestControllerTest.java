package co.grow.plan.academic.register.academicplan.subject.infrastructure;

import co.grow.plan.academic.register.AcademicRegisterServicesApplication;
import co.grow.plan.academic.register.academicplan.subject.application.SubjectDto;
import co.grow.plan.academic.register.academicplan.subject.application.SubjectNewDto;
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
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) // Fix problem with autoincrement but has performance issues
public class SubjectRestControllerTest {

    // Inserts
    private static final String insertSubjectMaths =
        "insert into subject (id, name, version) values(1, 'Maths', 0)";
    private static final String insertSubjectPhysics =
        "insert into subject (id, name, version) values(2, 'Physics', 1)";
    private static final String insertSubjectSocialStudies =
        "insert into subject (id, name, version) values(3, 'Social Studies', 0)";

    //Deletes
    private static final String deleteAllSubjects =
        "delete from subject";

    // AutoIncrement restarter
    private static final String restartAutoincrement =
        "ALTER TABLE subject ALTER COLUMN id RESTART WITH 1";

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
        jdbcTemplate.execute(insertSubjectMaths);
        jdbcTemplate.execute(insertSubjectPhysics);
        jdbcTemplate.execute(insertSubjectSocialStudies);
    }

    @Test
    @DisplayName("SubjectRestControllerTest - List - Must return a list of many objects")
    public void testListSubjectsManyObjects() throws Exception {

        mockMvc.perform(get("/v1/academic-plans/subjects")).
            andExpect(status().isOk()).
            andExpect(content().contentType(MediaType.APPLICATION_JSON)).
            andExpect(jsonPath("$", hasSize(3))).

            andExpect(jsonPath("$[0].id", is(1))).
            andExpect(jsonPath("$[0].name", is("Maths"))).
            andExpect(jsonPath("$[0].version", is(0))).

            andExpect(jsonPath("$[1].id", is(2))).
            andExpect(jsonPath("$[1].name", is("Physics"))).
            andExpect(jsonPath("$[1].version", is(1))).

            andExpect(jsonPath("$[2].id", is(3))).
            andExpect(jsonPath("$[2].name", is("Social Studies"))).
            andExpect(jsonPath("$[2].version", is(0)));
    }

    @Test
    @DisplayName("SubjectRestControllerTest - List - Must return an empty list")
    public void testListSubjectsEmptyList() throws Exception {

        jdbcTemplate.execute(deleteAllSubjects);


        mockMvc.perform(get("/v1/academic-plans/subjects")).
            andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("SubjectRestControllerTest - Create - Must generate exception when " +
        "Object parameter is null")
    public void testCreateSubjectBadRequest1() throws Exception {

        mockMvc.perform(
            post("/v1/academic-plans/subjects").
                contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("SubjectRestControllerTest - Create - Must generate exception when " +
        "name is null")
    public void testCreateSubjectBadRequest2() throws Exception {

        SubjectNewDto subjectNewDto = new SubjectNewDto(null);

        mockMvc.perform(
            post("/v1/academic-plans/subjects").
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(subjectNewDto))
        ).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("SubjectRestControllerTest - Create - Must generate exception when " +
        "breaking name constraint")
    public void testCreateSubjectBreakingConstrain1() throws Exception {

        SubjectNewDto subjectNewDto =
            new SubjectNewDto("Maths");

        mockMvc.perform(
            post("/v1/academic-plans/subjects").
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(subjectNewDto))
        ).andExpect(status().isConflict());
    }

    @Test
    @DisplayName("SubjectRestControllerTest - Create - Must create new record and " +
        "return created information")
    public void testCreateSubject() throws Exception{

        SubjectNewDto subjectNewDto =
            new SubjectNewDto("Painting");

        mockMvc.perform(
                post("/v1/academic-plans/subjects").
                    contentType(MediaType.APPLICATION_JSON).
                    content(objectMapper.writeValueAsString(subjectNewDto))
            ).andExpect(status().isCreated()).
            andExpect(jsonPath("$.id", is(4))).
            andExpect(jsonPath("$.name", is("Painting"))).
            andExpect(jsonPath("$.version", is(0)));
    }

    @Test
    @DisplayName("SubjectRestControllerTest - FindById - Must generate exception " +
        "when ID doesn't exist")
    public void testFindSubjectByIdIdDoesNotExist() throws Exception {
        mockMvc.perform(get("/v1/academic-plans/subjects/{id}", 8)).
            andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("SubjectRestControllerTest - FindById - Must return dto " +
        "when ID does exist")
    public void testFindSubjectById() throws Exception {
        mockMvc.perform(get("/v1/academic-plans/subjects/{id}", 2)).
            andExpect(status().isOk()).
            andExpect(jsonPath("$.id", is(2))).
            andExpect(jsonPath("$.name", is("Physics"))).
            andExpect(jsonPath("$.version", is(1)));
    }

    @Test
    @DisplayName("SubjectRestControllerTest - UpdateSubject - " +
        "Must generate exception when Subject object is null")
    public void testUpdateSubjectObjectNull() throws Exception {
        mockMvc.perform(
            put("/v1/academic-plans/subjects/{id}", 3).
                contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("SubjectRestControllerTest - UpdateSubject - " +
        "Must generate exception when IDs doesn't match")
    public void testUpdateSubjectIdsDoesNotMatch() throws Exception {
        Integer id = 5;
        SubjectDto subjectDto = new SubjectDto(
            15, "Maths", 0);

        mockMvc.perform(
            put("/v1/academic-plans/subjects/{id}", id).
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(subjectDto))
        ).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("SubjectRestControllerTest - UpdateSubject - " +
        "Must generate exception when ID doesn't exist")
    public void testUpdateSubjectIdDoesNotExist() throws Exception {
        Integer id = 5;
        SubjectDto subjectDto = new SubjectDto(
            5, "Maths", 0);

        mockMvc.perform(
            put("/v1/academic-plans/subjects/{id}", id).
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(subjectDto))
        ).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("SubjectRestControllerTest - UpdateSubject - " +
        "Must generate exception when Versions doesn't match")
    public void testUpdateSubjectVersionsDoesNotMatch() throws Exception {
        Integer id = 3;

        SubjectDto subjectDto = new SubjectDto(
            3, "RD", 3);

        mockMvc.perform(
            put("/v1/academic-plans/subjects/{id}", id).
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(subjectDto))
        ).andExpect(status().isConflict());
    }

    @Test
    @DisplayName("SubjectRestControllerTest - UpdateSubject - " +
        "Must generate exception when Name is null")
    public void testUpdateSubjectNullName() throws Exception {
        Integer id = 3;

        SubjectDto subjectDto = new SubjectDto(
            3, null, 3);

        mockMvc.perform(
            put("/v1/academic-plans/subjects/{id}", id).
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(subjectDto))
        ).andExpect(status().isConflict());
    }

    @Test
    @DisplayName("SubjectRestControllerTest - UpdateSubject - " +
        "Must generate exception when Name is empty")
    public void testUpdateSubjectEmptyName() throws Exception {
        Integer id = 3;

        SubjectDto subjectDto = new SubjectDto(
            3, "   ", 3);

        mockMvc.perform(
            put("/v1/academic-plans/subjects/{id}", id).
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(subjectDto))
        ).andExpect(status().isConflict());
    }

    @Test
    @DisplayName("SubjectRestControllerTest - UpdateSubject - " +
        "Must generate exception when Name already exists")
    public void testUpdateSubjectNameExists() throws Exception{
        Integer id = 3;

        SubjectDto subjectDto = new SubjectDto(
            3, "Maths", 3);

        mockMvc.perform(
            put("/v1/academic-plans/subjects/{id}", id).
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(subjectDto))
        ).andExpect(status().isConflict());
    }

    @Test
    @DisplayName("SubjectRestControllerTest - UpdateSubject - " +
        "Must update and return new persisted info")
    public void testUpdateSubject() throws Exception {
        Integer id = 2;

        SubjectDto subjectDto = new SubjectDto(
            2, "TT", 1);

        mockMvc.perform(
                put("/v1/academic-plans/subjects/{id}", id).
                    contentType(MediaType.APPLICATION_JSON).
                    content(objectMapper.writeValueAsString(subjectDto))
            ).andExpect(status().isOk()).
            andExpect(jsonPath("$.id", is(2))).
            andExpect(jsonPath("$.name", is("TT"))).
            andExpect(jsonPath("$.version", is(2)));
    }

    @Test
    @DisplayName("SubjectRestControllerTest - DeleteSubject - " +
        "Must generate exception when ID doesn't exist")
    public void testDeleteSubjectIdDoesNotExist() throws Exception{
        mockMvc.perform(
            delete("/v1/academic-plans/subjects/{id}", 9)
        ).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("SubjectRestControllerTest - DeleteSubject - " +
        "Must delete the information found")
    public void testDeleteSubject() throws Exception {
        mockMvc.perform(
            delete("/v1/academic-plans/subjects/{id}", 2)
        ).andExpect(status().isOk());

        mockMvc.perform(
            get("/v1/academic-plans/subjects/{id}", 2)
        ).andExpect(status().isNotFound());
    }

    @AfterEach
    public void clearDatabase() {
        jdbcTemplate.execute(deleteAllSubjects);
    }
}
