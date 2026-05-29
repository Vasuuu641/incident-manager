package com.security.incidentmanager.controller.web;

import com.security.incidentmanager.domain.SlaPolicy;
import com.security.incidentmanager.service.SlaPolicyService;
import com.security.incidentmanager.util.SecurityUtils; // ADDED
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication; // ADDED
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/sla-policies")
@RequiredArgsConstructor
public class SlaPolicyWebController {

    private final SlaPolicyService slaPolicyService;

    @GetMapping
    // CHANGED: added Authentication, isAdmin, buttonText
    public String list(Model model, Authentication authentication) {
        model.addAttribute("policies", slaPolicyService.findAll());
        model.addAttribute("view", "list");
        boolean isAdmin = SecurityUtils.isAdmin(authentication);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("buttonText", isAdmin ? "+ New Policy" : null);
        return "sla-policies";
    }

    @GetMapping("/new")
    // REMOVED: @PreAuthorize — handled by SecurityConfig
    public String newForm(Model model) {
        model.addAttribute("policy", new SlaPolicy());
        model.addAttribute("view", "form");
        model.addAttribute("formTitle", "New SLA Policy");
        return "sla-policies";
    }

    @PostMapping
    // REMOVED: @PreAuthorize — handled by SecurityConfig
    public String create(@ModelAttribute SlaPolicy policy) {
        slaPolicyService.save(policy);
        return "redirect:/sla-policies";
    }

    @GetMapping("/{id}/edit")
    // REMOVED: @PreAuthorize — handled by SecurityConfig
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("policy", slaPolicyService.findById(id));
        model.addAttribute("view", "form");
        model.addAttribute("formTitle", "Edit SLA Policy"); // FIXED: was "viewTitle"
        return "sla-policies";
    }

    @PostMapping("/{id}")
    // REMOVED: @PreAuthorize — handled by SecurityConfig
    public String update(@PathVariable Long id,
                         @ModelAttribute SlaPolicy policy) {
        policy.setId(id);
        slaPolicyService.save(policy);
        return "redirect:/sla-policies";
    }

    @PostMapping("/{id}/delete")
    // REMOVED: @PreAuthorize — handled by SecurityConfig
    public String delete(@PathVariable Long id,
                         RedirectAttributes redirectAttributes) {
        try {
            slaPolicyService.delete(id);
        } catch (IllegalStateException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/sla-policies";
    }
}