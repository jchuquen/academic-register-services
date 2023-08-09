package co.grow.plan.academic.register.admissions.identificationtype.application;


import co.grow.plan.academic.register.AcademicRegisterServicesApplication;
import co.grow.plan.academic.register.admissions.identificationtype.domain.IdentificationType;
import co.grow.plan.academic.register.admissions.identificationtype.domain.IdentificationTypeDao;
import co.grow.plan.academic.register.shared.exceptions.ApiBadInformationException;
import co.grow.plan.academic.register.shared.exceptions.ApiConflictException;
import co.grow.plan.academic.register.shared.exceptions.ApiMissingInformationException;
import co.grow.plan.academic.register.shared.exceptions.ApiNoEntityException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestPropertySource("/application-test.properties")
@SpringBootTest(classes = AcademicRegisterServicesApplication.class)
public class IdentificationTypeServiceTest {

    @MockBean
    private IdentificationTypeDao identificationTypeDao;

    @Autowired
    private IIdentificationTypeService identificationTypeService;

    @Test
    @DisplayName("IdentificationTypeServiceTest - List - Must return a list of many objects")
    public void testListIdentificationTypesManyObjects() {

        List<IdentificationType> identificationTypeList =
            List.of(
                new IdentificationType(1, "CC", 0),
                new IdentificationType(2, "TI", 1),
                new IdentificationType(3, "RC", 0)
            );

        when(identificationTypeDao.findAll()).thenReturn(identificationTypeList);

        List<IdentificationTypeDto> expectedList =
            List.of(
                new IdentificationTypeDto(1, "CC", 0),
                new IdentificationTypeDto(2, "TI", 1),
                new IdentificationTypeDto(3, "RC", 0)
            );

        List<IdentificationTypeDto> currentList =
            identificationTypeService.listIdentificationTypes();

        assertEquals(expectedList.size(), currentList.size());
        for (int i = 0; i < expectedList.size(); i++) {
            IdentificationTypeDto expected = expectedList.get(i);
            IdentificationTypeDto current = currentList.get(i);

            assertEntityWithDto(expected, current);
        }

        verify(identificationTypeDao, times(1)).findAll();
    }

    @Test
    @DisplayName("IdentificationTypeServiceTest - List - Must return an empty list")
    public void testListIdentificationTypesEmptyList() {

        List<IdentificationType> identificationTypeList =
            List.of();

        when(identificationTypeDao.findAll()).thenReturn(identificationTypeList);

        List<IdentificationTypeDto> expectedList =
            List.of();

        List<IdentificationTypeDto> currentList =
            identificationTypeService.listIdentificationTypes();

        assertIterableEquals(expectedList, currentList);
        verify(identificationTypeDao, times(1)).findAll();
    }

    @Test
    @DisplayName("IdentificationTypeServiceTest - Create - Must generate exception when " +
        "IdentificationType parameter is null")
    public void testCreateIdentificationTypeBadRequest1() {
        assertThrows(
            ApiBadInformationException.class,
            () -> identificationTypeService.createIdentificationType(null)
        );
    }

    @Test
    @DisplayName("IdentificationTypeServiceTest - Create - Must generate exception when " +
        "name is null")
    public void testCreateIdentificationTypeBadRequest2() {
        assertThrows(
            ApiMissingInformationException.class,
            () -> identificationTypeService.createIdentificationType(
                new IdentificationTypeNewDto(null)
            )
        );
    }

    @Test
    @DisplayName("IdentificationTypeServiceTest - Create - Must generate exception when " +
        "breaking name constraint")
    public void testCreateIdentificationTypeBreakingConstrain1() {

        IdentificationTypeNewDto identificationTypeNewDto =
            new IdentificationTypeNewDto("CC");

        IdentificationType identificationTypeConflict =
            new IdentificationType(99, "CC", 15);

        when(
            identificationTypeDao.getByName(identificationTypeNewDto.getName())
        ).thenReturn(Optional.of(identificationTypeConflict));

        assertThrows(
            ApiConflictException.class,
            () -> identificationTypeService.createIdentificationType(
                identificationTypeNewDto
            )
        );

        verify(identificationTypeDao, times(1)).
            getByName(identificationTypeNewDto.getName());
    }

    @Test
    @DisplayName("IdentificationTypeServiceTest - Create - Must create new record and " +
        "return created information")
    public void testCreateIdentificationType() {

        IdentificationTypeNewDto identificationTypeNewDto =
            new IdentificationTypeNewDto("CC");

        IdentificationType identificationType = new IdentificationType(
          15, "CC", 0);

        IdentificationTypeDto expected =
            new IdentificationTypeDto(15, "CC", 0);

        when(
            identificationTypeDao.save(any(IdentificationType.class))
        ).thenReturn(identificationType);

        IdentificationTypeDto current =
            identificationTypeService.createIdentificationType(identificationTypeNewDto);

        assertEntityWithDto(expected, current);

        verify(identificationTypeDao, times(1)).
            save(any(IdentificationType.class));
    }

