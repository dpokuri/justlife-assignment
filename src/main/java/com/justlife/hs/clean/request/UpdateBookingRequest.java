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
public class UpdateBookingRequest {
	
	private long bookingId;
	private LocalDate date;
	private LocalTime startTime;
//	
//	@JsonIgnore
//	private LocalTime endTime;

}
