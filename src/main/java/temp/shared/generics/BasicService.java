package temp.shared.generics;

import co.grow.plan.academic.register.shared.exceptions.ApiError;
import co.grow.plan.academic.register.shared.exceptions.ApiNoEntityException;
import lombok.AllArgsConstructor;
import java.util.List;
import java.util.Optional;

import static temp.shared.helpers.ValidationsHelper.*;

@AllArgsConstructor
public abstract class BasicService<
    E extends IEntity & IValidable,
    R extends IBasicRepository
    >
    implements IBasicService<E>
    {

    protected R repository;

    public BasicService() {
        super();
    }

        @Override
        public List<E> list() {
            return repository.findAll();
        }

        @Override
        public Optional<E> findById(Integer id) {
            validateNotNull(id, "ID");
            return repository.findById(id);
        }

        @Override
        public E create(E entity) {
            validateNotNull(entity,"Entity");
            entity.validate();
            validateConstrains(null, entity);
            return (E) repository.save(entity);
        }

        @Override
        public E update(Integer id, E entity) {
            validateNotNull(id, "ID");
            validateNotNull(entity,"Entity");

            validateIdsMatchingOrException(
                id, entity.id());

            E persistedEntity = validateInstanceIfExistsAndReturn(id);

            validateVersionsMatchOrException(
                entity.version(), persistedEntity.version());

            entity.validate();

            validateConstrains(id, entity);

            return (E) repository.save(entity);
        }

        @Override
        public void delete(Integer id) {
            validateNotNull(id, "ID");
            validateInstanceIfExistsAndReturn(id);
            repository.deleteById(id);
        }

        // Validations
        protected abstract void validateConstrains(Integer id, E entity);

        private E validateInstanceIfExistsAndReturn(Integer id) {

            Optional<E> optionalEntity = repository.findById(id);

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
