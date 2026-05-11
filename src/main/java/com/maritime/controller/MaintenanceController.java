package com.maritime.controller;

import com.maritime.dto.MaintenanceTaskDTO;
import com.maritime.dto.TaskUpdateDTO;
import com.maritime.model.MaintenanceTask;
import com.maritime.service.MaintenanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/maintenance")
@RequiredArgsConstructor
public class MaintenanceController {

    private final MaintenanceService maintenanceService;

    @PostMapping("/tasks")
    public ResponseEntity<?> createTask(@RequestBody MaintenanceTaskDTO dto, Authentication authentication) {
        if (authentication.getAuthorities().stream()
                .noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return ResponseEntity.status(403).body("Access denied: ADMIN role required");
        }
        MaintenanceTask task = maintenanceService.createTask(dto);
        return ResponseEntity.ok(task);
    }

    @GetMapping("/tasks")
    public ResponseEntity<?> getAllTasks(Authentication authentication) {
        if (authentication.getAuthorities().stream()
                .noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return ResponseEntity.status(403).body("Access denied: ADMIN role required");
        }
        List<MaintenanceTask> tasks = maintenanceService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/tasks/my")
    public ResponseEntity<List<MaintenanceTask>> getMyTasks(Authentication authentication) {
        List<MaintenanceTask> tasks = maintenanceService.getTasksByUser(authentication.getName());
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/tasks/ship/{shipId}")
    public ResponseEntity<List<MaintenanceTask>> getTasksByShip(@PathVariable Long shipId) {
        List<MaintenanceTask> tasks = maintenanceService.getTasksByShip(shipId);
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<MaintenanceTask> updateTask(@PathVariable Long id, @RequestBody TaskUpdateDTO dto) {
        MaintenanceTask task = maintenanceService.updateTask(id, dto);
        return ResponseEntity.ok(task);
    }

    @GetMapping("/tasks/overdue")
    public ResponseEntity<List<MaintenanceTask>> getOverdueTasks() {
        List<MaintenanceTask> tasks = maintenanceService.getOverdueTasks();
        return ResponseEntity.ok(tasks);
    }
}