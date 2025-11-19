package com.justlife.hs.clean.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.justlife.hs.clean.model.Schedule;
import com.justlife.hs.clean.request.CreateBookingRequest;
import com.justlife.hs.clean.request.UpdateBookingRequest;
import com.justlife.hs.clean.response.AvailabilityResp;
import com.justlife.hs.clean.response.BookingProfInfo;
import com.justlife.hs.clean.response.BookingResp;
import com.justlife.hs.clean.response.ScheduleResp;
import com.justlife.hs.clean.response.Status;
import com.justlife.hs.clean.service.CleanService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("cleaning")
public class CleanController {

	private final CleanService cleanService;

	@GetMapping("/availability")
	public ResponseEntity<AvailabilityResp> getAvailability(
			@RequestParam(value = "serviceId", required = true) int serviceId,
			@RequestParam(value = "date", required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
			@RequestParam(value = "startTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
			@RequestParam(value = "duration", required = false) int duration) {

		AvailabilityResp response = null;
		List<Schedule> data = null;
		Status status = null;
		if (null != date) {
			data = cleanService.getAvailability(serviceId, date, startTime, duration);
			status = Status.builder().code("200").type("SUCCESS").message("AVAILABILITY_RETIEVED")
					.description("Professionals availability details are retrieved").build();
			response = AvailabilityResp.builder().data(data).status(status).build();
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		status = Status.builder().code("400").type("ERROR").message("INVALID_INPUT")
				.description("INVALID_INPUT_REQUEST").build();
		response = AvailabilityResp.builder().status(status).build();
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

	}
	
	
	@PostMapping("/bookings")
	public ResponseEntity<BookingResp> appointmentBooking(@RequestBody CreateBookingRequest req) {

		BookingResp response = null;
		BookingProfInfo data = null;
		Status status = null;
		
			data = cleanService.createAppointment(req);
			if (null != data) {
				status = Status.builder().code("201").type("SUCCESS").message("APPOINT_CONFIRMED")
						.description("Appointment is booked successfully").build();
				response = BookingResp.builder().data(data).status(status).build();
				return new ResponseEntity<>(response, HttpStatus.CREATED);
			}

		status = Status.builder().code("400").type("ERROR").message("INVALID_INPUT")
				.description("Don't have available slots to meet the requirements").build();
		response = BookingResp.builder().status(status).build();
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

	}
	
	
	@PutMapping("/bookings")
	public ResponseEntity<BookingResp> updateBooking(@RequestBody UpdateBookingRequest req) {

		BookingResp response = null;
		BookingProfInfo data = null;
		Status status = null;
		if (null != req) {
			data = cleanService.updateBooking(req);
			status = Status.builder().code("200").type("SUCCESS").message("BOOKING_UPDATED")
					.description("Booking is updated successfully").build();
			response = BookingResp.builder().data(data).status(status).build();
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		status = Status.builder().code("204").type("ERROR").message("INVALID_INPUT")
				.description("There are no bookings available with the given booking id").build();
		response = BookingResp.builder().status(status).build();
		return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);

	}
	
	
	@PostMapping("/prof-schedule")
	public ResponseEntity<ScheduleResp> createProfSchedules(@RequestBody LocalDate date) {

		ScheduleResp response = null;
		String data = null;
		Status status = null;

		data = cleanService.createProfSchedules(date);
		if (data.contentEquals("SCHEDULE_GENERATED")) {
			status = Status.builder().code("201").type("SUCCESS").message("SCHEULES_GENERATED")
					.description("Schedules generated successfully").build();
			response = ScheduleResp.builder().data(data).status(status).build();
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		status = Status.builder().code("400").type("ERROR").message("INVALID_INPUT")
				.description("There is an issue while generating the schedules").build();
		response = ScheduleResp.builder().status(status).build();
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

	}
	
	
	
	
	
	
	

}
