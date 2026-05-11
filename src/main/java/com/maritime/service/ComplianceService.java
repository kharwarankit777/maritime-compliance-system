package com.maritime.service;

import com.maritime.dto.ComplianceDTO;
import com.maritime.enums.TaskStatus;
import com.maritime.repository.DrillAttendanceRepository;
import com.maritime.repository.MaintenanceTaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ComplianceService {
    
    private final MaintenanceTaskRepository maintenanceTaskRepository;
    private final DrillAttendanceRepository drillAttendanceRepository;
    
    public ComplianceDTO getComplianceDashboard() {
        // Task metrics
        long totalTasks = maintenanceTaskRepository.count();
        long completedTasks = maintenanceTaskRepository.countByStatus(TaskStatus.COMPLETED);
        LocalDate today = LocalDate.now();
        long overdueTasks = maintenanceTaskRepository.findByDueDateBeforeAndStatusNot(today, TaskStatus.COMPLETED).size();
        
        // Calculate maintenance compliance percentage
        double maintenanceCompliancePercent = 0.0;
        if (totalTasks > 0) {
            maintenanceCompliancePercent = ((double) completedTasks / totalTasks) * 100;
        }
        
        // Drill metrics
        long totalDrills = drillAttendanceRepository.count();
        long attendedDrills = drillAttendanceRepository.countByAttendedTrue();
        long missedDrills = drillAttendanceRepository.findByAttendedFalseAndDrill_ScheduledDateBefore(today).size();
        
        // Calculate drill compliance percentage
        double drillCompliancePercent = 0.0;
        if (totalDrills > 0) {
            drillCompliancePercent = ((double) attendedDrills / totalDrills) * 100;
        }
        
        // Calculate overall compliance percentage (average of both)
        double overallCompliancePercent = (maintenanceCompliancePercent + drillCompliancePercent) / 2;
        
        return ComplianceDTO.builder()
                .maintenanceCompliancePercent(maintenanceCompliancePercent)
                .drillCompliancePercent(drillCompliancePercent)
                .overallCompliancePercent(overallCompliancePercent)
                .totalTasks(totalTasks)
                .completedTasks(completedTasks)
                .overdueTasks(overdueTasks)
                .totalDrills(totalDrills)
                .attendedDrills(attendedDrills)
                .missedDrills(missedDrills)
                .build();
    }
}
