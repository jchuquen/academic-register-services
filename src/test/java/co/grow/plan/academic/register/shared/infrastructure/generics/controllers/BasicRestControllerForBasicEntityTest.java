package co.grow.plan.academic.register.shared.infrastructure.generics.controllers;

import co.grow.plan.academic.register.shared.application.generics.services.PropertyError;
import co.grow.plan.academic.register.shared.domain.interfaces.BasicEntity;
import co.grow.plan.academic.register.shared.infrastructure.generics.CreationalDto;
import co.grow.plan.academic.register.shared.infrastructure.generics.FullEntityDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public abstract class BasicRestControllerForBasicEntityTest<
        F extends FullEntityDto & BasicEntity,
        C extends CreationalDto
    > extends BasicRestControllerTest<F, C>{

    public BasicRestControllerForBasicEntityTest(
        JdbcTemplate jdbcTemplate,
        MockMvc mockMvc,
        ObjectMapper objectMapper
    ){
        super(jdbcTemplate, mockMvc, objectMapper);
    }

    @Test
    public void shouldReturnAListOfEntitiesAtList() throws Exception {

        List<F> listOfEntities = getListOfEntities();

        ResultActions resultActions = this.getMockMvc().perform(get(getBaseResourceURL())).
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
    public void shouldGenerateBadRequestWhenNameIsNullAtCreate() throws Exception {
        C creationalDto = getCreationalDto(PropertyError.NULL_NAME);

        this.getMockMvc().perform(
            post(getBaseResourceURL()).
                contentType(MediaType.APPLICATION_JSON).
                content(this.getObjectMapper().writeValueAsString(creationalDto))
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void shouldGenerateConflictExceptionWhenNameExistsAtCreate() throws Exception {
        C creationalDto = getCreationalDto(PropertyError.DUPLICATED_NAME);

        this.getMockMvc().perform(
            post(getBaseResourceURL()).
                contentType(MediaType.APPLICATION_JSON).
                content(this.getObjectMapper().writeValueAsString(creationalDto))
        ).andExpect(status().isConflict());
    }

    @Test
    public void shouldCreateNewEntityAndReturnPersistedInformation() throws Exception{
        C creationalDto = getCreationalDto(PropertyError.NONE);

        ResultActions resultActions = this.getMockMvc().perform(
            post(getBaseResourceURL()).
                contentType(MediaType.APPLICATION_JSON).
                content(this.getObjectMapper().writeValueAsString(creationalDto))
        ).andExpect(status().isCreated());

        F expected = getCreatedEntity();

        resultActions.andExpect(jsonPath("$.id", is(expected.id()))).
            andExpect(jsonPath("$.name", is(expected.name()))).
            andExpect(jsonPath("$.version", is(expected.version().intValue())));
    }

    @Test
    public void shouldReturnEntityWhenIdDoesExist() throws Exception {
        ResultActions resultActions = this.getMockMvc().perform(
            get(getIdentifiedResourceURL(), getExistingResourceId())).
            andExpect(status().isOk());

        F expected = getPersistedEntity();

        resultActions.andExpect(jsonPath("$.id", is(expected.id()))).
            andExpect(jsonPath("$.name", is(expected.name()))).
            andExpect(jsonPath("$.version", is(expected.version().intValue())));
    }


    @Test
    public void shouldGenerateExceptionWhenNameNullAtUpdating() throws Exception {
        F dtoWithUpdatedInfo = getFullDtoToUpdate(PropertyError.NULL_NAME);

        this.getMockMvc().perform(
            put(getIdentifiedResourceURL(), getExistingResourceId()).
                contentType(MediaType.APPLICATION_JSON).
                content(this.getObjectMapper().writeValueAsString(dtoWithUpdatedInfo))
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void shouldGenerateExceptionWhenNameIsEmptyAtUpdating() throws Exception {
        F dtoWithUpdatedInfo = getFullDtoToUpdate(PropertyError.EMPTY_NAME);

        this.getMockMvc().perform(
            put(getIdentifiedResourceURL(), getExistingResourceId()).
                contentType(MediaType.APPLICATION_JSON).
                content(this.getObjectMapper().writeValueAsString(dtoWithUpdatedInfo))
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void shouldGenerateExceptionWhenNameExistsAtUpdating() throws Exception{
        F dtoWithUpdatedInfo = getFullDtoToUpdate(PropertyError.DUPLICATED_NAME);

        this.getMockMvc().perform(
            put(getIdentifiedResourceURL(), getExistingResourceId()).
                contentType(MediaType.APPLICATION_JSON).
                content(this.getObjectMapper().writeValueAsString(dtoWithUpdatedInfo))
        ).andExpect(status().isConflict());
    }

    @Test
    public void shouldUpdateEntityAndReturnPersistedInformation() throws Exception {
        F dtoWithUpdatedInfo = getFullDtoToUpdate(PropertyError.NONE);

        ResultActions resultActions = this.getMockMvc().perform(
            put(getIdentifiedResourceURL(), getExistingResourceId()).
                contentType(MediaType.APPLICATION_JSON).
                content(this.getObjectMapper().writeValueAsString(dtoWithUpdatedInfo))
        ).andExpect(status().isOk());

        F expected = getUpdatedEntity();

        resultActions.andExpect(jsonPath("$.id", is(expected.id()))).
            andExpect(jsonPath("$.name", is(expected.name()))).
            andExpect(jsonPath("$.version", is(expected.version().intValue())));
    }
}
