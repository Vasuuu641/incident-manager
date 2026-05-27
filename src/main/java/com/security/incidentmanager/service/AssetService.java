package com.security.incidentmanager.service;

import com.security.incidentmanager.domain.Asset;
import com.security.incidentmanager.repository.AssetRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AssetService
        extends AbstractCrudService<Asset, AssetRepository> {

    public AssetService(AssetRepository repository) {
        super(repository);
    }
}