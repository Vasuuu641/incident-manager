package com.security.incidentmanager.service;

import com.security.incidentmanager.domain.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public abstract class AbstractCrudService<T extends BaseEntity,
        R extends JpaRepository<T, Long>>
        implements CrudService<T, Long> {

    protected final R repository;

    protected AbstractCrudService(R repository) {
        this.repository = repository;
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public T findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Entity not found with id: " + id));
    }

    @Override
    public T save(T entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}