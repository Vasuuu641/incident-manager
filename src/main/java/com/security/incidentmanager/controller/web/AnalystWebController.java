package com.security.incidentmanager.controller.web;

import com.security.incidentmanager.domain.Analyst;
import com.security.incidentmanager.service.AnalystService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

@Controller
@RequestMapping("/analysts")
@RequiredArgsConstructor
public class AnalystWebController {

    private final AnalystService analystService;

    @GetMapping
    public String list(Model model, Authentication authentication) {
        model.addAttribute("analysts", analystService.findAll());
        model.addAttribute("view", "list");
        boolean isAdmin = authentication != null &&
                authentication.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("showNewButton", isAdmin);
        model.addAttribute("buttonText", isAdmin ? "+ New Analyst" : null);
        model.addAttribute("isAdmin", isAdmin);
        return "analysts";
    }

    @GetMapping("/new")
    @PreAuthorize("hasRole('ADMIN')")
    public String newForm(Model model) {
        model.addAttribute("analyst", new Analyst());
        model.addAttribute("view", "form");
        model.addAttribute("formTitle", "New Analyst");
        return "analysts";
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String create(@ModelAttribute Analyst analyst) {
        analystService.save(analyst);
        return "redirect:/analysts";
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("analyst", analystService.findById(id));
        model.addAttribute("view", "form");
        model.addAttribute("formTitle", "Edit Analyst");
        return "analysts";
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String update(@PathVariable Long id,
                         @ModelAttribute Analyst analyst) {
        analyst.setId(id);
        analystService.save(analyst);
        return "redirect:/analysts";
    }

    @PostMapping("/{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public String delete(@PathVariable Long id) {
        analystService.delete(id);
        return "redirect:/analysts";
    }
}