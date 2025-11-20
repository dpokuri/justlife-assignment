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
public class BookingInfo {
	
	private long id;
	private long customerId;
	private int serviceId;
	private long vehicle_id;
	private LocalDate date;
	private LocalTime startTime;
	private LocalTime endTime;
	private int duration;
	private int profCount;
	private long profId;
}
