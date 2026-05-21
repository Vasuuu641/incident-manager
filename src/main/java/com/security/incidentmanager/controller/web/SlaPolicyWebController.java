package com.security.incidentmanager.controller.web;

import com.security.incidentmanager.domain.SlaPolicy;
import com.security.incidentmanager.service.SlaPolicyService;
import lombok.RequiredArgsConstructor;
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
    public String list(Model model) {
        model.addAttribute("policies", slaPolicyService.findAll());
        return "sla-policies/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("policy", new SlaPolicy());
        return "sla-policies/form";
    }

    @PostMapping
    public String create(@ModelAttribute SlaPolicy policy) {
        slaPolicyService.save(policy);
        return "redirect:/sla-policies";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("policy", slaPolicyService.findById(id));
        return "sla-policies/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @ModelAttribute SlaPolicy policy) {
        policy.setId(id);
        slaPolicyService.save(policy);
        return "redirect:/sla-policies";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            slaPolicyService.delete(id);
        } catch (IllegalStateException ex) {
            // add an error message and redirect back to the list page
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
            return "redirect:/sla-policies";
        }
        return "redirect:/sla-policies";
    }
}