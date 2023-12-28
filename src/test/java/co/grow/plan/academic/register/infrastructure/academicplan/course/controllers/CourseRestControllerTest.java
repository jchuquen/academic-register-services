package co.grow.plan.academic.register.infrastructure.academicplan.course.controllers;

import co.grow.plan.academic.register.AcademicRegisterServicesApplication;
import co.grow.plan.academic.register.infrastructure.academicplan.course.dtos.CourseCreationalDto;
import co.grow.plan.academic.register.infrastructure.academicplan.course.dtos.CourseFullDto;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    @DisplayName("CourseRestControllerTest - List - Must return a list of many objects")
    public void testListCoursesManyObjects() throws Exception {

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
    @DisplayName("CourseRestControllerTest - List - Must return an empty list")
    public void testListCoursesEmptyList() throws Exception {

        jdbcTemplate.execute(deleteAllCourses);


        mockMvc.perform(get("/v1/academic-plans/courses")).
            andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("CourseRestControllerTest - Create - Must generate exception when " +
        "Object parameter is null")
    public void testCreateCourseBadRequest1() throws Exception {

        mockMvc.perform(
            post("/v1/academic-plans/courses").
                contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("CourseRestControllerTest - Create - Must generate exception when " +
        "name is null")
    public void testCreateCourseBadRequest2() throws Exception {

        CourseCreationalDto courseNewDto =
            new CourseCreationalDto(null);

        mockMvc.perform(
            post("/v1/academic-plans/courses").
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(courseNewDto))
        ).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("CourseRestControllerTest - Create - Must generate exception when " +
        "breaking name constraint")
    public void testCreateCourseBreakingConstrain1() throws Exception {

        CourseCreationalDto courseNewDto =
            new CourseCreationalDto("Software Development");

        mockMvc.perform(
            post("/v1/academic-plans/courses").
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(courseNewDto))
        ).andExpect(status().isConflict());
    }

    @Test
    @DisplayName("CourseRestControllerTest - Create - Must create new record and " +
        "return created information")
    public void testCreateCourse() throws Exception{

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
    @DisplayName("CourseRestControllerTest - FindById - Must generate exception " +
        "when ID doesn't exist")
    public void testFindCourseByIdIdDoesNotExist() throws Exception {
        mockMvc.perform(get("/v1/academic-plans/courses/{id}", 8)).
            andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("CourseRestControllerTest - FindById - Must return dto " +
        "when ID does exist")
    public void testFindCourseById() throws Exception {
        mockMvc.perform(get("/v1/academic-plans/courses/{id}", 2)).
            andExpect(status().isOk()).
            andExpect(jsonPath("$.id", is(2))).
            andExpect(jsonPath("$.name", is("Microprocessors"))).
            andExpect(jsonPath("$.version", is(1)));
    }

    @Test
    @DisplayName("CourseRestControllerTest - UpdateCourse - " +
        "Must generate exception when Course object is null")
    public void testUpdateCourseObjectNull() throws Exception {
        mockMvc.perform(
            put("/v1/academic-plans/courses/{id}", 3).
                contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("CourseRestControllerTest - UpdateCourse - " +
        "Must generate exception when IDs doesn't match")
    public void testUpdateCourseIdsDoesNotMatch() throws Exception {
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
    @DisplayName("CourseRestControllerTest - UpdateCourse - " +
        "Must generate exception when ID doesn't exist")
    public void testUpdateCourseIdDoesNotExist() throws Exception {
        Integer id = 5;
        CourseFullDto courseDto = new CourseFullDto(
            4, "Software Development", 0L);

        mockMvc.perform(
            put("/v1/academic-plans/courses/{id}", id).
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(courseDto))
        ).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("CourseRestControllerTest - UpdateCourse - " +
        "Must generate exception when Versions doesn't match")
    public void testUpdateCourseVersionsDoesNotMatch() throws Exception {
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
    @DisplayName("CourseRestControllerTest - UpdateCourse - " +
        "Must generate exception when Name is null")
    public void testUpdateCourseNullName() throws Exception {
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
    @DisplayName("CourseRestControllerTest - UpdateCourse - " +
        "Must generate exception when Name is empty")
    public void testUpdateCourseEmptyName() throws Exception {
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
    @DisplayName("CourseRestControllerTest - UpdateCourse - " +
        "Must generate exception when Name already exists")
    public void testUpdateCourseNameExists() throws Exception{
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
    @DisplayName("CourseRestControllerTest - UpdateCourse - " +
        "Must update and return new persisted info")
    public void testUpdateCourse() throws Exception {
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
    @DisplayName("CourseRestControllerTest - DeleteCourse - " +
        "Must generate exception when ID doesn't exist")
    public void testDeleteCourseIdDoesNotExist() throws Exception{
        mockMvc.perform(
            delete("/v1/academic-plans/courses/{id}", 9)
        ).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("CourseRestControllerTest - DeleteCourse - " +
        "Must delete the information found")
    public void testDeleteCourse() throws Exception {
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
