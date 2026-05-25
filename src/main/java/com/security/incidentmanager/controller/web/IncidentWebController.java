package com.security.incidentmanager.controller.web;

import com.security.incidentmanager.domain.Incident;
import com.security.incidentmanager.domain.IncidentReport;
import com.security.incidentmanager.service.AnalystService;
import com.security.incidentmanager.service.IncidentService;
import com.security.incidentmanager.service.TagService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping
    public String list(Model model) {
        model.addAttribute("incidents", incidentService.findAll());
        return "incidents/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("incident", new Incident());
        model.addAttribute("analysts", analystService.findAll());
        model.addAttribute("tags", tagService.findAll());
        return "incidents/form";
    }

    @PostMapping
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
        return "incidents/view";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("incident", incidentService.findById(id));
        model.addAttribute("analysts", analystService.findAll());
        model.addAttribute("tags", tagService.findAll());
        return "incidents/form";
    }

    @GetMapping("/{id}/report/new")
    public String newReportForm(@PathVariable Long id, Model model) {
        model.addAttribute("incident", incidentService.findById(id));
        model.addAttribute("report", new IncidentReport());
        return "incidents/report-form";
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
    public String update(@PathVariable Long id,
                         @ModelAttribute Incident incident) {
        incident.setId(id);
        incidentService.save(incident);
        return "redirect:/incidents";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        incidentService.delete(id);
        return "redirect:/incidents";
    }
}