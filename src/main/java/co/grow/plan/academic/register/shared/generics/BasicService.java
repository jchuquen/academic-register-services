package co.grow.plan.academic.register.shared.generics;

import co.grow.plan.academic.register.shared.exceptions.ApiError;
import co.grow.plan.academic.register.shared.exceptions.ApiNoEntityException;
import co.grow.plan.academic.register.shared.helpers.ValidationsHelper;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public abstract class BasicService<
    E extends IIdentifiableAndVersionable, //The entity
    D extends CrudRepository<E, Integer>, // The Repository
    F extends IIdentifiableAndVersionable & IValidable, // The full DTO
    N extends INoIdentifiableAndVersionable & IValidable, // The DTO without ID and Version
    M extends IBasicMapper<E, F, N> // The mapper
    >
    implements IBasicService<F, N>{

    protected D dao;
    protected M mapper;

    public BasicService(D dao, M mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    //TODO: Use Spring validations in all methods to check incoming information
    @Override
    public List<F> list() {
        return mapper.entityListToIdentifiableAndVersionableDtoList(
            dao.findAll());
    }

    @Override
    //@Transactional
    public F create(N dto) {

        ValidationsHelper.validateNotNull(dto,
            "IdentificationType Object");

        dto.validate();
        validateConstrains(null, dto);

        E entity = mapper.noIdentifiableAndVersionableDtoToEntity(dto);
        entity = dao.save(entity);


        return mapper.entityToIdentifiableAndVersionableDto(entity);
    }

    @Override
    public F findById(Integer id) {

        ValidationsHelper.validateNotNull(id, "ID");

        return mapper.entityToIdentifiableAndVersionableDto(
            validateInstanceIfExistsAndReturn(id));
    }

    @Override
    //@Transactional
    public F update(Integer id, F dto) {

        ValidationsHelper.validateNotNull(id, "ID");
        ValidationsHelper.validateNotNull(dto,
            "IdentificationType Object");

        ValidationsHelper.validateIdsMatchingOrException(
            id, dto.getId());

        E entity = validateInstanceIfExistsAndReturn(id);

        ValidationsHelper.validateVersionsMatchOrException(
            dto.getVersion(), entity.getVersion());

        dto.validate();

        validateConstrains(id, dto);

        entity =
            dao.save(
                mapper.updateEntityFromNoIdentifiableAndVersionableDto(entity,
                    mapper.identifiableAndVersionableDtoToNoIdentifiableAndVersionableDto(dto))
            );

        return mapper.entityToIdentifiableAndVersionableDto(entity);
    }

    @Override
    //@Transactional
    public void delete(Integer id) {
        ValidationsHelper.validateNotNull(id, "ID");
        validateInstanceIfExistsAndReturn(id);
        dao.deleteById(id);
    }

    // Validations
    protected abstract void validateConstrains(Integer id, IValidable dto);

    private E validateInstanceIfExistsAndReturn(Integer id) {

        Optional<E> optionalEntity = dao.findById(id);

        if (optionalEntity.isEmpty()) {
            throw new ApiNoEntityException(
                new ApiError(
                    String.format(
                        "Instance with id %s doesn't exist",
                        id)
                )
            );
        }
        return optionalEntity.get();
    }
}
