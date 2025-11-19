package com.justlife.hs.clean.response;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.justlife.hs.clean.model.ProfInfo;

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
public class BookingProfInfo {
	
	private long bookindId;
	private LocalDate date;
	private LocalTime startTime;
	private LocalTime endTime;
	private int totalNoofProfs;
	private int duration;
	private long serviceId;
	private long vehicleId;
	
	private List<ProfInfo> profs;
	

}
