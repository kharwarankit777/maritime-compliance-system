package com.maritime.repository;

import com.maritime.model.SafetyDrill;
import com.maritime.model.Ship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SafetyDrillRepository extends JpaRepository<SafetyDrill, Long> {
    List<SafetyDrill> findByShip(Ship ship);
    List<SafetyDrill> findByScheduledDateAfter(LocalDate date);
}
