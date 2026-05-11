package com.maritime.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComplianceDTO {
    private double maintenanceCompliancePercent;
    private double drillCompliancePercent;
    private double overallCompliancePercent;
    private long totalTasks;
    private long completedTasks;
    private long overdueTasks;
    private long totalDrills;
    private long attendedDrills;
    private long missedDrills;
	public double getMaintenanceCompliancePercent() {
		return maintenanceCompliancePercent;
	}
	public void setMaintenanceCompliancePercent(double maintenanceCompliancePercent) {
		this.maintenanceCompliancePercent = maintenanceCompliancePercent;
	}
	public double getDrillCompliancePercent() {
		return drillCompliancePercent;
	}
	public void setDrillCompliancePercent(double drillCompliancePercent) {
		this.drillCompliancePercent = drillCompliancePercent;
	}
	public double getOverallCompliancePercent() {
		return overallCompliancePercent;
	}
	public void setOverallCompliancePercent(double overallCompliancePercent) {
		this.overallCompliancePercent = overallCompliancePercent;
	}
	public long getTotalTasks() {
		return totalTasks;
	}
	public void setTotalTasks(long totalTasks) {
		this.totalTasks = totalTasks;
	}
	public long getCompletedTasks() {
		return completedTasks;
	}
	public void setCompletedTasks(long completedTasks) {
		this.completedTasks = completedTasks;
	}
	public long getOverdueTasks() {
		return overdueTasks;
	}
	public void setOverdueTasks(long overdueTasks) {
		this.overdueTasks = overdueTasks;
	}
	public long getTotalDrills() {
		return totalDrills;
	}
	public void setTotalDrills(long totalDrills) {
		this.totalDrills = totalDrills;
	}
	public long getAttendedDrills() {
		return attendedDrills;
	}
	public void setAttendedDrills(long attendedDrills) {
		this.attendedDrills = attendedDrills;
	}
	public long getMissedDrills() {
		return missedDrills;
	}
	public void setMissedDrills(long missedDrills) {
		this.missedDrills = missedDrills;
	}
    
    
    
}
