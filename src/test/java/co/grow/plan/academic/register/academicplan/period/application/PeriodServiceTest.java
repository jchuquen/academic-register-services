package co.grow.plan.academic.register.academicplan.period.application;

import co.grow.plan.academic.register.academicplan.period.domain.Period;
import co.grow.plan.academic.register.academicplan.period.domain.IPeriodDao;
import co.grow.plan.academic.register.shared.exceptions.ApiBadInformationException;
import co.grow.plan.academic.register.shared.exceptions.ApiConflictException;
import co.grow.plan.academic.register.shared.exceptions.ApiMissingInformationException;
import co.grow.plan.academic.register.shared.exceptions.ApiNoEntityException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestPropertySource("/application-test.properties")
@ExtendWith(MockitoExtension.class)
public class PeriodServiceTest {

    @Mock
    private IPeriodDao periodDao;

    @Mock
    private IPeriodMapper periodMapper;

    @InjectMocks
    private PeriodService periodService;

    @Test
    @DisplayName("PeriodServiceTest - List - Must return a list of many objects")
    public void testListPeriodsManyObjects() {

        List<Period> periodList =
            List.of(
                new Period(1, "2023/III", true, 0),
                new Period(2, "2021/IV", false, 1),
                new Period(3, "2023/II", false, 0)
            );

        when(periodDao.findAll()).thenReturn(periodList);

        List<PeriodDto> expectedList =
            List.of(
                new PeriodDto(1, "2023/III", true, 0),
                new PeriodDto(2, "2021/IV", false, 1),
                new PeriodDto(3, "2023/II", false, 0)
            );

        when(
            periodMapper.entityListToIdentifiableAndVersionableDtoList(
                any(Iterable.class)
            )
        ).thenReturn(expectedList);

        List<PeriodDto> currentList =
            periodService.list();

        assertEquals(expectedList.size(), currentList.size());

        PeriodDto expected;
        PeriodDto current;

        for (int i = 0; i < expectedList.size(); i++) {
            expected = expectedList.get(i);
            current = currentList.get(i);

            assertEntityWithDto(expected, current);
        }

        verify(periodDao, times(1)).findAll();
    }

    @Test
    @DisplayName("PeriodServiceTest - List - Must return an empty list")
    public void testListPeriodsEmptyList() {

        List<Period> periodList =
            List.of();

        when(periodDao.findAll()).thenReturn(periodList);

        List<PeriodDto> expectedList =
            List.of();

        List<PeriodDto> currentList =
            periodService.list();

        assertIterableEquals(expectedList, currentList);
        verify(periodDao, times(1)).findAll();
    }

    @Test
    @DisplayName("PeriodServiceTest - Create - Must generate exception when " +
        "Period parameter is null")
    public void testCreatePeriodBadRequest1() {
        assertThrows(
            ApiBadInformationException.class,
            () -> periodService.create(null)
        );
    }

    @Test
    @DisplayName("PeriodServiceTest - Create - Must generate exception when " +
        "name is null")
    public void testCreatePeriodBadRequest2() {
        assertThrows(
            ApiMissingInformationException.class,
            () -> periodService.create(
                new PeriodNewDto(null, false)
            )
        );
    }

    @Test
    @DisplayName("PeriodServiceTest - Create - Must generate exception when " +
        "isActive is null")
    public void testCreatePeriodBadRequest3() {
        assertThrows(
            ApiMissingInformationException.class,
            () -> periodService.create(
                new PeriodNewDto("2023/IV", null)
            )
        );
    }

    @Test
    @DisplayName("PeriodServiceTest - Create - Must generate exception when " +
        "breaking name constraint")
    public void testCreatePeriodBreakingConstrain1() {

        PeriodNewDto periodNewDto =
            new PeriodNewDto("2023/III", true);

        Period periodConflict =
            new Period(99, "2023/III", true, 15);

        when(
            periodDao.getByName(periodNewDto.getName())
        ).thenReturn(Optional.of(periodConflict));

        assertThrows(
            ApiConflictException.class,
            () -> periodService.create(
                periodNewDto
            )
        );

        verify(periodDao, times(1)).
            getByName(periodNewDto.getName());
    }

