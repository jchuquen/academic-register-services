package co.grow.plan.academic.register.admissions.identificationtype.application;


import co.grow.plan.academic.register.AcademicRegisterServicesApplication;
import co.grow.plan.academic.register.admissions.identificationtype.domain.IdentificationType;
import co.grow.plan.academic.register.admissions.identificationtype.domain.IdentificationTypeDao;
import co.grow.plan.academic.register.shared.exceptions.ApiBadInformationException;
import co.grow.plan.academic.register.shared.exceptions.ApiConflictException;
import co.grow.plan.academic.register.shared.exceptions.ApiMissingInformationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
            IdentificationTypeDto expectedItem = expectedList.get(i);
            IdentificationTypeDto currentItem = currentList.get(i);

            assertEquals(expectedItem.getId(), currentItem.getId());
            assertEquals(expectedItem.getName(), currentItem.getName());
            assertEquals(expectedItem.getVersion(), currentItem.getVersion());
        }
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
        "breaking name constrain")
    public void testCreateIdentificationTypeBreakingConstrain1() {

        IdentificationTypeNewDto identificationTypeNewDto =
            new IdentificationTypeNewDto("CC");

        IdentificationType identificationTypeConflict =
            new IdentificationType(99, "CC", 15);

        when(
            identificationTypeDao.getByName(identificationTypeNewDto.getName())
        ).thenReturn(identificationTypeConflict);

        assertThrows(
            ApiConflictException.class,
            () -> identificationTypeService.createIdentificationType(
                identificationTypeNewDto
            )
        );
    }

    @Test
    @DisplayName("IdentificationTypeServiceTest - Create - Must create new record and " +
        "return created information")
    public void testCreateIdentificationType() {

        IdentificationTypeNewDto identificationTypeNewDto =
            new IdentificationTypeNewDto("CC");

        IdentificationType identificationType = new IdentificationType(
          15, "CC", 0);

        when(
            identificationTypeDao.save(any(IdentificationType.class))
        ).thenReturn(identificationType);

        IdentificationTypeDto identificationTypeSaved =
            identificationTypeService.createIdentificationType(identificationTypeNewDto);

        assertEquals(identificationType.getId(), identificationTypeSaved.getId());
        assertEquals(identificationType.getName(), identificationTypeSaved.getName());
        assertEquals(identificationType.getVersion(), identificationTypeSaved.getVersion());
    }

    @Test
    @DisplayName("IdentificationTypeServiceTest - Find - Must generate exception " +
        "when ID is null")
    public void testFindIdentificationTypeByIdBadRequest1() {
        //TODO
    }
}
