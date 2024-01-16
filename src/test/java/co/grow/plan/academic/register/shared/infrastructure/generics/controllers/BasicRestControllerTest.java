package co.grow.plan.academic.register.shared.infrastructure.generics.controllers;

import co.grow.plan.academic.register.shared.application.generics.services.PropertyError;
import co.grow.plan.academic.register.shared.domain.interfaces.IBasicEntity;
import co.grow.plan.academic.register.shared.infrastructure.generics.ICreationalDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RequiredArgsConstructor
public abstract class BasicRestControllerTest<
        F extends IBasicEntity,
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
    public void shouldReturnAListOfEntitiesAtList() throws Exception {

        List<F> listOfEntities = getListOfEntities();

        ResultActions resultActions = mockMvc.perform(get(getBaseResourceURL())).
            andExpect(status().isOk()).
            andExpect(content().contentType(MediaType.APPLICATION_JSON)).
            andExpect(jsonPath("$", hasSize(listOfEntities.size())));

        for (int i = 0; i < listOfEntities.size(); i++) {
            resultActions.andExpect(jsonPath(String.format("$[%s].id", i), is(listOfEntities.get(i).id()))).
                andExpect(jsonPath(String.format("$[%s].name", i), is(listOfEntities.get(i).name()))).
                andExpect(jsonPath(String.format("$[%s].version", i), is(listOfEntities.get(i).version().intValue())));
        }
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
    public void shouldGenerateBadRequestWhenNameIsNullAtCreate() throws Exception {
        C creationalDto = getCreationalDto(PropertyError.NULL_NAME);

        mockMvc.perform(
            post(getBaseResourceURL()).
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(creationalDto))
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void shouldGenerateConflictExceptionWhenNameExistsAtCreate() throws Exception {
        C creationalDto = getCreationalDto(PropertyError.DUPLICATED_NAME);

        mockMvc.perform(
            post(getBaseResourceURL()).
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(creationalDto))
        ).andExpect(status().isConflict());
    }

    @Test
    public void shouldCreateNewEntityAndReturnPersistedInformation() throws Exception{
        C creationalDto = getCreationalDto(PropertyError.NONE);

        ResultActions resultActions = mockMvc.perform(
            post(getBaseResourceURL()).
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(creationalDto))
        ).andExpect(status().isCreated());

        F expected = getCreatedEntity();

        resultActions.andExpect(jsonPath("$.id", is(expected.id()))).
            andExpect(jsonPath("$.name", is(expected.name()))).
            andExpect(jsonPath("$.version", is(expected.version().intValue())));
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
    public void shouldReturnEntityWhenIdDoesExist() throws Exception {
        ResultActions resultActions = mockMvc.perform(
            get(getIdentifiedResourceURL(), getExistingResourceId())).
            andExpect(status().isOk());

        F expected = getPersistedEntity();

        resultActions.andExpect(jsonPath("$.id", is(expected.id()))).
            andExpect(jsonPath("$.name", is(expected.name()))).
            andExpect(jsonPath("$.version", is(expected.version().intValue())));
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
    public void shouldGenerateExceptionWhenNameNullAtUpdating() throws Exception {
        F dtoWithUpdatedInfo = getFullDtoToUpdate(PropertyError.NULL_NAME);

        mockMvc.perform(
            put(getIdentifiedResourceURL(), getExistingResourceId()).
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(dtoWithUpdatedInfo))
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void shouldGenerateExceptionWhenNameIsEmptyAtUpdating() throws Exception {
        F dtoWithUpdatedInfo = getFullDtoToUpdate(PropertyError.EMPTY_NAME);

        mockMvc.perform(
            put(getIdentifiedResourceURL(), getExistingResourceId()).
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(dtoWithUpdatedInfo))
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void shouldGenerateExceptionWhenNameExistsAtUpdating() throws Exception{
        F dtoWithUpdatedInfo = getFullDtoToUpdate(PropertyError.DUPLICATED_NAME);

        mockMvc.perform(
            put(getIdentifiedResourceURL(), getExistingResourceId()).
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(dtoWithUpdatedInfo))
        ).andExpect(status().isConflict());
    }

    @Test
    public void shouldUpdateEntityAndReturnPersistedInformation() throws Exception {
        F dtoWithUpdatedInfo = getFullDtoToUpdate(PropertyError.NONE);

        ResultActions resultActions = mockMvc.perform(
            put(getIdentifiedResourceURL(), getExistingResourceId()).
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(dtoWithUpdatedInfo))
        ).andExpect(status().isOk());

        F expected = getUpdatedEntity();

        resultActions.andExpect(jsonPath("$.id", is(expected.id()))).
            andExpect(jsonPath("$.name", is(expected.name()))).
            andExpect(jsonPath("$.version", is(expected.version().intValue())));
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


    //
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
