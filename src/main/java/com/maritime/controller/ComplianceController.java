package com.maritime.controller;

import com.maritime.dto.ComplianceDTO;
import com.maritime.service.ComplianceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/compliance")
@RequiredArgsConstructor
public class ComplianceController {
    
    private final ComplianceService complianceService;
    
    @GetMapping("/dashboard")
    public ResponseEntity<ComplianceDTO> getComplianceDashboard() {
        ComplianceDTO dashboard = complianceService.getComplianceDashboard();
        return ResponseEntity.ok(dashboard);
    }
}
