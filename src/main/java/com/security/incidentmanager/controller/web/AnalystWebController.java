package com.security.incidentmanager.controller.web;

import com.security.incidentmanager.domain.Analyst;
import com.security.incidentmanager.service.AnalystService;
import com.security.incidentmanager.util.SecurityUtils; // ADDED
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication; // ADDED
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/analysts")
@RequiredArgsConstructor
public class AnalystWebController {

    private final AnalystService analystService;

    @GetMapping
    // CHANGED: added Authentication, isAdmin, buttonText
    public String list(Model model, Authentication authentication) {
        model.addAttribute("analysts", analystService.findAll());
        model.addAttribute("view", "list");
        boolean isAdmin = SecurityUtils.isAdmin(authentication);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("buttonText", isAdmin ? "+ New Analyst" : null);
        return "analysts";
    }

    @GetMapping("/new")
    // REMOVED: @PreAuthorize — handled by SecurityConfig
    public String newForm(Model model) {
        model.addAttribute("analyst", new Analyst());
        model.addAttribute("view", "form");
        model.addAttribute("formTitle", "New Analyst");
        return "analysts";
    }

    @PostMapping
    // REMOVED: @PreAuthorize — handled by SecurityConfig
    public String create(@ModelAttribute Analyst analyst) {
        analystService.save(analyst);
        return "redirect:/analysts";
    }

    @GetMapping("/{id}/edit")
    // REMOVED: @PreAuthorize — handled by SecurityConfig
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("analyst", analystService.findById(id));
        model.addAttribute("view", "form");
        model.addAttribute("formTitle", "Edit Analyst");
        return "analysts";
    }

    @PostMapping("/{id}")
    // REMOVED: @PreAuthorize — handled by SecurityConfig
    public String update(@PathVariable Long id,
                         @ModelAttribute Analyst analyst) {
        analyst.setId(id);
        analystService.save(analyst);
        return "redirect:/analysts";
    }

    @PostMapping("/{id}/delete")
    // REMOVED: @PreAuthorize — handled by SecurityConfig
    public String delete(@PathVariable Long id) {
        analystService.delete(id);
        return "redirect:/analysts";
    }
}