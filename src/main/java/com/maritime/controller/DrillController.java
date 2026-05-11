package com.maritime.controller;

import com.maritime.dto.DrillDTO;
import com.maritime.model.DrillAttendance;
import com.maritime.model.SafetyDrill;
import com.maritime.service.DrillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drills")
@RequiredArgsConstructor
public class DrillController {
    
    private final DrillService drillService;
    
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<SafetyDrill> createDrill(@RequestBody DrillDTO dto) {
        SafetyDrill drill = drillService.createDrill(dto);
        return ResponseEntity.ok(drill);
    }
    
    @GetMapping
    public ResponseEntity<List<SafetyDrill>> getAllDrills() {
        List<SafetyDrill> drills = drillService.getAllDrills();
        return ResponseEntity.ok(drills);
    }
    
    @GetMapping("/upcoming")
    public ResponseEntity<List<SafetyDrill>> getUpcomingDrills() {
        List<SafetyDrill> drills = drillService.getUpcomingDrills();
        return ResponseEntity.ok(drills);
    }
    
    @GetMapping("/ship/{shipId}")
    public ResponseEntity<List<SafetyDrill>> getDrillsByShip(@PathVariable Long shipId) {
        List<SafetyDrill> drills = drillService.getDrillsByShip(shipId);
        return ResponseEntity.ok(drills);
    }
    
    @PostMapping("/{id}/attend")
    public ResponseEntity<DrillAttendance> markAttendance(@PathVariable Long id, Authentication authentication) {
        DrillAttendance attendance = drillService.markAttendance(id, authentication.getName());
        return ResponseEntity.ok(attendance);
    }
}
