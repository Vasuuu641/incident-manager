package com.security.incidentmanager.service;

import com.security.incidentmanager.domain.Tag;
import com.security.incidentmanager.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TagService
        extends AbstractCrudService<Tag, TagRepository> {

    public TagService(TagRepository repository) {
        super(repository);
    }
}