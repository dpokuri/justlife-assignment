package com.justlife.hs.clean.response;

import java.util.List;

import com.justlife.hs.clean.model.Schedule;

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
public class AvailabilityResp {
	
	private Status status;
	private List<Schedule> data;

}
