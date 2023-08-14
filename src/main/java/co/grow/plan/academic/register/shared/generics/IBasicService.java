package co.grow.plan.academic.register.shared.generics;

import java.util.List;

public interface IBasicService <
    F extends IIdentifiableAndVersionable & IValidable, // The full DTO
    N extends INoIdentifiableAndVersionable & IValidable // The DTO without ID and Version
    >{

    List<F> list();
    F create(N dto);
    F findById(Integer id);
    F update(Integer id, F dto);
    void delete(Integer id);
}
