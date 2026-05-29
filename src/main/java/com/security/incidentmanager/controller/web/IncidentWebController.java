package com.security.incidentmanager.controller.web;

import com.security.incidentmanager.domain.Incident;
import com.security.incidentmanager.domain.IncidentReport;
import com.security.incidentmanager.service.AnalystService;
import com.security.incidentmanager.service.IncidentService;
import com.security.incidentmanager.service.TagService;
import com.security.incidentmanager.service.SlaPolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/incidents")
@RequiredArgsConstructor
public class IncidentWebController {

    private final IncidentService incidentService;
    private final AnalystService analystService;
    private final TagService tagService;
    private final SlaPolicyService slaPolicyService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("incidents", incidentService.findAll());
        model.addAttribute("view", "list");
        return "incidents";
    }

    @GetMapping("/new")
    @PreAuthorize("hasRole('ADMIN')")
    public String newForm(Model model) {
        model.addAttribute("incident", new Incident());
        model.addAttribute("analysts", analystService.findAll());
        model.addAttribute("tags", tagService.findAll());
        model.addAttribute("policies", slaPolicyService.findAll());
        model.addAttribute("view", "form");
        model.addAttribute("formTitle", "New Incident");
        return "incidents";
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String create(@ModelAttribute Incident incident) {
        incident.setDetectedAt(LocalDateTime.now());
        if (incident.getDetectedAt() == null) {
            incident.setDetectedAt(LocalDateTime.now());
        }
        incidentService.save(incident);
        return "redirect:/incidents";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model) {
        model.addAttribute("incident", incidentService.findById(id));
        model.addAttribute("view", "detail");
        return "incidents";
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("incident", incidentService.findById(id));
        model.addAttribute("analysts", analystService.findAll());
        model.addAttribute("tags", tagService.findAll());
        model.addAttribute("policies", slaPolicyService.findAll());
        model.addAttribute("view", "form");
        model.addAttribute("formTitle", "Edit Incident");
        return "incidents";
    }

    @GetMapping("/{id}/report/new")
    public String newReportForm(@PathVariable Long id, Model model) {
        model.addAttribute("incident", incidentService.findById(id));
        model.addAttribute("report", new IncidentReport());
        return "report-form";
    }

    @PostMapping("/{id}/report")
    public String createReport(@PathVariable Long id,
                               @ModelAttribute IncidentReport report) {
        Incident incident = incidentService.findById(id);
        report.setCreatedAt(LocalDateTime.now());
        incident.setReport(report);
        incidentService.save(incident);
        return "redirect:/incidents/" + id;
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String update(@PathVariable Long id,
                         @ModelAttribute Incident incident) {
        incident.setId(id);
        incidentService.save(incident);
        return "redirect:/incidents";
    }

    @PostMapping("/{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public String delete(@PathVariable Long id) {
        incidentService.delete(id);
        return "redirect:/incidents";
    }
}