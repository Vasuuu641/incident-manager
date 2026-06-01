package com.security.incidentmanager.dto.mapper;

import com.security.incidentmanager.domain.Tag;
import com.security.incidentmanager.dto.request.TagRequestDTO;
import com.security.incidentmanager.dto.response.TagResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class TagMapper
        implements AbstractMapper<Tag, TagRequestDTO, TagResponseDTO> {

    @Override
    public TagResponseDTO toResponseDTO(Tag tag) {
        TagResponseDTO dto = new TagResponseDTO();
        dto.setId(tag.getId());
        dto.setName(tag.getName());
        dto.setColor(tag.getColor());
        dto.setCreatedAt(tag.getCreatedAt());
        return dto;
    }

    @Override
    public Tag toEntity(TagRequestDTO dto) {
        Tag tag = new Tag();
        tag.setName(dto.getName());
        tag.setColor(dto.getColor());
        return tag;
    }
}