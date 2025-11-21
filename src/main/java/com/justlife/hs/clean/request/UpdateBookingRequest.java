package com.justlife.hs.clean.request;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
	
	@Min(value = 1, message = "booking id value should not be lesaa than 1")
	private long bookingId;
	
	@NotNull
	private LocalDate date;
	
	@NotNull
	private LocalTime startTime;


}
