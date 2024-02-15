package co.grow.plan.academic.register.infrastructure.shared.generics;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BasicRestController<
    F extends FullEntityDto, // The full DTO
    C extends CreationalDto // The DTO without ID and Version
    > {

    ResponseEntity<List<F>> list();

    ResponseEntity<F> create(C dto);

    ResponseEntity<F> findById(Integer id);

    ResponseEntity<F> update(Integer id, F dto);

    ResponseEntity<Void> delete(Integer id);
}
