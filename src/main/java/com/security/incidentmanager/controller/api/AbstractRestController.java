package com.security.incidentmanager.controller.api;

import com.security.incidentmanager.domain.BaseEntity;
import com.security.incidentmanager.dto.mapper.AbstractMapper;
import com.security.incidentmanager.service.CrudService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractRestController<
        T extends BaseEntity,
        RQ,
        RS> {

protected abstract CrudService<T, Long> getService();
protected abstract AbstractMapper<T, RQ, RS> getMapper();

@GetMapping
public List<RS> getAll() {
    return getService().findAll()
            .stream()
            .map(getMapper()::toResponseDTO)
            .collect(Collectors.toList());
}

@GetMapping("/{id}")
public ResponseEntity<RS> getById(@PathVariable Long id) {
    return ResponseEntity.ok(
            getMapper().toResponseDTO(
                    getService().findById(id)));
}

@PostMapping
public ResponseEntity<RS> create(@RequestBody RQ dto) {
    T saved = getService().save(getMapper().toEntity(dto));
    return ResponseEntity.status(HttpStatus.CREATED)
            .body(getMapper().toResponseDTO(saved));
}

@PutMapping("/{id}")
public ResponseEntity<RS> update(@PathVariable Long id,
                                 @RequestBody RQ dto) {
    T entity = getMapper().toEntity(dto);
    entity.setId(id);
    return ResponseEntity.ok(
            getMapper().toResponseDTO(
                    getService().save(entity)));
}

@DeleteMapping("/{id}")
public ResponseEntity<Void> delete(@PathVariable Long id) {
    getService().delete(id);
    return ResponseEntity.noContent().build();
}
}