package ro.ubb.xml.Repository;

import ro.ubb.xml.Domain.Validators.Validator;
import ro.ubb.xml.Domain.Validators.ValidatorException;
import ro.ubb.xml.Domain.BaseEntity;
import ro.ubb.xml.Domain.Validators.ValidatorException;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryRepository<ID, T extends BaseEntity<ID>> implements Repository<ID, T> {
    Map<ID, T> entities;
    private Validator<T> validator;

    public InMemoryRepository(Validator<T> validator) {
        this.validator = validator;
        entities = new HashMap<>();
    }

    @Override
    public Optional<T> findOne(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        return Optional.ofNullable(entities.get(id));
    }

    @Override
    public Iterable<T> findAll() {
        return entities.values();
    }

    @Override
    public Optional<T> save(T entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null");
        }
        validator.validate(entity);
        return Optional.ofNullable(entities.put(entity.getId(), entity));
    }

    @Override
    public Optional<T> delete(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        return Optional.ofNullable(entities.remove(id));
    }

    @Override
    public Optional<T> update(T entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null");
        }
        validator.validate(entity);
        if (entities.containsKey(entity.getId())) {
            entities.put(entity.getId(), entity);
            return Optional.of(entity);
        }
        return Optional.empty();
    }
}