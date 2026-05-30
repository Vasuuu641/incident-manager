package com.security.incidentmanager.service;

import com.security.incidentmanager.domain.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import com.security.incidentmanager.exception.EntityNotFoundException;
import java.util.List;

public abstract class AbstractCrudService<T extends BaseEntity,
        R extends JpaRepository<T, Long>>
        implements CrudService<T, Long> {

    protected final R repository;

    protected AbstractCrudService(R repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public T findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        getClass().getSimpleName().replace("Service", "")
                                + " not found with id: " + id));
    }

    @Override
    @Transactional
    public T save(T entity) {
        return repository.save(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}