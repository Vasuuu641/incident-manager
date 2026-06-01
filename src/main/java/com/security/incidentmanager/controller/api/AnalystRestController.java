package com.security.incidentmanager.controller.api;

import com.security.incidentmanager.domain.Analyst;
import com.security.incidentmanager.dto.mapper.AnalystMapper;
import com.security.incidentmanager.dto.mapper.AbstractMapper;
import com.security.incidentmanager.dto.request.AnalystRequestDTO;
import com.security.incidentmanager.dto.response.AnalystResponseDTO;
import com.security.incidentmanager.service.AnalystService;
import com.security.incidentmanager.service.CrudService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/analysts")
public class AnalystRestController
        extends AbstractRestController<Analyst, AnalystRequestDTO, AnalystResponseDTO> {

    private final AnalystService analystService;
    private final AnalystMapper analystMapper;

    public AnalystRestController(AnalystService analystService,
                                 AnalystMapper analystMapper) {
        this.analystService = analystService;
        this.analystMapper = analystMapper;
    }

    @Override
    protected CrudService<Analyst, Long> getService() {
        return analystService;
    }

    @Override
    protected AbstractMapper<Analyst, AnalystRequestDTO, AnalystResponseDTO> getMapper() {
        return analystMapper;
    }
}