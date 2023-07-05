package co.grow.plan.academic.register.controllers;

import co.grow.plan.academic.register.models.IdentificationType;
import co.grow.plan.academic.register.services.IdentificacionTypeService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

//post()
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

//status()
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.Mockito.when;

@TestPropertySource("/application-test.properties")
@SpringBootTest
@AutoConfigureMockMvc
public class IdentificationTypeRestControllerTest {

    @Mock
    private IdentificacionTypeService identificacionTypeServiceMock;

    @InjectMocks
    private IdentificationTypeRestController identificationTypeRestController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateIdentificacionTypeHttpRequestMockedDao() throws Exception {
        IdentificationType identificationType = new IdentificationType("Cédula");
        identificationType.setId(1);

        when(identificacionTypeServiceMock.createIdentificationType("Cédula")).thenReturn(identificationType);

        mockMvc.perform(
                post("/api/rest/identificationType").
                        queryParam("name", "Cédula")
        ).andExpect(status().isOk());
    }

}
