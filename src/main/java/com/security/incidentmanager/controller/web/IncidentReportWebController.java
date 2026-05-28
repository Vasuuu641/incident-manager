package com.security.incidentmanager.controller.web;

import com.security.incidentmanager.domain.IncidentReport;
import com.security.incidentmanager.service.IncidentReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reports")
@RequiredArgsConstructor
public class IncidentReportWebController {

    private final IncidentReportService incidentReportService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("reports", incidentReportService.findAll());
        model.addAttribute("view", "list");
        return "reports";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("report", new IncidentReport());
        model.addAttribute("view", "form");
        return "reports";
    }

    @PostMapping
    public String create(@ModelAttribute IncidentReport report) {
        incidentReportService.save(report);
        return "redirect:/reports";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("report", incidentReportService.findById(id));
        model.addAttribute("view", "form");
        return "reports";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @ModelAttribute IncidentReport report) {
        report.setId(id);
        incidentReportService.save(report);
        return "redirect:/reports";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        incidentReportService.delete(id);
        return "redirect:/reports";
    }
}