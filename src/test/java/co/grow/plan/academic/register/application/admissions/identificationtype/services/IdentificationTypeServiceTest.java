package co.grow.plan.academic.register.application.admissions.identificationtype.services;

import co.grow.plan.academic.register.application.admissions.identificationtype.ports.spi.IIdentificationTypeRepositorySPI;
import co.grow.plan.academic.register.domain.admissions.identificationtype.model.IdentificationType;
import co.grow.plan.academic.register.shared.application.exceptions.ApiBadInformationException;
import co.grow.plan.academic.register.shared.application.exceptions.ApiConflictException;
import co.grow.plan.academic.register.shared.application.exceptions.ApiMissingInformationException;
import co.grow.plan.academic.register.shared.application.exceptions.ApiNoEntityException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public final class IdentificationTypeServiceTest {

    @Mock
    private IIdentificationTypeRepositorySPI repositorySPI;

    @InjectMocks
    private IdentificationTypeService identificationTypeService;

    @Test
    public void shouldReturnAListOfIdentificationTypes() {

        List<IdentificationType> identificationTypeList =
            List.of(
                new IdentificationType(1, "CC", 0L),
                new IdentificationType(2, "TI", 1L),
                new IdentificationType(3, "RC", 0L)
            );

        when(repositorySPI.findAll()).thenReturn(identificationTypeList);

        var currentList = identificationTypeService.list();

        assertEquals(identificationTypeList.size(), currentList.size());

        IdentificationType expected;
        IdentificationType current;

        for (int i = 0; i < identificationTypeList.size(); i++) {
            expected = identificationTypeList.get(i);
            current = currentList.get(i);

            assertObjectProperties(expected, current);
        }

        verify(repositorySPI, times(1)).findAll();
    }

    @Test
    public void shouldReturnAnEmptyListOfIdentificationTypes() {

        List<IdentificationType> identificationTypeList = List.of();

        when(repositorySPI.findAll()).thenReturn(identificationTypeList);

        List<IdentificationType> currentList = identificationTypeService.list();

        assertIterableEquals(identificationTypeList, currentList);
        verify(repositorySPI, times(1)).findAll();
    }

    @Test
    public void shouldGenerateExceptionWhenIdentificationTypeIsNull() {
        assertThrows(
            ApiBadInformationException.class,
            () -> identificationTypeService.create(null)
        );
    }

    @Test
    public void shouldGenerateExceptionWhenNameIsNull() {
        assertThrows(
            ApiMissingInformationException.class,
            () -> identificationTypeService.create(
                new IdentificationType(null,null, null)
            )
        );
    }

    @Test
    public void shouldGenerateExceptionWhenNameExists() {

        IdentificationType identificationType =
            new IdentificationType(null, "CC", null);

        IdentificationType identificationTypeConflict =
            new IdentificationType(99, "CC", 15L);

        when(
            repositorySPI.getByName(identificationType.name())
        ).thenReturn(Optional.of(identificationTypeConflict));

        assertThrows(
            ApiConflictException.class,
            () -> identificationTypeService.create(
                identificationType
            )
        );

        verify(repositorySPI, times(1)).
            getByName(identificationType.name());
    }

    @Test
    public void shouldCreateNewIdentificationTypeAndReturnPersistedInformation() {

        IdentificationType identificationType = new IdentificationType(
            null, "CC", null);

        IdentificationType expected = new IdentificationType(
            15, "CC", 0L);

        when(
            repositorySPI.save(identificationType)
        ).thenReturn(expected);

        IdentificationType persisted =
            identificationTypeService.create(identificationType);

        assertObjectProperties(expected, persisted);

        verify(repositorySPI, times(1)).
            save(any(IdentificationType.class));
    }

    @Test
    public void shouldGenerateExceptionWhenIdNullAtFindById() {
        assertThrows(
            ApiBadInformationException.class,
            () -> identificationTypeService.findById(null)
        );
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

        IdentificationType identificationType = new IdentificationType(
            15, "CC", 0L);

        when(
            repositorySPI.findById(15)
        ).thenReturn(identificationType);

        IdentificationType identificationTypeFound =
            identificationTypeService.findById(15);

        assertObjectProperties(identificationType, identificationTypeFound);

        verify(repositorySPI, times(1)).
            findById(15);
    }

    @Test
    public void shouldGenerateExceptionWhenIdNullAtUpdating () {
        assertThrows(
            ApiBadInformationException.class,
            () -> identificationTypeService.update(
                null, null)
        );
    }

    @Test
    public void shouldGenerateExceptionWhenObjectNullAtUpdating() {
        assertThrows(
            ApiBadInformationException.class,
            () -> identificationTypeService.update(
                5, null)
        );
    }

    @Test
    public void shouldGenerateExceptionWhenIdsDoesNotMatchAtUpdating() {
        Integer id = 5;
        IdentificationType identificationTypeDto = new IdentificationType(
            15, "CC", 0L);

        assertThrows(
            ApiBadInformationException.class,
            () -> identificationTypeService.update(
                id, identificationTypeDto)
        );
    }

    @Test
    public void shouldGenerateExceptionWhenIdDoesNotExistAtUpdating() {
        Integer id = 5;
        IdentificationType identificationTypeDto = new IdentificationType(
            5, "CC", 0L);

        assertThrows(
            ApiNoEntityException.class,
            () -> identificationTypeService.update(
                id, identificationTypeDto)
        );
    }

    @Test
    public void shouldGenerateExceptionWhenVersionsDoesNotMatchAtUpdating() {
        Integer id = 5;

        IdentificationType sourceIdentificationType = new IdentificationType(
            5, "CC", 1L);

        IdentificationType targetIdentificationType = new IdentificationType(
            5, "CC", 3L);

        when(
            repositorySPI.findById(5)
        ).thenReturn(targetIdentificationType);

        assertThrows(
            ApiConflictException.class,
            () -> identificationTypeService.update(
                id, sourceIdentificationType)
        );

        verify(repositorySPI, times(1)).
            findById(5);
    }

    @Test
    public void shouldGenerateExceptionWhenNameNullAtUpdating() {
        Integer id = 5;

        IdentificationType sourceIdentificationType = new IdentificationType(
            5, null, 1L);

        IdentificationType targetIdentificationType = new IdentificationType(
            5, "CC", 1L);

        when(
            repositorySPI.findById(5)
        ).thenReturn(targetIdentificationType);

        assertThrows(
            ApiMissingInformationException.class,
            () -> identificationTypeService.update(
                id, sourceIdentificationType)
        );

        verify(repositorySPI, times(1)).
            findById(5);
    }

    @Test
    public void shouldGenerateExceptionWhenNameIsEmptyAtUpdating() {
        Integer id = 5;

        IdentificationType sourceIdentificationType = new IdentificationType(
            5, " ", 1L);

        IdentificationType targetIdentificationType = new IdentificationType(
            5, "CC", 1L);

        when(
            repositorySPI.findById(5)
        ).thenReturn(targetIdentificationType);

        assertThrows(
            ApiMissingInformationException.class,
            () -> identificationTypeService.update(
                id, sourceIdentificationType)
        );

        verify(repositorySPI, times(1)).
            findById(5);
    }

    @Test
    public void shouldGenerateExceptionWhenNameExistsAtUpdating() {
        Integer id = 5;

        IdentificationType sourceIdentificationType = new IdentificationType(
            5, "TI", 1L);

        IdentificationType targetIdentificationType = new IdentificationType(
            5, "CC", 1L);

        IdentificationType identificationTypeWithSameName = new IdentificationType(
            15, "TI", 12L);

        when(
            repositorySPI.findById(5)
        ).thenReturn(targetIdentificationType);

        when(
            repositorySPI.getByName(sourceIdentificationType.name())
        ).thenReturn(Optional.of(identificationTypeWithSameName));

        assertThrows(
            ApiConflictException.class,
            () -> identificationTypeService.update(
                id, sourceIdentificationType)
        );

        verify(repositorySPI, times(1)).
            findById(5);

        verify(repositorySPI, times(1)).
            getByName(sourceIdentificationType.name());
    }

    @Test
    public void shouldUpdateIdentificationTypeAndReturnPersistedInformation() {
        Integer id = 5;

        IdentificationType sourceIdentificationType = new IdentificationType(
            5, "TI", 1L);

        IdentificationType targetIdentificationType = new IdentificationType(
            5, "CC", 1L);

        when(
            repositorySPI.findById(5)
        ).thenReturn(targetIdentificationType);

        IdentificationType updatedIdentificationTypeRepo = new IdentificationType(
            5, "TI", 2L);

        when(
            repositorySPI.save(sourceIdentificationType)
        ).thenReturn(updatedIdentificationTypeRepo);

        IdentificationType updatedIdentificationTypeService =
            identificationTypeService.update(
                id, sourceIdentificationType);

        assertObjectProperties(updatedIdentificationTypeRepo, updatedIdentificationTypeService);

        verify(repositorySPI, times(1)).
            findById(5);

        verify(repositorySPI, times(1)).
            save(any(IdentificationType.class));
    }

    @Test
    public void shouldGenerateExceptionWhenIdNullAtDeleting() {
        assertThrows(
            ApiBadInformationException.class,
            () -> identificationTypeService.delete(null)
        );
    }

    @Test
    public void shouldGenerateExceptionWhenIdDoesNotExistAtDeleting() {
        assertThrows(
            ApiNoEntityException.class,
            () -> identificationTypeService.delete(7)
        );
    }

    @Test
    public void shouldDeleteTheIdentificationType() {
        Integer id = 7;

        IdentificationType identificationType = new IdentificationType(
            7, "CC", 1L);

        when(
            repositorySPI.findById(id)
        ).thenReturn(identificationType);

        identificationTypeService.delete(id);

        verify(repositorySPI, times(1)).
            findById(id);

        assertTimeoutPreemptively(Duration.ofSeconds(3),
            () -> identificationTypeService.delete(id),
            "Should execute in less than 3 seconds");
    }

    // Utilities
    private void assertObjectProperties(
        IdentificationType expected,IdentificationType current) {

        assertEquals(expected.id(), current.id());
        assertEquals(expected.name(), current.name());
        assertEquals(expected.version(), current.version());
    }
}
