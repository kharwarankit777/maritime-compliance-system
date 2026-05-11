package com.maritime.repository;

import com.maritime.model.DrillAttendance;
import com.maritime.model.SafetyDrill;
import com.maritime.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DrillAttendanceRepository extends JpaRepository<DrillAttendance, Long> {
    List<DrillAttendance> findByUser(User user);
    List<DrillAttendance> findByDrill(SafetyDrill drill);
    Optional<DrillAttendance> findByDrillAndUser(SafetyDrill drill, User user);
    long countByAttendedTrue();
    List<DrillAttendance> findByAttendedFalseAndDrill_ScheduledDateBefore(LocalDate date);
}
