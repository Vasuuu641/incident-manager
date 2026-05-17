package com.security.incidentmanager.controller.api;

import com.security.incidentmanager.domain.Tag;
import com.security.incidentmanager.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagRestController {

    private final TagService tagService;

    @GetMapping
    public List<Tag> getAll() {
        return tagService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tag> getById(@PathVariable Long id) {
        return ResponseEntity.ok(tagService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Tag> create(@RequestBody Tag tag) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tagService.save(tag));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tag> update(
            @PathVariable Long id,
            @RequestBody Tag tag) {
        tag.setId(id);
        return ResponseEntity.ok(tagService.save(tag));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tagService.delete(id);
        return ResponseEntity.noContent().build();
    }
}