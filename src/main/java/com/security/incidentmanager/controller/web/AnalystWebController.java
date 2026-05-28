package com.security.incidentmanager.controller.web;

import com.security.incidentmanager.domain.Analyst;
import com.security.incidentmanager.service.AnalystService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/analysts")
@RequiredArgsConstructor
public class AnalystWebController {

    private final AnalystService analystService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("analysts", analystService.findAll());
        return "analysts";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("analyst", new Analyst());
        return "analysts";
    }

    @PostMapping
    public String create(@ModelAttribute Analyst analyst) {
        analystService.save(analyst);
        return "redirect:/analysts";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("analyst", analystService.findById(id));
        return "analysts";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @ModelAttribute Analyst analyst) {
        analyst.setId(id);
        analystService.save(analyst);
        return "redirect:/analysts";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        analystService.delete(id);
        return "redirect:/analysts";
    }
}