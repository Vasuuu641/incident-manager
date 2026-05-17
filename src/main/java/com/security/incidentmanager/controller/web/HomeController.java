package com.security.incidentmanager.controller.web;

import com.security.incidentmanager.service.IncidentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final IncidentService incidentService;

    @GetMapping("/")
    public String root() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home(Model model) {
        long openCount = incidentService.findByStatus("OPEN").size();
        long inProgressCount = incidentService.findByStatus("IN_PROGRESS").size();
        long resolvedCount = incidentService.findByStatus("RESOLVED").size();

        model.addAttribute("openCount", openCount);
        model.addAttribute("inProgressCount", inProgressCount);
        model.addAttribute("resolvedCount", resolvedCount);

        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}