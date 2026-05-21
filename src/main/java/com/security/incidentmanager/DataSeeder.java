package com.security.incidentmanager;

import com.security.incidentmanager.domain.*;
import com.security.incidentmanager.repository.*;
import com.security.incidentmanager.service.SlaPolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final AnalystRepository analystRepository;
    private final TagRepository tagRepository;
    private final IncidentRepository incidentRepository;
    private final AssetRepository assetRepository;
    private final SlaPolicyService slaPolicyService;

    @Override
    public void run(String... args) {

        // ===========================
        // Create SLA Policies
        // ===========================
        SlaPolicy critical = new SlaPolicy();
        critical.setSeverity("CRITICAL");
        critical.setResolutionHours(4);
        critical.setEscalationHours(2);
        slaPolicyService.save(critical);

        SlaPolicy high = new SlaPolicy();
        high.setSeverity("HIGH");
        high.setResolutionHours(24);
        high.setEscalationHours(12);
        slaPolicyService.save(high);

        SlaPolicy medium = new SlaPolicy();
        medium.setSeverity("MEDIUM");
        medium.setResolutionHours(72);
        medium.setEscalationHours(48);
        slaPolicyService.save(medium);

        SlaPolicy low = new SlaPolicy();
        low.setSeverity("LOW");
        low.setResolutionHours(168);
        low.setEscalationHours(120);
        slaPolicyService.save(low);

        // ===========================
        // Create Analysts
        // ===========================
        Analyst alice = new Analyst();
        alice.setName("Alice Chen");
        alice.setEmail("alice@security.com");
        alice.setSpecialization("Network Security");
        analystRepository.save(alice);

        Analyst bob = new Analyst();
        bob.setName("Bob Patel");
        bob.setEmail("bob@security.com");
        bob.setSpecialization("Malware Analysis");
        analystRepository.save(bob);

        Analyst carol = new Analyst();
        carol.setName("Carol Smith");
        carol.setEmail("carol@security.com");
        carol.setSpecialization("Incident Response");
        analystRepository.save(carol);

        // ===========================
        // Create Tags
        // ===========================
        Tag ddos = new Tag();
        ddos.setName("DDoS");
        ddos.setColor("#FF4444");
        tagRepository.save(ddos);

        Tag phishing = new Tag();
        phishing.setName("Phishing");
        phishing.setColor("#FFA500");
        tagRepository.save(phishing);

        Tag ransomware = new Tag();
        ransomware.setName("Ransomware");
        ransomware.setColor("#FF0000");
        tagRepository.save(ransomware);

        Tag bruteForce = new Tag();
        bruteForce.setName("Brute Force");
        bruteForce.setColor("#9B59B6");
        tagRepository.save(bruteForce);

        Tag sqlInjection = new Tag();
        sqlInjection.setName("SQL Injection");
        sqlInjection.setColor("#3498DB");
        tagRepository.save(sqlInjection);

        // ===========================
        // Incident 1 — CRITICAL, already breached
        // ===========================
        IncidentReport report1 = new IncidentReport();
        report1.setFindings("Unusual outbound traffic detected on port 443 " +
                "from finance server. Data exfiltration pattern identified.");
        report1.setRecommendations("Block suspicious IPs immediately. " +
                "Rotate all credentials. Conduct full forensic analysis.");
        report1.setSeverity("CRITICAL");
        report1.setCreatedAt(LocalDateTime.now().minusDays(2));

        Incident incident1 = new Incident();
        incident1.setTitle("Suspicious outbound traffic — Finance server");
        incident1.setDescription("Possible data exfiltration attempt detected " +
                "originating from the finance department server.");
        incident1.setStatus("OPEN");
        incident1.setDetectedAt(LocalDateTime.now().minusHours(6));
        incident1.setAnalyst(alice);
        incident1.setReport(report1);
        incident1.setTags(Set.of(ddos, phishing));
        incident1.setSlaPolicy(critical);
        incident1.setSlaDeadline(
                incident1.getDetectedAt()
                        .plusHours(critical.getResolutionHours())
                        .minusHours(8) // force breach for demo
        );
        incident1.setEscalated(false); // scheduler will pick this up
        incidentRepository.save(incident1);

        Asset asset1 = new Asset();
        asset1.setHostname("finance-server-01");
        asset1.setIpAddress("192.168.1.100");
        asset1.setAssetType("SERVER");
        asset1.setIncident(incident1);
        assetRepository.save(asset1);

        Asset asset2 = new Asset();
        asset2.setHostname("finance-workstation-03");
        asset2.setIpAddress("192.168.1.145");
        asset2.setAssetType("WORKSTATION");
        asset2.setIncident(incident1);
        assetRepository.save(asset2);

        // ===========================
        // Incident 2 — HIGH, approaching deadline
        // ===========================
        IncidentReport report2 = new IncidentReport();
        report2.setFindings("Multiple failed login attempts detected " +
                "against admin accounts from foreign IP addresses.");
        report2.setRecommendations("Enable MFA on all admin accounts. " +
                "Block offending IP ranges. Review access logs.");
        report2.setSeverity("HIGH");
        report2.setCreatedAt(LocalDateTime.now().minusDays(1));

        Incident incident2 = new Incident();
        incident2.setTitle("Brute force attack on admin portal");
        incident2.setDescription("Sustained brute force attack targeting " +
                "administrative login portal over 48 hours.");
        incident2.setStatus("IN_PROGRESS");
        incident2.setDetectedAt(LocalDateTime.now().minusDays(2));
        incident2.setAnalyst(bob);
        incident2.setReport(report2);
        incident2.setTags(Set.of(bruteForce));
        incident2.setSlaPolicy(high);
        incident2.setSlaDeadline(
                incident2.getDetectedAt()
                        .plusHours(high.getResolutionHours())
        );
        incident2.setEscalated(false);
        incidentRepository.save(incident2);

        Asset asset3 = new Asset();
        asset3.setHostname("admin-portal");
        asset3.setIpAddress("10.0.0.1");
        asset3.setAssetType("SERVER");
        asset3.setIncident(incident2);
        assetRepository.save(asset3);

        // ===========================
        // Incident 3 — RESOLVED, within SLA
        // ===========================
        IncidentReport report3 = new IncidentReport();
        report3.setFindings("Phishing email campaign targeting HR department. " +
                "3 employees clicked malicious links.");
        report3.setRecommendations("Mandatory security awareness training. " +
                "Deploy email filtering solution.");
        report3.setSeverity("MEDIUM");
        report3.setCreatedAt(LocalDateTime.now().minusDays(5));

        Incident incident3 = new Incident();
        incident3.setTitle("Phishing campaign targeting HR department");
        incident3.setDescription("Coordinated phishing campaign with " +
                "spoofed HR emails containing malicious attachments.");
        incident3.setStatus("RESOLVED");
        incident3.setDetectedAt(LocalDateTime.now().minusDays(5));
        incident3.setAnalyst(carol);
        incident3.setReport(report3);
        incident3.setTags(Set.of(phishing));
        incident3.setSlaPolicy(medium);
        incident3.setSlaDeadline(
                incident3.getDetectedAt()
                        .plusHours(medium.getResolutionHours())
        );
        incident3.setEscalated(false);
        incidentRepository.save(incident3);

        // ===========================
        // Incident 4 — OPEN, fresh, no analyst
        // ===========================
        Incident incident4 = new Incident();
        incident4.setTitle("SQL injection attempt on customer portal");
        incident4.setDescription("Automated SQL injection probing detected " +
                "against customer-facing web application.");
        incident4.setStatus("OPEN");
        incident4.setDetectedAt(LocalDateTime.now().minusHours(1));
        incident4.setTags(Set.of(sqlInjection));
        incident4.setSlaPolicy(low);
        incident4.setSlaDeadline(
                incident4.getDetectedAt()
                        .plusHours(low.getResolutionHours())
        );
        incident4.setEscalated(false);
        incidentRepository.save(incident4);

        System.out.println("✅ Data seeder completed — " +
                "4 SLA policies, 3 analysts, 5 tags, 4 incidents loaded.");
    }
}