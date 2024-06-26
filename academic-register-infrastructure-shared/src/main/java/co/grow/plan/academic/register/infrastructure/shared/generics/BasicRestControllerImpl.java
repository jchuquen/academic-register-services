package co.grow.plan.academic.register.infrastructure.shared.generics;

import co.grow.plan.academic.register.application.shared.generics.services.BasicService;
import co.grow.plan.academic.register.domain.shared.interfaces.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@Getter
@Slf4j
public class BasicRestControllerImpl<
    F extends FullEntityDto, // The full DTO
    C extends CreationalDto, // The DTO without ID and Version
    E extends Entity,
    S extends BasicService<E>,
    M extends BasicInfrastructureDtoVsDomainEntityMapper<E, F, C>
    >
    implements BasicRestController<F, C> {

    private final S service;
    private final M mapper;

    @GetMapping(
        value = "" ,
        produces = { "application/json", "application/xml" }
    )
    public ResponseEntity<List<F>> list() {

        log.debug("Listing Entities using BasicRestControllerImpl");

        var list =
            mapper.domainEntitiesToFullDtos(service.list());

        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(
                list, HttpStatus.OK
            );
        }
    }

    @PostMapping(
        value = "",
        produces = { "application/json", "application/xml" },
        consumes = { "application/json", "application/xml", "application/x-www-form-urlencoded" }
    )
    public ResponseEntity<F> create(@RequestBody C dto) {

        log.debug("Creating entity using BasicRestControllerImpl");

        return new ResponseEntity<>(
            mapper.domainEntityToFullDto(
                service.create(
                    mapper.creationalDtoToDomainEntity(dto)
                )
            ),
            HttpStatus.CREATED
        );
    }

    @GetMapping(
        value = "/{id}",
        produces = { "application/json", "application/xml" }
    )
    public ResponseEntity<F> findById(@PathVariable("id") Integer id) {

        log.debug(String.format("Finding entity by id %s using BasicRestControllerImpl", id));

        var dto = mapper.domainEntityToFullDto(service.findById(id));
        if (dto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @PutMapping(
        value = "/{id}",
        produces = { "application/json", "application/xml" },
        consumes = { "application/json", "application/xml", "application/x-www-form-urlencoded" }
    )
    public ResponseEntity<F> update(
        @PathVariable("id")Integer id,
        @RequestBody F dto) {

        log.debug(String.format("Updating entity with id %s using BasicRestControllerImpl", id));

        return new ResponseEntity<>(
            mapper.domainEntityToFullDto(
                service.update(id,
                    mapper.fullDtoToDomainEntity(dto)
                )
            ),
            HttpStatus.OK
        );
    }

    @DeleteMapping(
        value = "/{id}",
        produces = { "application/json", "application/xml" }
    )
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {

        log.debug(String.format("Updating entity with id %s using BasicRestControllerImpl", id));

        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}