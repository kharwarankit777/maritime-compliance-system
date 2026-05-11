package com.maritime.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DrillDTO {
    private String title;
    private String drillType;
    private Long shipId;
    private LocalDate scheduledDate;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDrillType() {
		return drillType;
	}
	public void setDrillType(String drillType) {
		this.drillType = drillType;
	}
	public Long getShipId() {
		return shipId;
	}
	public void setShipId(Long shipId) {
		this.shipId = shipId;
	}
	public LocalDate getScheduledDate() {
		return scheduledDate;
	}
	public void setScheduledDate(LocalDate scheduledDate) {
		this.scheduledDate = scheduledDate;
	}
    
    
}
