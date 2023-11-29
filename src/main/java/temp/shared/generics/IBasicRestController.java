package temp.shared.generics;


import co.grow.plan.academic.register.shared.generics.INoIdentifiableAndVersionable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IBasicRestController<
    F extends IIdentifiableAndVersionable & IValidable, // The full DTO
    N extends INoIdentifiableAndVersionable & IValidable // The DTO without ID and Version
    > {

    ResponseEntity<List<F>> list();

    ResponseEntity<F> create(N dto);

    ResponseEntity<F> findById(Integer id);

    ResponseEntity<F> update(Integer id, F dto);

    ResponseEntity<Void> delete(Integer id);
}
