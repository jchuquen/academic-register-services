package co.grow.plan.academic.register.controllers;

import co.grow.plan.academic.register.admissions.controllers.IdentificationTypeRestController;
import co.grow.plan.academic.register.admissions.models.IdentificationType;
import co.grow.plan.academic.register.admissions.services.IdentificationTypeService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource("/application-test.properties")
@SpringBootTest
@AutoConfigureMockMvc
public class IdentificationTypeRestControllerTest {

    @Mock
    private IdentificationTypeService identificacionTypeServiceMock;

    @InjectMocks
    private IdentificationTypeRestController identificationTypeRestController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateIdentificacionTypeHttpRequestMockedDao() throws Exception {
        IdentificationType identificationType = new IdentificationType("Cédula");
        identificationType.setId(1);

        //when(identificacionTypeServiceMock.createIdentificationType("Cédula")).thenReturn(identificationType);

        mockMvc.perform(
                post("/api/rest/identificationType").
                        queryParam("name", "Cédula")
        ).andExpect(status().isOk());
    }

}
