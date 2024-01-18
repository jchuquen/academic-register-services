package co.grow.plan.academic.register.shared.infrastructure.generics.controllers;

import co.grow.plan.academic.register.shared.application.generics.services.PropertyError;
import co.grow.plan.academic.register.shared.infrastructure.generics.ICreationalDto;
import co.grow.plan.academic.register.shared.infrastructure.generics.IFullEntityDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RequiredArgsConstructor
@Getter
public abstract class BasicRestControllerTest<
        F extends IFullEntityDto,
        C extends ICreationalDto
    > {

    private final JdbcTemplate jdbcTemplate;
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @BeforeEach
    public void setupDatabase() {
        jdbcTemplate.execute(getRestartAutoincrementSentence());
        jdbcTemplate.execute(getFirstExampleInsertSentence());
        jdbcTemplate.execute(getSecondExampleInsertSentence());
        jdbcTemplate.execute(getThirdExampleInsertSentence());
    }

    @Test
    public void shouldReturnAnEmptyListOfEntitiesAtList() throws Exception {
        jdbcTemplate.execute(getDeleteAllSentence());

        mockMvc.perform(get(getBaseResourceURL())).
            andExpect(status().isNoContent());
    }

    @Test
    public void shouldGenerateBadRequestWhenDtoIsNullAtCreate() throws Exception {
        mockMvc.perform(
            post(getBaseResourceURL()).
                contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void shouldGenerateExceptionWhenIdDoesNotExistAtGetById() throws Exception {
        mockMvc.perform(
            get(
                getIdentifiedResourceURL(),
                getWrongResourceId()
            )
        ).andExpect(status().isNotFound());
    }

    @Test
        public void shouldGenerateExceptionWhenObjectNullAtUpdating() throws Exception {
        mockMvc.perform(
            put(getIdentifiedResourceURL(), getExistingResourceId()).
                contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void shouldGenerateExceptionWhenIdsDoesNotMatchAtUpdating() throws Exception {
        F dtoWithUpdatedInfo = getFullDtoToUpdate(PropertyError.WRONG_ID);

        mockMvc.perform(
            put(getIdentifiedResourceURL(), getExistingResourceId()).
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(dtoWithUpdatedInfo))
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void shouldGenerateExceptionWhenIdDoesNotExistAtUpdating() throws Exception {
        F dtoWithUpdatedInfo = getFullDtoToUpdate(PropertyError.WRONG_ID);

        mockMvc.perform(
            put(getIdentifiedResourceURL(), getWrongResourceId()).
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(dtoWithUpdatedInfo))
        ).andExpect(status().isNotFound());
    }

    @Test
    public void shouldGenerateExceptionWhenVersionsDoesNotMatchAtUpdating() throws Exception {
        F dtoWithUpdatedInfo = getFullDtoToUpdate(PropertyError.WRONG_VERSION);

        mockMvc.perform(
            put(getIdentifiedResourceURL(), getExistingResourceId()).
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(dtoWithUpdatedInfo))
        ).andExpect(status().isConflict());
    }

    @Test
    public void shouldGenerateExceptionWhenIdDoesNotExistAtDeleting() throws Exception{
        mockMvc.perform(
            delete(getIdentifiedResourceURL(), getWrongResourceId())
        ).andExpect(status().isNotFound());
    }

    @Test
    public void shouldDeleteTheEntity() throws Exception {
        mockMvc.perform(
            delete(getIdentifiedResourceURL(), getExistingResourceId())
        ).andExpect(status().isOk());

        mockMvc.perform(
            get(getIdentifiedResourceURL(), getExistingResourceId())
        ).andExpect(status().isNotFound());
    }

    @AfterEach
    public void clearDatabase() {
        jdbcTemplate.execute(getDeleteAllSentence());
    }

    // SQL sentences
    protected abstract String getRestartAutoincrementSentence();
    protected abstract String getFirstExampleInsertSentence();
    protected abstract String getSecondExampleInsertSentence();
    protected abstract String getThirdExampleInsertSentence();
    protected abstract String getDeleteAllSentence();

    // Templates
    protected abstract String getBaseResourceURL();
    protected abstract List<F> getListOfEntities();
    protected abstract C getCreationalDto(PropertyError propertyError);
    protected abstract F getCreatedEntity();
    protected abstract String getIdentifiedResourceURL();
    protected abstract int getWrongResourceId();
    protected abstract int getExistingResourceId();
    protected abstract F getPersistedEntity();
    protected abstract F getFullDtoToUpdate(PropertyError propertyError);
    protected abstract F getUpdatedEntity();

}
