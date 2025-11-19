package com.justlife.hs.clean.model;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Schedule {
	
	private long slotId;
	private long profId;
	private long vehicleId;
	private int serviceId;
	private LocalDate date;
	private LocalTime startTime;
	private LocalTime endTime;

}
