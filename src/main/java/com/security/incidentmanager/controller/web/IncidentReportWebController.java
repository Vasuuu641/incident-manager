package com.security.incidentmanager.controller.web;

import com.security.incidentmanager.domain.IncidentReport;
import com.security.incidentmanager.service.IncidentReportService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.security.incidentmanager.service.CrudService;

@Controller
@RequestMapping("/reports")
public class IncidentReportWebController
        extends AbstractWebController<IncidentReport> {

    private final IncidentReportService incidentReportService;

    public IncidentReportWebController(IncidentReportService incidentReportService) {
        this.incidentReportService = incidentReportService;
    }

    @Override
    protected CrudService<IncidentReport, Long> getService() {
        return incidentReportService;
    }

    @Override
    protected String getTemplateName() { return "reports"; }

    @Override
    protected String getEntityAttributeName() { return "report"; }

    @Override
    protected String getBaseUrl() { return "/reports"; }

    @Override
    protected String getNewButtonLabel() { return "+ New Report"; }

    @Override
    protected IncidentReport newEntity() { return new IncidentReport(); }

}