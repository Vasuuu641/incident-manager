package com.security.incidentmanager.controller.api;

import com.security.incidentmanager.domain.Tag;
import com.security.incidentmanager.dto.mapper.TagMapper;
import com.security.incidentmanager.dto.request.TagRequestDTO;
import com.security.incidentmanager.dto.response.TagResponseDTO;
import com.security.incidentmanager.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagRestController {

    private final TagService tagService;
    private final TagMapper tagMapper;

    @GetMapping
    public List<TagResponseDTO> getAll() {
        return tagService.findAll()
                .stream()
                .map(tagMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(
                tagMapper.toResponseDTO(tagService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<TagResponseDTO> create(
            @RequestBody TagRequestDTO dto) {
        Tag saved = tagService.save(tagMapper.toEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tagMapper.toResponseDTO(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagResponseDTO> update(
            @PathVariable Long id,
            @RequestBody TagRequestDTO dto) {
        Tag tag = tagMapper.toEntity(dto);
        tag.setId(id);
        return ResponseEntity.ok(
                tagMapper.toResponseDTO(tagService.save(tag)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tagService.delete(id);
        return ResponseEntity.noContent().build();
    }
}