    @Test
    @DisplayName("PeriodServiceTest - Create - Must create new record and " +
        "return created information")
    public void testCreatePeriod() {

        PeriodNewDto periodNewDto =
            new PeriodNewDto("2023/III", true);

        Period periodNoPersisted =
            new Period(periodNewDto.getName(), periodNewDto.getActive());

        when(
            periodMapper.noIdentifiableAndVersionableDtoToEntity(
                periodNewDto
            )
        ).thenReturn(periodNoPersisted);

        Period period = new Period(
          15, "2023/III", true, 0);

        when(
            periodDao.save(any(Period.class))
        ).thenReturn(period);

        PeriodDto expected =
            new PeriodDto(15, "2023/III", true, 0);

        when(
            periodMapper.entityToIdentifiableAndVersionableDto(any(Period.class))
        ).thenReturn(expected);

        PeriodDto current =
            periodService.create(periodNewDto);

        assertEntityWithDto(expected, current);

        verify(periodDao, times(1)).
            save(any(Period.class));
    }

    @Test
    @DisplayName("PeriodServiceTest - FindById - Must generate exception " +
        "when ID is null")
    public void testFindPeriodByIdNullId() {
        assertThrows(
            ApiBadInformationException.class,
            () -> periodService.findById(null)
        );
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

        Period period = new Period(
            15, "2023/III", true, 0);

        when(
            periodDao.findById(15)
        ).thenReturn(Optional.of(period));

        PeriodDto expected = new PeriodDto(
            15, "2023/III", true, 0);

        when(
            periodMapper.entityToIdentifiableAndVersionableDto(
                any(Period.class)
            )
        ).thenReturn(expected);

        PeriodDto periodDto =
            periodService.findById(15);

        assertEntityWithDto(expected, periodDto);

        verify(periodDao, times(1)).
            findById(15);
    }

    @Test
    @DisplayName("PeriodServiceTest - UpdatePeriod - " +
        "Must generate exception when ID is null")
    public void testUpdatePeriodIdNull() {
        assertThrows(
            ApiBadInformationException.class,
            () -> periodService.update(
                null, null)
        );
    }

    @Test
    @DisplayName("PeriodServiceTest - UpdatePeriod - " +
        "Must generate exception when Period object is null")
    public void testUpdatePeriodObjectNull() {
        assertThrows(
            ApiBadInformationException.class,
            () -> periodService.update(
                5, null)
        );
    }

    @Test
    @DisplayName("PeriodServiceTest - UpdatePeriod - " +
        "Must generate exception when IDs doesn't match")
    public void testUpdatePeriodIdsDoesNotMatch() {
        Integer id = 5;
        PeriodDto periodDto = new PeriodDto(
            15, "2023/III", true, 0);

        assertThrows(
            ApiBadInformationException.class,
            () -> periodService.update(
                id, periodDto)
        );
    }

    @Test
    @DisplayName("PeriodServiceTest - UpdatePeriod - " +
        "Must generate exception when ID doesn't exist")
    public void testUpdatePeriodIdDoesNotExist() {
        Integer id = 5;
        PeriodDto periodDto = new PeriodDto(
            5, "2023/III", true, 0);

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
        Integer id = 5;

        PeriodDto periodDto = new PeriodDto(
            5, "2023/III", true, 1);

        Period period = new Period(
            5, "2023/III", true, 3);

        when(
            periodDao.findById(5)
        ).thenReturn(Optional.of(period));

        assertThrows(
            ApiConflictException.class,
            () -> periodService.update(
                id, periodDto)
        );

        verify(periodDao, times(1)).
            findById(5);
    }

    @Test
    @DisplayName("PeriodServiceTest - UpdatePeriod - " +
        "Must generate exception when Name is null")
    public void testUpdatePeriodNullName() {
        Integer id = 5;

        PeriodDto periodDto = new PeriodDto(5, null, true, 1);

        Period period = new Period(
            5, "2023/III", true, 1);

        when(
            periodDao.findById(5)
        ).thenReturn(Optional.of(period));

        assertThrows(
            ApiMissingInformationException.class,
            () -> periodService.update(
                id, periodDto)
        );

        verify(periodDao, times(1)).
            findById(5);
    }

