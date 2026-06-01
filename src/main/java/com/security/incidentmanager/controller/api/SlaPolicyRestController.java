package com.security.incidentmanager.controller.api;

import com.security.incidentmanager.domain.SlaPolicy;
import com.security.incidentmanager.dto.mapper.SlaPolicyMapper;
import com.security.incidentmanager.dto.request.SlaPolicyRequestDTO;
import com.security.incidentmanager.dto.response.SlaPolicyResponseDTO;
import com.security.incidentmanager.service.CrudService;
import com.security.incidentmanager.service.SlaPolicyService;
import com.security.incidentmanager.dto.mapper.AbstractMapper;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/sla-policies")

public class SlaPolicyRestController
        extends AbstractRestController<SlaPolicy, SlaPolicyRequestDTO, SlaPolicyResponseDTO> {

    private final SlaPolicyService slaPolicyService;
    private final SlaPolicyMapper slaPolicyMapper;

    public SlaPolicyRestController(SlaPolicyService slaPolicyService,
                                   SlaPolicyMapper slaPolicyMapper) {
        this.slaPolicyService = slaPolicyService;
        this.slaPolicyMapper = slaPolicyMapper;
    }

    @Override
    protected CrudService<SlaPolicy, Long> getService() {
        return slaPolicyService;
    }

    @Override
    protected AbstractMapper<SlaPolicy, SlaPolicyRequestDTO, SlaPolicyResponseDTO> getMapper() {
        return slaPolicyMapper;
    }
}