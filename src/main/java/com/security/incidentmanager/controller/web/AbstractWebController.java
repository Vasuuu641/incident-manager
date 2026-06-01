package com.security.incidentmanager.controller.web;

import com.security.incidentmanager.domain.BaseEntity;
import com.security.incidentmanager.service.CrudService;
import com.security.incidentmanager.util.SecurityUtils;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

public abstract class AbstractWebController<T extends BaseEntity> {

    protected abstract CrudService<T, Long> getService();
    protected abstract String getTemplateName();
    protected abstract String getEntityAttributeName();
    protected abstract String getBaseUrl();
    protected abstract String getNewButtonLabel();
    protected abstract T newEntity();

    @GetMapping
    public String list(Model model, Authentication authentication) {
        model.addAttribute(getEntityAttributeName() + "s",
                getService().findAll());
        model.addAttribute("view", "list");
        boolean isAdmin = SecurityUtils.isAdmin(authentication);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("buttonText",
                isAdmin ? getNewButtonLabel() : null);
        return getTemplateName();
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute(getEntityAttributeName(), newEntity());
        model.addAttribute("view", "form");
        model.addAttribute("formTitle", "New "
                + capitalize(getEntityAttributeName()));
        return getTemplateName();
    }

    @PostMapping
    public String create(@ModelAttribute T entity) {
        getService().save(entity);
        return "redirect:" + getBaseUrl();
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute(getEntityAttributeName(),
                getService().findById(id));
        model.addAttribute("view", "form");
        model.addAttribute("formTitle", "Edit "
                + capitalize(getEntityAttributeName()));
        return getTemplateName();
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @ModelAttribute T entity) {
        entity.setId(id);
        getService().save(entity);
        return "redirect:" + getBaseUrl();
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        getService().delete(id);
        return "redirect:" + getBaseUrl();
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return Character.toUpperCase(str.charAt(0))
                + str.substring(1);
    }
}