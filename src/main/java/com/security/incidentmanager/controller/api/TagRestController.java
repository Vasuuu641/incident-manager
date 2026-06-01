package com.security.incidentmanager.controller.api;

import com.security.incidentmanager.domain.Tag;
import com.security.incidentmanager.dto.mapper.TagMapper;
import com.security.incidentmanager.dto.request.TagRequestDTO;
import com.security.incidentmanager.dto.response.TagResponseDTO;
import com.security.incidentmanager.dto.mapper.AbstractMapper;
import com.security.incidentmanager.service.CrudService;
import com.security.incidentmanager.service.TagService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/tags")
public class TagRestController
        extends AbstractRestController<Tag, TagRequestDTO, TagResponseDTO> {

    private final TagService tagService;
    private final TagMapper tagMapper;

    public TagRestController(TagService tagService,
                             TagMapper tagMapper) {
        this.tagService = tagService;
        this.tagMapper = tagMapper;
    }

    @Override
    protected CrudService<Tag, Long> getService() {
        return tagService;
    }

    @Override
    protected AbstractMapper<Tag, TagRequestDTO, TagResponseDTO> getMapper() {
        return tagMapper;
    }
}