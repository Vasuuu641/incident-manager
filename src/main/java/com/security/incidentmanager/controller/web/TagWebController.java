package com.security.incidentmanager.controller.web;

import com.security.incidentmanager.domain.Tag;
import com.security.incidentmanager.service.TagService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.security.incidentmanager.service.CrudService;

@Controller
@RequestMapping("/tags")
public class TagWebController
        extends AbstractWebController<Tag> {

    private final TagService tagService;

    public TagWebController(TagService tagService) {
        this.tagService = tagService;
    }

    @Override
    protected CrudService<Tag, Long> getService() { return tagService; }

    @Override
    protected String getTemplateName() { return "tags"; }

    @Override
    protected String getEntityAttributeName() { return "tag"; }

    @Override
    protected String getBaseUrl() { return "/tags"; }

    @Override
    protected String getNewButtonLabel() { return "+ New Tag"; }

    @Override
    protected Tag newEntity() { return new Tag(); }
}