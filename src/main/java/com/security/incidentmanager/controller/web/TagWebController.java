package com.security.incidentmanager.controller.web;

import com.security.incidentmanager.domain.Tag;
import com.security.incidentmanager.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagWebController {

    private final TagService tagService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("tags", tagService.findAll());
        model.addAttribute("view", "list");
        return "tags";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("tag", new Tag());
        model.addAttribute("view", "form");
        return "tags";
    }

    @PostMapping
    public String create(@ModelAttribute Tag tag) {
        tagService.save(tag);
        return "redirect:/tags";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("tag", tagService.findById(id));
        model.addAttribute("view", "form");
        return "tags";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @ModelAttribute Tag tag) {
        tag.setId(id);
        tagService.save(tag);
        return "redirect:/tags";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        tagService.delete(id);
        return "redirect:/tags";
    }
}