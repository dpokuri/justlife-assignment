package com.justlife.hs.clean.request;

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
public class ProfSlot {

	private long profId;
	private String firstName;
	private String lastName;
	private String phone;
	private String email;
	private long slotId;
	private LocalDate date;
	private LocalTime startTime;
	private LocalTime endTime;
	private long vehicleId;
	private long serviceId;
}
