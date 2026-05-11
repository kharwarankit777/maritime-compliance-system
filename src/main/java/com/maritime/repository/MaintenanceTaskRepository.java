package com.maritime.repository;

import com.maritime.enums.TaskStatus;
import com.maritime.model.MaintenanceTask;
import com.maritime.model.Ship;
import com.maritime.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MaintenanceTaskRepository extends JpaRepository<MaintenanceTask, Long> {
    List<MaintenanceTask> findByAssignedTo(User user);
    List<MaintenanceTask> findByShip(Ship ship);
    List<MaintenanceTask> findByStatus(TaskStatus status);
    List<MaintenanceTask> findByDueDateBeforeAndStatusNot(LocalDate date, TaskStatus status);
    long countByStatus(TaskStatus status);
}
