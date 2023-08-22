package co.grow.plan.academic.register.academicplan.period.application;


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
public class PeriodServiceDBTest {

    // Inserts
    private static final String insertPeriod2023_3 =
        "insert into period (id, name, active, version) values(1, '2023/III', true, 0)";
    private static final String insertPeriod2021_4 =
        "insert into period (id, name, active, version) values(2, '2021/IV', false, 1)";
    private static final String insertPeriod2023_2 =
        "insert into period (id, name, active, version) values(3, '2023/II', false, 0)";

    //Deletes
    private static final String deleteAllPeriods =
        "delete from period";

    // AutoIncrement restarter
    private static final String restartAutoincrement =
        "ALTER TABLE period ALTER COLUMN id RESTART WITH 1";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private IPeriodService periodService;

    @BeforeEach
    public void setupDatabase() {
        jdbcTemplate.execute(restartAutoincrement);
        jdbcTemplate.execute(insertPeriod2023_3);
        jdbcTemplate.execute(insertPeriod2021_4);
        jdbcTemplate.execute(insertPeriod2023_2);
    }

    @Test
    @DisplayName("PeriodServiceTest - List - Must return a list of many objects")
    public void testListPeriodsManyObjects() {

        List<PeriodDto> expectedList =
            List.of(
                new PeriodDto(1, "2023/III", true, 0),
                new PeriodDto(2, "2021/IV", false, 1),
                new PeriodDto(3, "2023/II", false, 0)
            );

        List<PeriodDto> currentList =
            periodService.list();

        assertEquals(expectedList.size(), currentList.size());
        for (int i = 0; i < expectedList.size(); i++) {
            PeriodDto expected = expectedList.get(i);
            PeriodDto current = currentList.get(i);

            assertEntityWithDto(expected, current);
        }
    }

    @Test
    @DisplayName("PeriodServiceTest - List - Must return an empty list")
    public void testListPeriodsEmptyList() {

        jdbcTemplate.execute(deleteAllPeriods);

        List<PeriodDto> expectedList =
            List.of();

        List<PeriodDto> currentList =
            periodService.list();

        assertIterableEquals(expectedList, currentList);
    }

    @Test
    @DisplayName("PeriodServiceTest - Create - Must generate exception when " +
        "breaking name constrain")
    public void testCreatePeriodBreakingConstrain1() {

        PeriodNewDto periodNewDto =
            new PeriodNewDto("2023/III", true);

        assertThrows(
            ApiConflictException.class,
            () -> periodService.create(
                periodNewDto
            )
        );
    }

    @Test
    @DisplayName("PeriodServiceTest - Create - Must create new record and " +
        "return created information")
    public void testCreatePeriod() {

        PeriodNewDto periodNewDto =
            new PeriodNewDto("2024/I", false);

        PeriodDto expected =
            new PeriodDto(4, "2024/I", false , 0);

        PeriodDto current =
            periodService.create(periodNewDto);

        assertEntityWithDto(expected, current);
    }

    @Test
    @DisplayName("PeriodServiceTest - FindById - Must generate exception " +
        "when ID doesn't exist")
    public void testFindPeriodByIdIdDoesNotExist() {
        assertThrows(
            ApiNoEntityException.class,
            () -> periodService.findById(9)
        );
    }

    @Test
    @DisplayName("PeriodServiceTest - FindById - Must return dto " +
        "when ID does exist")
    public void testFindPeriodById() {

        PeriodDto expected = new PeriodDto(3, "2023/II", false , 0);

        PeriodDto periodDto =
            periodService.findById(expected.getId());

        assertEntityWithDto(expected, periodDto);
    }

    @Test
    @DisplayName("PeriodServiceTest - UpdatePeriod - " +
        "Must generate exception when ID doesn't exist")
    public void testUpdatePeriodIdDoesNotExist() {
        Integer id = 5;
        PeriodDto periodDto = new PeriodDto(5, "2024/I", false , 0);

        assertThrows(
            ApiNoEntityException.class,
            () -> periodService.update(
                id, periodDto)
        );
    }

    @Test
    @DisplayName("PeriodServiceTest - UpdatePeriod - " +
        "Must generate exception when Versions doesn't match")
    public void testUpdatePeriodVersionsDoesNotMatch() {
        Integer id = 3;

        PeriodDto periodDto = new PeriodDto(3, "2023/II", false , 3);

        assertThrows(
            ApiConflictException.class,
            () -> periodService.update(
                id, periodDto)
        );
    }

    @Test
    @DisplayName("PeriodServiceTest - UpdatePeriod - " +
        "Must generate exception when Name is null")
    public void testUpdatePeriodNullName() {
        Integer id = 3;

        PeriodDto periodDto = new PeriodDto(
            3, null, false, 0);

        assertThrows(
            ApiMissingInformationException.class,
            () -> periodService.update(
                id, periodDto)
        );
    }

    @Test
    @DisplayName("PeriodServiceTest - UpdatePeriod - " +
        "Must generate exception when isActive is null")
    public void testUpdatePeriodNullIsActive() {
        Integer id = 3;

        PeriodDto periodDto = new PeriodDto(
            3, null, false, 0);

        assertThrows(
            ApiMissingInformationException.class,
            () -> periodService.update(
                id, periodDto)
        );
    }

    @Test
    @DisplayName("PeriodServiceTest - UpdatePeriod - " +
        "Must generate exception when Name is empty")
    public void testUpdatePeriodEmptyName() {
        Integer id = 3;

        PeriodDto periodDto = new PeriodDto(
            3, " ", false , 0);

        assertThrows(
            ApiMissingInformationException.class,
            () -> periodService.update(
                id, periodDto)
        );
    }

    @Test
    @DisplayName("PeriodServiceTest - UpdatePeriod - " +
        "Must generate exception when Name already exists")
    public void testUpdatePeriodNameExists() {
        Integer id = 3;

        PeriodDto periodDto = new PeriodDto(
            3, "2023/III", false, 0);

        assertThrows(
            ApiConflictException.class,
            () -> periodService.update(
                id, periodDto)
        );
    }

    @Test
    @DisplayName("PeriodServiceTest - UpdatePeriod - " +
        "Must update and return new persisted info")
    public void testUpdatePeriod() {
        Integer id = 3;

        PeriodDto periodDto = new PeriodDto(
            3, "2024/II", false , 0);

        PeriodDto current =
            periodService.update(
                id, periodDto);

        PeriodDto expected = new PeriodDto(
            3, "2024/II", false, 1);

        assertEntityWithDto(expected, current);
    }

    @Test
    @DisplayName("PeriodServiceTest - DeletePeriod - " +
        "Must delete the information")
    public void testDeletePeriod() {
        Integer id = 3;

        periodService.delete(id);

        assertThrows(
            ApiNoEntityException.class,
            () -> periodService.findById(3)
        );

    }

    @AfterEach
    public void clearDatabase() {
        jdbcTemplate.execute(deleteAllPeriods);
    }

    //Utils
    private static void assertEntityWithDto(
        PeriodDto expected,
        PeriodDto current) {

        assertEquals(expected.getId(), current.getId());
        assertEquals(expected.getName(), current.getName());
        assertEquals(expected.getActive(), current.getActive());
        assertEquals(expected.getVersion(), current.getVersion());
    }
}
