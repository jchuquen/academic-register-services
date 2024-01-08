package co.grow.plan.academic.register.application.admissions.identificationtype.services;

import co.grow.plan.academic.register.AcademicRegisterServicesApplication;
import co.grow.plan.academic.register.application.admissions.identificationtype.ports.api.IIdentificationTypeServiceAPI;
import co.grow.plan.academic.register.domain.admissions.identificationtype.model.IdentificationType;
import co.grow.plan.academic.register.shared.application.exceptions.ApiConflictException;
import co.grow.plan.academic.register.shared.application.exceptions.ApiMissingInformationException;
import co.grow.plan.academic.register.shared.application.exceptions.ApiNoEntityException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource("/application-test.properties")
@SpringBootTest(classes = AcademicRegisterServicesApplication.class)
public class IdentificationTypeServiceIntegrationTest {

    // Inserts
    private static final String insertIdentificationTypeCC =
        "insert into identification_type (id, name, version) values(1, 'CC', 0)";
    private static final String insertIdentificationTypeTI =
        "insert into identification_type (id, name, version) values(2, 'TI', 1)";
    private static final String insertIdentificationTypeRC =
        "insert into identification_type (id, name, version) values(3, 'RC', 0)";

    //Deletes
    private static final String deleteAllIdentificationTypes =
        "delete from identification_type";

    // AutoIncrement restarter
    private static final String restartAutoincrement =
        "ALTER TABLE identification_type ALTER COLUMN id RESTART WITH 4";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private IIdentificationTypeServiceAPI identificationTypeService;

    @BeforeEach
    public void setupDatabase() {
        jdbcTemplate.execute(restartAutoincrement);
        jdbcTemplate.execute(insertIdentificationTypeCC);
        jdbcTemplate.execute(insertIdentificationTypeTI);
        jdbcTemplate.execute(insertIdentificationTypeRC);
    }

    @Test
    public void shouldReturnAListOfIdentificationTypes() {

        List<IdentificationType> expectedList =
            List.of(
                new IdentificationType(1, "CC", 0L),
                new IdentificationType(2, "TI", 1L),
                new IdentificationType(3, "RC", 0L)
            );

        List<IdentificationType> currentList =
            identificationTypeService.list();

        assertEquals(expectedList.size(), currentList.size());
        for (int i = 0; i < expectedList.size(); i++) {
            IdentificationType expected = expectedList.get(i);
            IdentificationType current = currentList.get(i);

            assertObjectProperties(expected, current);
        }
    }

    @Test
    public void shouldReturnAnEmptyListOfIdentificationTypes() {

        jdbcTemplate.execute(deleteAllIdentificationTypes);

        List<IdentificationType> expectedList =
            List.of();

        List<IdentificationType> currentList =
            identificationTypeService.list();

        assertIterableEquals(expectedList, currentList);
    }

    @Test
    public void shouldGenerateExceptionWhenNameExists() {

        IdentificationType identificationType =
            new IdentificationType(null, "CC", null);

        assertThrows(
            ApiConflictException.class,
            () -> identificationTypeService.create(identificationType)
        );
    }

    @Test
    public void shouldCreateNewIdentificationTypeAndReturnPersistedInformation() {

        IdentificationType identificationType =
            new IdentificationType(null, "CE", null);

        IdentificationType expected =
            new IdentificationType(4, "CE", 0L);

        IdentificationType current = identificationTypeService.create(identificationType);

        assertObjectProperties(expected, current);
    }

    @Test
    public void shouldGenerateExceptionWhenIdDoesNotExistAtFindById() {
        assertThrows(
            ApiNoEntityException.class,
            () -> identificationTypeService.findById(9)
        );
    }

    @Test
    public void shouldReturnIdentificationTypeWhenIdDoesExist() {

        IdentificationType expected = new IdentificationType(
            3, "RC", 0L);

        IdentificationType identificationTypeFound =
            identificationTypeService.findById(expected.id());

        assertObjectProperties(expected, identificationTypeFound);
    }

    @Test
    public void shouldGenerateExceptionWhenIdDoesNotExistAtUpdating() {
        Integer id = 5;
        IdentificationType identificationType = new IdentificationType(
            5, "CE", 0L);

        assertThrows(
            ApiNoEntityException.class,
            () -> identificationTypeService.update(
                id, identificationType)
        );
    }

    @Test
    public void shouldGenerateExceptionWhenVersionsDoesNotMatchAtUpdating() {
        Integer id = 3;

        IdentificationType identificationType = new IdentificationType(
            3, "RC", 3L);

        assertThrows(
            ApiConflictException.class,
            () -> identificationTypeService.update(
                id, identificationType)
        );
    }

    @Test
    public void shouldGenerateExceptionWhenNameNullAtUpdating() {
        Integer id = 3;

        IdentificationType identificationTypeDto = new IdentificationType(
            3, null, 0L);

        assertThrows(
            ApiMissingInformationException.class,
            () -> identificationTypeService.update(
                id, identificationTypeDto)
        );
    }

    @Test
    public void shouldGenerateExceptionWhenNameIsEmptyAtUpdating() {
        Integer id = 3;

        IdentificationType identificationType = new IdentificationType(
            3, " ", 0L);

        assertThrows(
            ApiMissingInformationException.class,
            () -> identificationTypeService.update(
                id, identificationType)
        );
    }

    @Test
    public void shouldGenerateExceptionWhenNameExistsAtUpdating() {
        Integer id = 3;

        IdentificationType identificationTypeDto = new IdentificationType(
            3, "CC", 0L);

        assertThrows(
            ApiConflictException.class,
            () -> identificationTypeService.update(
                id, identificationTypeDto)
        );
    }

    @Test
    public void shouldUpdateIdentificationTypeAndReturnPersistedInformation() {
        Integer id = 3;

        IdentificationType identificationType = new IdentificationType(
            3, "RR", 0L);

        IdentificationType current =
            identificationTypeService.update(
                id, identificationType);

        IdentificationType expected = new IdentificationType(
            3, "RR", 1L);

        assertObjectProperties(expected, current);
    }

    @Test
    public void shouldDeleteTheIdentificationType() {
        Integer id = 3;

        identificationTypeService.delete(id);

        assertThrows(
            ApiNoEntityException.class,
            () -> identificationTypeService.findById(3)
        );

    }

    @AfterEach
    public void clearDatabase() {
        jdbcTemplate.execute(deleteAllIdentificationTypes);
    }

    // Utilities
    private void assertObjectProperties(
        IdentificationType expected,IdentificationType current) {

        assertEquals(expected.id(), current.id());
        assertEquals(expected.name(), current.name());
        assertEquals(expected.version(), current.version());
    }
}