    @Test
    @DisplayName("PeriodServiceTest - UpdatePeriod - " +
        "Must generate exception when Name is empty")
    public void testUpdatePeriodEmptyName() {
        Integer id = 5;

        PeriodDto periodDto = new PeriodDto(5, " ", true, 1);

        Period period = new Period(
            5, "2023/III", true, 1);

        when(
            periodDao.findById(5)
        ).thenReturn(Optional.of(period));

        assertThrows(
            ApiMissingInformationException.class,
            () -> periodService.update(
                id, periodDto)
        );

        verify(periodDao, times(1)).
            findById(5);
    }

    @Test
    @DisplayName("PeriodServiceTest - UpdatePeriod - " +
        "Must generate exception when Name already exists")
    public void testUpdatePeriodNameExists() {
        Integer id = 5;

        PeriodDto periodDto = new PeriodDto(
            5, "2021/IV", false, 1);

        Period period = new Period(
            5, "2023/III", true, 1);

        Period periodExists = new Period(
            15, "2021/IV", false, 12);

        when(
            periodDao.findById(5)
        ).thenReturn(Optional.of(period));

        when(
            periodDao.getByName(periodDto.getName())
        ).thenReturn(Optional.of(periodExists));

        assertThrows(
            ApiConflictException.class,
            () -> periodService.update(
                id, periodDto)
        );

        verify(periodDao, times(1)).
            findById(5);

        verify(periodDao, times(1)).
            getByName(periodDto.getName());
    }

    @Test
    @DisplayName("PeriodServiceTest - UpdatePeriod - " +
        "Must update and return new persisted info")
    public void testUpdatePeriod() {
        Integer id = 5;

        PeriodDto periodDto = new PeriodDto(
            5, "2021/IV", false, 1);

        Period period = new Period(
            5, "2023/III", true, 1);

        when(
            periodDao.findById(5)
        ).thenReturn(Optional.of(period));

        PeriodNewDto periodNewDto =
            new PeriodNewDto(periodDto.getName(), periodDto.getActive());

        when(
            periodMapper.identifiableAndVersionableDtoToNoIdentifiableAndVersionableDto(
                periodDto
            )
        ).thenReturn(periodNewDto);

        Period periodUpdatedNoPersisted = new Period(
            5, "2021/IV", false, 1);

        when(
            periodMapper.updateEntityFromNoIdentifiableAndVersionableDto(
                any(Period.class),
                any(PeriodNewDto.class)
            )
        ).thenReturn(periodUpdatedNoPersisted);

        PeriodDto expected = new PeriodDto(
            5, "2021/IV", false, 2);

        when(
            periodMapper.entityToIdentifiableAndVersionableDto(any(Period.class))
        ).thenReturn(expected);

        Period newPeriod = new Period(
            5, "2021/IV", false, 2);

        when(
            periodDao.save(any(Period.class))
        ).thenReturn(newPeriod);

        PeriodDto current =
            periodService.update(
                id, periodDto);

        assertEntityWithDto(expected, current);

        verify(periodDao, times(1)).
            findById(5);

        verify(periodDao, times(1)).
            save(any(Period.class));
    }

    @Test
    @DisplayName("PeriodServiceTest - DeletePeriod - " +
        "Must generate exception when ID is null")
    public void testDeletePeriodIdNull() {
        assertThrows(
            ApiBadInformationException.class,
            () -> periodService.delete(null)
        );
    }

    @Test
    @DisplayName("PeriodServiceTest - DeletePeriod - " +
        "Must generate exception when ID doesn't exist")
    public void testDeletePeriodIdDoesNotExist() {
        assertThrows(
            ApiNoEntityException.class,
            () -> periodService.delete(7)
        );
    }

    @Test
    @DisplayName("PeriodServiceTest - DeletePeriod - " +
        "Must delete the information")
    public void testDeletePeriod() {
        Integer id = 7;

        Period period = new Period(
            7, "2023/III", true, 1);

        when(
            periodDao.findById(id)
        ).thenReturn(Optional.of(period));

        periodService.delete(id);

        verify(periodDao, times(1)).
            findById(id);

        assertTimeoutPreemptively(Duration.ofSeconds(3),
            () -> periodService.delete(id),
            "Should execute in less than 3 seconds");
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
