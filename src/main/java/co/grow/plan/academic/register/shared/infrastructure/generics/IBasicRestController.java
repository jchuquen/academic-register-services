package co.grow.plan.academic.register.shared.infrastructure.generics;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IBasicRestController<
    F extends IFullEntityDto, // The full DTO
    C extends ICreationalDto // The DTO without ID and Version
    > {

    ResponseEntity<List<F>> list();

    ResponseEntity<F> create(C dto);

    ResponseEntity<F> findById(Integer id);

    ResponseEntity<F> update(Integer id, F dto);

    ResponseEntity<Void> delete(Integer id);
}
