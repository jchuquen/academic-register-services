package co.grow.plan.academic.register.shared.generics;

import co.grow.plan.academic.register.shared.exceptions.ApiError;
import co.grow.plan.academic.register.shared.exceptions.ApiNoEntityException;
import co.grow.plan.academic.register.shared.helpers.ValidationsHelper;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public abstract class BaseEntityService<
    E, //The entity
    D extends CrudRepository<E, Integer>, // The Repository
    //TODO: Verify how I am using this
    F extends IIdentificableAndVersionable & INoIdAndVersionEntityDto, // The full DTO
    N extends INoIdAndVersionEntityDto, // The DTO without ID and Version
    M extends IBaseEntityMapper<E, F, N> // The mapper
    > {

    private D dao;
    private M mapper;


    //TODO: Use Spring validations in all methods to check incoming information
    //@Override
    public List<F> list() {
        return mapper.enityListToFullDtoList(
            dao.findAll());
    }

    //@Override
    public F create(N dto) {

        ValidationsHelper.validateNotNull(dto,
            "IdentificationType Object");

        dto.validateInfo();
        validateConstrains(null, dto);

        E entity = mapper.noIdAndVersionDtoToEntity(dto);
        entity = dao.save(entity);

        return mapper.entityToFullDto(entity);
    }

    //@Override
    public F findById(Integer id) {

        ValidationsHelper.validateNotNull(id, "ID");

        return mapper.entityToFullDto(
            validateInstanceIfExistsAndReturn(id));
    }

    //@Override
    public F update(Integer id, F dto) {

        ValidationsHelper.validateNotNull(id, "ID");
        ValidationsHelper.validateNotNull(dto,
            "IdentificationType Object");

        ValidationsHelper.validateIdsMatchingOrException(
            id, dto.getId());

        E entity = validateInstanceIfExistsAndReturn(id);

        ValidationsHelper.validateVersionsMatchOrException(
            dto.getVersion(), entity.getVersion());

        dto.validateInfo();

        validateConstrains(id, dto);

        entity =
            dao.save(
                mapper.updateEntityFromNoIdAndVersionDto(entity,
                    mapper.fullDtoToNoIdAndVersionDto(dto))
            );

        return mapper.entityToFullDto(entity);
    }

    //@Override
    public void delete(Integer id) {
        ValidationsHelper.validateNotNull(id, "ID");
        validateInstanceIfExistsAndReturn(id);
        dao.deleteById(id);
    }

    // Validations
    abstract void validateConstrains(Integer id,N dto);

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