    @Test
    @DisplayName("IdentificationTypeServiceTest - FindById - Must generate exception " +
        "when ID is null")
    public void testFindIdentificationTypeByIdNullId() {
        assertThrows(
            ApiBadInformationException.class,
            () -> identificationTypeService.findIdentificationTypeById(null)
        );
    }

    @Test
    @DisplayName("IdentificationTypeServiceTest - FindById - Must generate exception " +
        "when ID doesn't exist")
    public void testFindIdentificationTypeByIdIdDoesNotExist() {
        assertThrows(
            ApiNoEntityException.class,
            () -> identificationTypeService.findIdentificationTypeById(9)
        );
    }

    @Test
    @DisplayName("IdentificationTypeServiceTest - FindById - Must return dto " +
        "when ID does exist")
    public void testFindIdentificationTypeById() {
        IdentificationType identificationType = new IdentificationType(
            15, "CC", 0);

        IdentificationTypeDto expected = new IdentificationTypeDto(
            15, "CC", 0);

        when(
            identificationTypeDao.findById(15)
        ).thenReturn(Optional.of(identificationType));

        IdentificationTypeDto identificationTypeDto =
            identificationTypeService.findIdentificationTypeById(15);

        assertEntityWithDto(expected, identificationTypeDto);

        verify(identificationTypeDao, times(1)).
            findById(15);
    }

    @Test
    @DisplayName("IdentificationTypeServiceTest - UpdateIdentificationType - " +
        "Must generate exception when ID is null")
    public void testUpdateIdentificationTypeIdNull() {
        assertThrows(
            ApiBadInformationException.class,
            () -> identificationTypeService.updateIdentificationType(
                null, null)
        );
    }

    @Test
    @DisplayName("IdentificationTypeServiceTest - UpdateIdentificationType - " +
        "Must generate exception when IdentificationType object is null")
    public void testUpdateIdentificationTypeObjectNull() {
        assertThrows(
            ApiBadInformationException.class,
            () -> identificationTypeService.updateIdentificationType(
                5, null)
        );
    }

    @Test
    @DisplayName("IdentificationTypeServiceTest - UpdateIdentificationType - " +
        "Must generate exception when IDs doesn't match")
    public void testUpdateIdentificationTypeIdsDoesNotMatch() {
        Integer id = 5;
        IdentificationTypeDto identificationTypeDto = new IdentificationTypeDto(
            15, "CC", 0);

        assertThrows(
            ApiBadInformationException.class,
            () -> identificationTypeService.updateIdentificationType(
                id, identificationTypeDto)
        );
    }

    @Test
    @DisplayName("IdentificationTypeServiceTest - UpdateIdentificationType - " +
        "Must generate exception when ID doesn't exist")
    public void testUpdateIdentificationTypeIdDoesNotExist() {
        Integer id = 5;
        IdentificationTypeDto identificationTypeDto = new IdentificationTypeDto(
            5, "CC", 0);

        assertThrows(
            ApiNoEntityException.class,
            () -> identificationTypeService.updateIdentificationType(
                id, identificationTypeDto)
        );
    }

    @Test
    @DisplayName("IdentificationTypeServiceTest - UpdateIdentificationType - " +
        "Must generate exception when Versions doesn't match")
    public void testUpdateIdentificationTypeVersionsDoesNotMatch() {
        Integer id = 5;

        IdentificationTypeDto identificationTypeDto = new IdentificationTypeDto(
            5, "CC", 1);

        IdentificationType identificationType = new IdentificationType(
            5, "CC", 3);

        when(
            identificationTypeDao.findById(5)
        ).thenReturn(Optional.of(identificationType));

        assertThrows(
            ApiConflictException.class,
            () -> identificationTypeService.updateIdentificationType(
                id, identificationTypeDto)
        );

        verify(identificationTypeDao, times(1)).
            findById(5);
    }

    @Test
    @DisplayName("IdentificationTypeServiceTest - UpdateIdentificationType - " +
        "Must generate exception when Name is null")
    public void testUpdateIdentificationTypeNullName() {
        Integer id = 5;

        IdentificationTypeDto identificationTypeDto = new IdentificationTypeDto(
            5, null, 1);

        IdentificationType identificationType = new IdentificationType(
            5, "CC", 1);

        when(
            identificationTypeDao.findById(5)
        ).thenReturn(Optional.of(identificationType));

        assertThrows(
            ApiMissingInformationException.class,
            () -> identificationTypeService.updateIdentificationType(
                id, identificationTypeDto)
        );

        verify(identificationTypeDao, times(1)).
            findById(5);
    }

