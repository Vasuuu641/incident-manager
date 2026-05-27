package com.security.incidentmanager.service;

import java.util.List;

public interface CrudService<T, ID> {
    List<T> findAll();
    T findById(ID id);
    T save(T entity);
    void delete(ID id);
}