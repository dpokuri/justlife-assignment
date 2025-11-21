package com.justlife.hs.clean.request;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class CreateBookingRequest {

	@JsonIgnore
	private long id;
	
	@Min(value = 1, message = "Customer id value should not be lesaa than 1")
	private long customerId;
	
	@Min(value = 1, message = "Customer id value should not be lesaa than 1")
	private long serviceId;

	@JsonIgnore
	private long vehicleId;
	
	@NotNull
	private LocalDate date;

	@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
	private LocalTime startTime;

	@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
	private LocalTime endTime;
	
	@Min(value = 1, message = "duration should be min 1 hour")
	private int duration;
	
	@Min(value = 1, message = "No of professionals count should be min 1")
	private int profCount;

}
