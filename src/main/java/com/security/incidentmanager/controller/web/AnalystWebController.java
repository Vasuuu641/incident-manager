package com.security.incidentmanager.controller.web;

import com.security.incidentmanager.domain.Analyst;
import com.security.incidentmanager.service.AnalystService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.security.incidentmanager.service.CrudService;

@Controller
@RequestMapping("/analysts")
public class AnalystWebController
        extends AbstractWebController<Analyst> {

    private final AnalystService analystService;

    public AnalystWebController(AnalystService analystService) {
        this.analystService = analystService;
    }

    @Override
    protected CrudService<Analyst, Long> getService() {
        return analystService;
    }

    @Override
    protected String getTemplateName() { return "analysts"; }

    @Override
    protected String getEntityAttributeName() { return "analyst"; }

    @Override
    protected String getBaseUrl() { return "/analysts"; }

    @Override
    protected String getNewButtonLabel() { return "+ New Analyst"; }

    @Override
    protected Analyst newEntity() { return new Analyst(); }
}