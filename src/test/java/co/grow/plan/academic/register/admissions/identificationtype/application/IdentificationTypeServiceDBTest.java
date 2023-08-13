package co.grow.plan.academic.register.admissions.identificationtype.application;


import co.grow.plan.academic.register.AcademicRegisterServicesApplication;
import co.grow.plan.academic.register.shared.exceptions.ApiConflictException;
import co.grow.plan.academic.register.shared.exceptions.ApiMissingInformationException;
import co.grow.plan.academic.register.shared.exceptions.ApiNoEntityException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource("/application-test.properties")
@SpringBootTest(classes = AcademicRegisterServicesApplication.class)
public class IdentificationTypeServiceDBTest {

    // Inserts
    private final String insertIdentificationTypeCC =
        "insert into identification_type (id, name, version) values(1, 'CC', 0)";
    private final String insertIdentificationTypeTI =
        "insert into identification_type (id, name, version) values(2, 'TI', 1)";
    private final String insertIdentificationTypeRC =
        "insert into identification_type (id, name, version) values(3, 'RC', 0)";

    //Deletes
    private final String deleteAllIdentificationTypes =
        "delete from identification_type";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private IIdentificationTypeService identificationTypeService;

    @BeforeEach
    public void setupDatabase() {
        jdbcTemplate.execute(insertIdentificationTypeCC);
        jdbcTemplate.execute(insertIdentificationTypeTI);
        jdbcTemplate.execute(insertIdentificationTypeRC);
    }

    @Test
    @DisplayName("IdentificationTypeServiceTest - List - Must return a list of many objects")
    public void testListIdentificationTypesManyObjects() {

        List<IdentificationTypeDto> expectedList =
            List.of(
                new IdentificationTypeDto(1, "CC", 0),
                new IdentificationTypeDto(2, "TI", 1),
                new IdentificationTypeDto(3, "RC", 0)
            );

        List<IdentificationTypeDto> currentList =
            identificationTypeService.list();

        assertEquals(expectedList.size(), currentList.size());
        for (int i = 0; i < expectedList.size(); i++) {
            IdentificationTypeDto expected = expectedList.get(i);
            IdentificationTypeDto current = currentList.get(i);

            assertEntityWithDto(expected, current);
        }
    }

    @Test
    @DisplayName("IdentificationTypeServiceTest - List - Must return an empty list")
    public void testListIdentificationTypesEmptyList() {

        jdbcTemplate.execute(deleteAllIdentificationTypes);

        List<IdentificationTypeDto> expectedList =
            List.of();

        List<IdentificationTypeDto> currentList =
            identificationTypeService.list();

        assertIterableEquals(expectedList, currentList);
    }

    @Test
    @DisplayName("IdentificationTypeServiceTest - Create - Must generate exception when " +
        "breaking name constrain")
    public void testCreateIdentificationTypeBreakingConstrain1() {

        IdentificationTypeNewDto identificationTypeNewDto =
            new IdentificationTypeNewDto("CC");

        assertThrows(
            ApiConflictException.class,
            () -> identificationTypeService.create(
                identificationTypeNewDto
            )
        );
    }

    @Test
    @DisplayName("IdentificationTypeServiceTest - Create - Must create new record and " +
        "return created information")
    public void testCreateIdentificationType() {

        IdentificationTypeNewDto identificationTypeNewDto =
            new IdentificationTypeNewDto("CE");

        IdentificationTypeDto expected =
            new IdentificationTypeDto(4, "CE", 0);

        IdentificationTypeDto current =
            identificationTypeService.create(identificationTypeNewDto);

        assertEntityWithDto(expected, current);
    }

    @Test
    @DisplayName("IdentificationTypeServiceTest - FindById - Must generate exception " +
        "when ID doesn't exist")
    public void testFindIdentificationTypeByIdIdDoesNotExist() {
        assertThrows(
            ApiNoEntityException.class,
            () -> identificationTypeService.findById(9)
        );
    }

