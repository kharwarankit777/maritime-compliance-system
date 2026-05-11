package com.maritime.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceTaskDTO {
    private String title;
    private String description;
    private Long shipId;
    private Long assignedToUserId;
    private LocalDate dueDate;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getShipId() {
		return shipId;
	}
	public void setShipId(Long shipId) {
		this.shipId = shipId;
	}
	public Long getAssignedToUserId() {
		return assignedToUserId;
	}
	public void setAssignedToUserId(Long assignedToUserId) {
		this.assignedToUserId = assignedToUserId;
	}
	public LocalDate getDueDate() {
		return dueDate;
	}
	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}
    
    
    
    
}
