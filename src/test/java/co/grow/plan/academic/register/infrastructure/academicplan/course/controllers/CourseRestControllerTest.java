package co.grow.plan.academic.register.infrastructure.academicplan.course.controllers;

import co.grow.plan.academic.register.AcademicRegisterServicesApplication;
import co.grow.plan.academic.register.infrastructure.academicplan.course.dtos.CourseCreationalDto;
import co.grow.plan.academic.register.infrastructure.academicplan.course.dtos.CourseFullDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
public class CourseRestControllerTest {
    // Inserts
    private static final String insertCourseSD =
        "insert into course (id, name, version) values(1, 'Software Development', 0)";
    private static final String insertCourseMicroprocessors =
        "insert into course (id, name, version) values(2, 'Microprocessors', 1)";
    private static final String insertCourseSocialStudies =
        "insert into course (id, name, version) values(3, 'Pure Maths', 0)";

    //Deletes
    private static final String deleteAllCourses =
        "delete from course";

    // AutoIncrement restarter
    private static final String restartAutoincrement =
        "ALTER TABLE course ALTER COLUMN id RESTART WITH 4";

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
        jdbcTemplate.execute(insertCourseSD);
        jdbcTemplate.execute(insertCourseMicroprocessors);
        jdbcTemplate.execute(insertCourseSocialStudies);
    }

    @Test
    public void shouldReturnAListOfCourses() throws Exception {

        mockMvc.perform(get("/v1/academic-plans/courses")).
            andExpect(status().isOk()).
            andExpect(content().contentType(MediaType.APPLICATION_JSON)).
            andExpect(jsonPath("$", hasSize(3))).

            andExpect(jsonPath("$[0].id", is(1))).
            andExpect(jsonPath("$[0].name", is("Software Development"))).
            andExpect(jsonPath("$[0].version", is(0))).

            andExpect(jsonPath("$[1].id", is(2))).
            andExpect(jsonPath("$[1].name", is("Microprocessors"))).
            andExpect(jsonPath("$[1].version", is(1))).

            andExpect(jsonPath("$[2].id", is(3))).
            andExpect(jsonPath("$[2].name", is("Pure Maths"))).
            andExpect(jsonPath("$[2].version", is(0)));
    }

    @Test
    public void shouldReturnAnEmptyListOfCourses() throws Exception {

        jdbcTemplate.execute(deleteAllCourses);


        mockMvc.perform(get("/v1/academic-plans/courses")).
            andExpect(status().isNoContent());
    }

    @Test
    public void shouldGenerateBadRequestWhenCourseIsNull() throws Exception {

        mockMvc.perform(
            post("/v1/academic-plans/courses").
                contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void shouldGenerateBadRequestWhenNameIsNull() throws Exception {

        CourseCreationalDto courseNewDto =
            new CourseCreationalDto(null);

        mockMvc.perform(
            post("/v1/academic-plans/courses").
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(courseNewDto))
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void shouldGenerateConflictExceptionWhenNameExists() throws Exception {

        CourseCreationalDto courseNewDto =
            new CourseCreationalDto("Software Development");

        mockMvc.perform(
            post("/v1/academic-plans/courses").
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(courseNewDto))
        ).andExpect(status().isConflict());
    }

    @Test
    public void shouldCreateNewCourseAndReturnPersistedInformation() throws Exception{

        CourseCreationalDto courseNewDto =
            new CourseCreationalDto("Painting");

        mockMvc.perform(
                post("/v1/academic-plans/courses").
                    contentType(MediaType.APPLICATION_JSON).
                    content(objectMapper.writeValueAsString(courseNewDto))
            ).andExpect(status().isCreated()).
            andExpect(jsonPath("$.id", is(4))).
            andExpect(jsonPath("$.name", is("Painting"))).
            andExpect(jsonPath("$.version", is(0)));
    }

    @Test
    public void shouldGenerateExceptionWhenIdDoesNotExistAtGetById() throws Exception {
        mockMvc.perform(get("/v1/academic-plans/courses/{id}", 8)).
            andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnCourseWhenIdDoesExist() throws Exception {
        mockMvc.perform(get("/v1/academic-plans/courses/{id}", 2)).
            andExpect(status().isOk()).
            andExpect(jsonPath("$.id", is(2))).
            andExpect(jsonPath("$.name", is("Microprocessors"))).
            andExpect(jsonPath("$.version", is(1)));
    }

    @Test
    public void shouldGenerateExceptionWhenObjectNullAtUpdating() throws Exception {
        mockMvc.perform(
            put("/v1/academic-plans/courses/{id}", 3).
                contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void shouldGenerateExceptionWhenIdsDoesNotMatchAtUpdating() throws Exception {
        Integer id = 5;
        CourseFullDto courseDto = new CourseFullDto(
            4, "Software Development", 0L);

        mockMvc.perform(
            put("/v1/academic-plans/courses/{id}", id).
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(courseDto))
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void shouldGenerateExceptionWhenIdDoesNotExistAtUpdating() throws Exception {
        Integer id = 5;
        CourseFullDto courseDto = new CourseFullDto(
            5, "Software Development", 0L);

        mockMvc.perform(
            put("/v1/academic-plans/courses/{id}", id).
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(courseDto))
        ).andExpect(status().isNotFound());
    }

    @Test
    public void shouldGenerateExceptionWhenVersionsDoesNotMatchAtUpdating() throws Exception {
        Integer id = 3;

        CourseFullDto courseDto = new CourseFullDto(
            3, "RD", 3L);

        mockMvc.perform(
            put("/v1/academic-plans/courses/{id}", id).
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(courseDto))
        ).andExpect(status().isConflict());
    }

    @Test
    public void shouldGenerateExceptionWhenNameNullAtUpdating() throws Exception {
        Integer id = 3;

        CourseFullDto courseDto = new CourseFullDto(
            3, null, 3L);

        mockMvc.perform(
            put("/v1/academic-plans/courses/{id}", id).
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(courseDto))
        ).andExpect(status().isConflict());
    }

    @Test
    public void shouldGenerateExceptionWhenNameIsEmptyAtUpdating() throws Exception {
        Integer id = 3;

        CourseFullDto courseDto = new CourseFullDto(
            3, "   ", 3L);

        mockMvc.perform(
            put("/v1/academic-plans/courses/{id}", id).
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(courseDto))
        ).andExpect(status().isConflict());
    }

    @Test
    public void shouldGenerateExceptionWhenNameExistsAtUpdating() throws Exception{
        Integer id = 3;

        CourseFullDto courseDto = new CourseFullDto(
            3, "Software Development", 3L);

        mockMvc.perform(
            put("/v1/academic-plans/courses/{id}", id).
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(courseDto))
        ).andExpect(status().isConflict());
    }

    @Test
    public void shouldUpdateCourseAndReturnPersistedInformation() throws Exception {
        Integer id = 2;

        CourseFullDto courseDto = new CourseFullDto(
            2, "TT", 1L);

        mockMvc.perform(
                put("/v1/academic-plans/courses/{id}", id).
                    contentType(MediaType.APPLICATION_JSON).
                    content(objectMapper.writeValueAsString(courseDto))
            ).andExpect(status().isOk()).
            andExpect(jsonPath("$.id", is(2))).
            andExpect(jsonPath("$.name", is("TT"))).
            andExpect(jsonPath("$.version", is(2)));
    }

    @Test
    public void shouldGenerateExceptionWhenIdDoesNotExistAtDeleting() throws Exception{
        mockMvc.perform(
            delete("/v1/academic-plans/courses/{id}", 9)
        ).andExpect(status().isNotFound());
    }

    @Test
    public void shouldDeleteTheCourse() throws Exception {
        mockMvc.perform(
            delete("/v1/academic-plans/courses/{id}", 2)
        ).andExpect(status().isOk());

        mockMvc.perform(
            get("/v1/academic-plans/courses/{id}", 2)
        ).andExpect(status().isNotFound());
    }

    @AfterEach
    public void clearDatabase() {
        jdbcTemplate.execute(deleteAllCourses);
    }
}