    @Test
    @DisplayName("IdentificationTypeServiceTest - FindById - Must return dto " +
        "when ID does exist")
    public void testFindIdentificationTypeById() {

        IdentificationTypeDto expected = new IdentificationTypeDto(
            3, "RC", 0);

        IdentificationTypeDto identificationTypeDto =
            identificationTypeService.findById(expected.getId());

        assertEntityWithDto(expected, identificationTypeDto);
    }

    @Test
    @DisplayName("IdentificationTypeServiceTest - UpdateIdentificationType - " +
        "Must generate exception when ID doesn't exist")
    public void testUpdateIdentificationTypeIdDoesNotExist() {
        Integer id = 5;
        IdentificationTypeDto identificationTypeDto = new IdentificationTypeDto(
            5, "CE", 0);

        assertThrows(
            ApiNoEntityException.class,
            () -> identificationTypeService.update(
                id, identificationTypeDto)
        );
    }

    @Test
    @DisplayName("IdentificationTypeServiceTest - UpdateIdentificationType - " +
        "Must generate exception when Versions doesn't match")
    public void testUpdateIdentificationTypeVersionsDoesNotMatch() {
        Integer id = 3;

        IdentificationTypeDto identificationTypeDto = new IdentificationTypeDto(
            3, "RC", 3);

        assertThrows(
            ApiConflictException.class,
            () -> identificationTypeService.update(
                id, identificationTypeDto)
        );
    }

    @Test
    @DisplayName("IdentificationTypeServiceTest - UpdateIdentificationType - " +
        "Must generate exception when Name is null")
    public void testUpdateIdentificationTypeNullName() {
        Integer id = 3;

        IdentificationTypeDto identificationTypeDto = new IdentificationTypeDto(
            3, null, 0);

        assertThrows(
            ApiMissingInformationException.class,
            () -> identificationTypeService.update(
                id, identificationTypeDto)
        );
    }

    @Test
    @DisplayName("IdentificationTypeServiceTest - UpdateIdentificationType - " +
        "Must generate exception when Name is empty")
    public void testUpdateIdentificationTypeEmptyName() {
        Integer id = 3;

        IdentificationTypeDto identificationTypeDto = new IdentificationTypeDto(
            3, " ", 0);

        assertThrows(
            ApiMissingInformationException.class,
            () -> identificationTypeService.update(
                id, identificationTypeDto)
        );
    }

    @Test
    @DisplayName("IdentificationTypeServiceTest - UpdateIdentificationType - " +
        "Must generate exception when Name already exists")
    public void testUpdateIdentificationTypeNameExists() {
        Integer id = 3;

        IdentificationTypeDto identificationTypeDto = new IdentificationTypeDto(
            3, "CC", 0);

        assertThrows(
            ApiConflictException.class,
            () -> identificationTypeService.update(
                id, identificationTypeDto)
        );
    }

    @Test
    @DisplayName("IdentificationTypeServiceTest - UpdateIdentificationType - " +
        "Must update and return new persisted info")
    public void testUpdateIdentificationType() {
        Integer id = 3;

        IdentificationTypeDto identificationTypeDto = new IdentificationTypeDto(
            3, "RR", 0);

        IdentificationTypeDto current =
            identificationTypeService.update(
                id, identificationTypeDto);

        IdentificationTypeDto expected = new IdentificationTypeDto(
            3, "RR", 1);

        assertEntityWithDto(expected, current);
    }

    @Test
    @DisplayName("IdentificationTypeServiceTest - DeleteIdentificationType - " +
        "Must delete the identification type")
    public void testDeleteIdentificationType() {
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

    //Utils
    private static void assertEntityWithDto(
        IdentificationTypeDto expected,
        IdentificationTypeDto current) {

        assertEquals(expected.getId(), current.getId());
        assertEquals(expected.getName(), current.getName());
        assertEquals(expected.getVersion(), current.getVersion());
    }
}
