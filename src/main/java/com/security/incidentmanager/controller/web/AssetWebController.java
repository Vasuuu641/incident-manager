package com.security.incidentmanager.controller.web;

import com.security.incidentmanager.domain.Asset;
import com.security.incidentmanager.service.AssetService;
import com.security.incidentmanager.service.IncidentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/assets")
@RequiredArgsConstructor
public class AssetWebController {

    private final AssetService assetService;
    private final IncidentService incidentService;

    @GetMapping("/new")
    public String newForm(@RequestParam Long incidentId, Model model) {
        Asset asset = new Asset();
        model.addAttribute("asset", asset);
        model.addAttribute("incident",
                incidentService.findById(incidentId));
        model.addAttribute("view", "form");
        return "assets";
    }

    @PostMapping
    public String create(@ModelAttribute Asset asset,
                         @RequestParam Long incidentId) {
        asset.setIncident(incidentService.findById(incidentId));
        assetService.save(asset);
        return "redirect:/incidents/" + incidentId;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id,
                         @RequestParam Long incidentId) {
        assetService.delete(id);
        return "redirect:/incidents/" + incidentId;
    }
}