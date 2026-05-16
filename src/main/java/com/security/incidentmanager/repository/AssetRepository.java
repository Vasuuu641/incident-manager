package com.security.incidentmanager.repository;

import com.security.incidentmanager.domain.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {
    List<Asset> findByAssetType(String assetType);
    List<Asset> findByIncidentId(Long incidentId);
}
