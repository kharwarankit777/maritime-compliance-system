package com.maritime.service;

import com.maritime.dto.DrillDTO;
import com.maritime.model.DrillAttendance;
import com.maritime.model.SafetyDrill;
import com.maritime.model.Ship;
import com.maritime.model.User;
import com.maritime.repository.DrillAttendanceRepository;
import com.maritime.repository.SafetyDrillRepository;
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
public class DrillService {
    
    private final SafetyDrillRepository safetyDrillRepository;
    private final DrillAttendanceRepository drillAttendanceRepository;
    private final ShipRepository shipRepository;
    private final UserRepository userRepository;
    
    public SafetyDrill createDrill(DrillDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User createdBy = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Ship ship = shipRepository.findById(dto.getShipId())
                .orElseThrow(() -> new RuntimeException("Ship not found"));
        
        SafetyDrill drill = SafetyDrill.builder()
                .title(dto.getTitle())
                .drillType(dto.getDrillType())
                .ship(ship)
                .scheduledDate(dto.getScheduledDate())
                .createdBy(createdBy)
                .build();
        
        return safetyDrillRepository.save(drill);
    }
    
    public List<SafetyDrill> getAllDrills() {
        return safetyDrillRepository.findAll();
    }
    
    public List<SafetyDrill> getUpcomingDrills() {
        LocalDate today = LocalDate.now();
        return safetyDrillRepository.findByScheduledDateAfter(today.minusDays(1));
    }
    
    public List<SafetyDrill> getDrillsByShip(Long shipId) {
        Ship ship = shipRepository.findById(shipId)
                .orElseThrow(() -> new RuntimeException("Ship not found"));
        return safetyDrillRepository.findByShip(ship);
    }
    
    public DrillAttendance markAttendance(Long drillId, String email) {
        SafetyDrill drill = safetyDrillRepository.findById(drillId)
                .orElseThrow(() -> new RuntimeException("Drill not found"));
        
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        DrillAttendance attendance = drillAttendanceRepository.findByDrillAndUser(drill, user)
                .orElse(DrillAttendance.builder()
                        .drill(drill)
                        .user(user)
                        .build());
        
        attendance.setAttended(true);
        attendance.setSubmittedAt(LocalDateTime.now());
        
        return drillAttendanceRepository.save(attendance);
    }
}
