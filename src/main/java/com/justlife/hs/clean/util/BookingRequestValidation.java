package com.justlife.hs.clean.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class BookingRequestValidation {
	
	public static String validateBookingReuest(LocalDate date, LocalTime startTime, int duration) {
		String validationMsg = null;
		if(date.getDayOfWeek() == DayOfWeek.FRIDAY) {
			validationMsg = "Given date id Firday hence no bookings allowed";
		}
		if (startTime.isBefore(LocalTime.of(8, 0))
				|| (startTime.plusHours(duration).isBefore(LocalTime.of(22, 0))
						|| startTime.plusHours(duration).compareTo(LocalTime.of(22, 0)) == 0)) {
			
			validationMsg =  "Given start time is fall into the non working hours";

		}
		return validationMsg;
		
	}
	
	public static String validateBookingReuest(LocalDate date, LocalTime startTime) {
		String validationMsg = null;
		if(date.getDayOfWeek() == DayOfWeek.FRIDAY) {
			validationMsg = "Given date id Firday hence no bookings allowed";
		}
		if (startTime.isBefore(LocalTime.of(8, 0))
				|| startTime.isAfter(LocalTime.of(20, 0))) {
			
			validationMsg =  "Given start time is fall into the non working hours";

		}
		return validationMsg;
		
	}

	public static String validateBookingReuest(LocalDate date) {
		if (date.getDayOfWeek() == DayOfWeek.FRIDAY) {
			return "Given date is Firday hence no schedules allowed";
		}
		return null;
	}

}
