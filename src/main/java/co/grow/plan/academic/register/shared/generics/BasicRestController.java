package co.grow.plan.academic.register.shared.generics;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public abstract class BasicRestController<
    F extends IIdentifiableAndVersionable & IValidable, // The full DTO
    N extends INoIdentifiableAndVersionable & IValidable, // The DTO without ID and Version
    S extends IBasicService<F,N>
    >{

    protected S service;

    public BasicRestController(S service) {
        this.service = service;
    }

    @GetMapping(
        value = "" ,
        produces = { "application/json", "application/xml" }
    )
    public ResponseEntity<List<F>> list() {
        List<F> list = service.list();

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
    public ResponseEntity<F> create(@RequestBody N dto) {
        return new ResponseEntity<>(
            service.create(dto),
            HttpStatus.CREATED
        );
    }

    @GetMapping(
        value = "/{id}",
        produces = { "application/json", "application/xml" }
    )
    public ResponseEntity<F> findById(@PathVariable("id") Integer id) {

        F dto = service.findById(id);
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
            service.update(id, dto),
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