package temp.shared.infrastructure.generics;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import temp.shared.application.generics.IBasicService;
import temp.shared.domain.interfaces.IEntity;

import java.util.List;

@RestController
@AllArgsConstructor
public class BasicRestController<
    F extends IFullEntityDto, // The full DTO
    C extends ICreationalDto, // The DTO without ID and Version
    E extends IEntity,
    S extends IBasicService<E>,
    M extends IBasicInfrastructureDtoVsDomainEntityMapper<E, F, C>
    >
    implements IBasicRestController<F, C> {

    protected S service;
    protected M mapper;

    @GetMapping(
        value = "" ,
        produces = { "application/json", "application/xml" }
    )
    public ResponseEntity<List<F>> list() {
        List<F> list =
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

        F dto = mapper.domainEntityToFullDto(service.findById(id));
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
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}