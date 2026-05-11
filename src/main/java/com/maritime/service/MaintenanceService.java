package com.maritime.service;

import com.maritime.dto.MaintenanceTaskDTO;
import com.maritime.dto.TaskUpdateDTO;
import com.maritime.enums.TaskStatus;
import com.maritime.model.MaintenanceTask;
import com.maritime.model.Ship;
import com.maritime.model.User;
import com.maritime.repository.MaintenanceTaskRepository;
import com.maritime.repository.ShipRepository;
import com.maritime.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MaintenanceService {
    
    private final MaintenanceTaskRepository maintenanceTaskRepository;
    private final ShipRepository shipRepository;
    private final UserRepository userRepository;
    
    public MaintenanceTask createTask(MaintenanceTaskDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User createdBy = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Ship ship = shipRepository.findById(dto.getShipId())
                .orElseThrow(() -> new RuntimeException("Ship not found"));
        
        User assignedTo = null;
        if (dto.getAssignedToUserId() != null) {
            assignedTo = userRepository.findById(dto.getAssignedToUserId())
                    .orElseThrow(() -> new RuntimeException("Assigned user not found"));
        }
        
        MaintenanceTask task = MaintenanceTask.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .ship(ship)
                .assignedTo(assignedTo)
                .createdBy(createdBy)
                .dueDate(dto.getDueDate())
                .build();
        
        return maintenanceTaskRepository.save(task);
    }
    
    public List<MaintenanceTask> getAllTasks() {
        return maintenanceTaskRepository.findAll();
    }
    
    public List<MaintenanceTask> getTasksByUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return maintenanceTaskRepository.findByAssignedTo(user);
    }
    
    public List<MaintenanceTask> getTasksByShip(Long shipId) {
        Ship ship = shipRepository.findById(shipId)
                .orElseThrow(() -> new RuntimeException("Ship not found"));
        return maintenanceTaskRepository.findByShip(ship);
    }
    
    public MaintenanceTask updateTask(Long id, TaskUpdateDTO dto) {
        MaintenanceTask task = maintenanceTaskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        
        task.setStatus(dto.getStatus());
        task.setNotes(dto.getNotes());
        
        if (dto.getStatus() == TaskStatus.COMPLETED) {
            task.setCompletedAt(LocalDateTime.now());
        }
        
        return maintenanceTaskRepository.save(task);
    }
    
    public List<MaintenanceTask> getOverdueTasks() {
        LocalDate today = LocalDate.now();
        return maintenanceTaskRepository.findByDueDateBeforeAndStatusNot(today, TaskStatus.COMPLETED);
    }
}
