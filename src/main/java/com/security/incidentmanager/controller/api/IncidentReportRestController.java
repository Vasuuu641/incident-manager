package com.security.incidentmanager.controller.api;

import com.security.incidentmanager.domain.IncidentReport;
import com.security.incidentmanager.dto.mapper.AbstractMapper;
import com.security.incidentmanager.dto.mapper.IncidentReportMapper;
import com.security.incidentmanager.dto.request.IncidentReportRequestDTO;
import com.security.incidentmanager.dto.response.IncidentReportResponseDTO;
import com.security.incidentmanager.service.CrudService;
import com.security.incidentmanager.service.IncidentReportService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/reports")
public class IncidentReportRestController
        extends AbstractRestController<IncidentReport, IncidentReportRequestDTO, IncidentReportResponseDTO> {

    private final IncidentReportService incidentReportService;
    private final IncidentReportMapper incidentReportMapper;

    public IncidentReportRestController(
            IncidentReportService incidentReportService,
            IncidentReportMapper incidentReportMapper) {
        this.incidentReportService = incidentReportService;
        this.incidentReportMapper = incidentReportMapper;
    }

    @Override
    protected CrudService<IncidentReport, Long> getService() {
        return incidentReportService;
    }

    @Override
    protected AbstractMapper<IncidentReport, IncidentReportRequestDTO, IncidentReportResponseDTO> getMapper() {
        return incidentReportMapper;
    }

}