    @Test
    @DisplayName("IdentificationTypeServiceTest - UpdateIdentificationType - " +
        "Must generate exception when Name is empty")
    public void testUpdateIdentificationTypeEmptyName() {
        Integer id = 5;

        IdentificationTypeDto identificationTypeDto = new IdentificationTypeDto(
            5, " ", 1);

        IdentificationType identificationType = new IdentificationType(
            5, "CC", 1);

        when(
            identificationTypeDao.findById(5)
        ).thenReturn(Optional.of(identificationType));

        assertThrows(
            ApiMissingInformationException.class,
            () -> identificationTypeService.updateIdentificationType(
                id, identificationTypeDto)
        );

        verify(identificationTypeDao, times(1)).
            findById(5);
    }

    @Test
    @DisplayName("IdentificationTypeServiceTest - UpdateIdentificationType - " +
        "Must generate exception when Name already exists")
    public void testUpdateIdentificationTypeNameExists() {
        Integer id = 5;

        IdentificationTypeDto identificationTypeDto = new IdentificationTypeDto(
            5, "TI", 1);

        IdentificationType identificationType = new IdentificationType(
            5, "CC", 1);

        IdentificationType identificationTypeExists = new IdentificationType(
            15, "TI", 12);

        when(
            identificationTypeDao.findById(5)
        ).thenReturn(Optional.of(identificationType));

        when(
            identificationTypeDao.getByName(identificationTypeDto.getName())
        ).thenReturn(Optional.of(identificationTypeExists));

        assertThrows(
            ApiConflictException.class,
            () -> identificationTypeService.updateIdentificationType(
                id, identificationTypeDto)
        );

        verify(identificationTypeDao, times(1)).
            findById(5);

        verify(identificationTypeDao, times(1)).
            getByName(identificationTypeDto.getName());
    }

    @Test
    @DisplayName("IdentificationTypeServiceTest - UpdateIdentificationType - " +
        "Must update and return new persisted info")
    public void testUpdateIdentificationType() {
        Integer id = 5;

        IdentificationTypeDto identificationTypeDto = new IdentificationTypeDto(
            5, "TI", 1);

        IdentificationType identificationType = new IdentificationType(
            5, "CC", 1);

        IdentificationType newIdentificationType = new IdentificationType(
            5, "TI", 2);

        IdentificationTypeDto expected = new IdentificationTypeDto(
            5, "TI", 2);

        when(
            identificationTypeDao.findById(5)
        ).thenReturn(Optional.of(identificationType));

        when(
            identificationTypeDao.save(any(IdentificationType.class))
        ).thenReturn(newIdentificationType);

        IdentificationTypeDto current =
            identificationTypeService.updateIdentificationType(
                id, identificationTypeDto);

        assertEntityWithDto(expected, current);

        verify(identificationTypeDao, times(1)).
            findById(5);

        verify(identificationTypeDao, times(1)).
            save(any(IdentificationType.class));
    }

    @Test
    @DisplayName("IdentificationTypeServiceTest - DeleteIdentificationType - " +
        "Must generate exception when ID is null")
    public void testDeleteIdentificationTypeIdNull() {
        assertThrows(
            ApiBadInformationException.class,
            () -> identificationTypeService.deleteIdentificationType(null)
        );
    }

    @Test
    @DisplayName("IdentificationTypeServiceTest - DeleteIdentificationType - " +
        "Must generate exception when ID doesn't exist")
    public void testDeleteIdentificationTypeIdDoesNotExist() {
        assertThrows(
            ApiNoEntityException.class,
            () -> identificationTypeService.deleteIdentificationType(7)
        );
    }

    @Test
    @DisplayName("IdentificationTypeServiceTest - DeleteIdentificationType - " +
        "Must delete the identification type")
    public void testDeleteIdentificationType() {
        Integer id = 7;

        IdentificationType identificationType = new IdentificationType(
            7, "CC", 1);

        when(
            identificationTypeDao.findById(id)
        ).thenReturn(Optional.of(identificationType));

        identificationTypeService.deleteIdentificationType(id);

        verify(identificationTypeDao, times(1)).
            findById(id);

        assertTimeoutPreemptively(Duration.ofSeconds(3),
            () -> identificationTypeService.deleteIdentificationType(id),
            "Should execute in less than 3 seconds");
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
