package com.justlife.hs.clean.controller;

import java.time.Instant;
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
import com.justlife.hs.clean.response.TestResponse;
import com.justlife.hs.clean.service.CleanService;
import com.justlife.hs.clean.util.ErrorCodes;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
			@RequestParam(value = "serviceId", required = true) @Min(value = 1) int serviceId,
			@RequestParam(value = "date", required = true) @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
			@RequestParam(value = "startTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
			@RequestParam(value = "duration", required = false, defaultValue = "0") int duration) {

		AvailabilityResp response = null;
		List<Schedule> data = null;
		Status status = null;

		data = cleanService.getAvailability(serviceId, date, startTime, duration);
		if (null != data) {
			status = Status.builder().code("200").type("SUCCESS").message("DATA_FETCHED").description(ErrorCodes.SCHEDULE_FETCHED_SUCCESS)
					.timestamp(Instant.now()).build();
			response = AvailabilityResp.builder().data(data).status(status).build();
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		status = Status.builder().code("400").type("ERROR").message("INVALID_INPUT").description(ErrorCodes.SCHEDULE_FETCHED_FAILURE)
				.timestamp(Instant.now()).build();
		response = AvailabilityResp.builder().status(status).build();
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

	}

	@PostMapping("/bookings")
	public ResponseEntity<BookingResp> appointmentBooking(@RequestBody @NotNull CreateBookingRequest req) {

		BookingResp response = null;
		BookingProfInfo data = null;
		Status status = null;

		data = cleanService.createAppointment(req);
		if (null != data) {
			status = Status.builder().code("201").type("SUCCESS").message("APPOINT_CONFIRMED")
					.description(ErrorCodes.APPOINTMENT_BOOKING_SUCCESS).timestamp(Instant.now()).build();
			response = BookingResp.builder().data(data).status(status).build();
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		}

		status = Status.builder().code("400").type("ERROR").message("INVALID_INPUT")
				.description(ErrorCodes.APPOINTMENT_BOOKING_FAILURE).timestamp(Instant.now()).build();
		response = BookingResp.builder().status(status).build();
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

	}

	@PutMapping("/bookings")
	public ResponseEntity<BookingResp> updateBooking(@RequestBody @NotNull UpdateBookingRequest req) {

		BookingResp response = null;
		BookingProfInfo data = null;
		Status status = null;

		data = cleanService.updateBooking(req);
		if (null != data) {
			status = Status.builder().code("200").type("SUCCESS").message("BOOKING_UPDATED")
					.description(ErrorCodes.BOOKING_UPDATE_SUCCESS).timestamp(Instant.now()).build();
			response = BookingResp.builder().data(data).status(status).build();
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		status = Status.builder().code("204").type("ERROR").message("INVALID_INPUT")
				.description(ErrorCodes.BOOKING_UPDATE_FAILURE).timestamp(Instant.now()).build();
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
					.description(ErrorCodes.SCHEDULE_GENERATION_SUCCESS).timestamp(Instant.now()).build();
			response = ScheduleResp.builder().data(data).status(status).build();
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		status = Status.builder().code("400").type("ERROR").message("INVALID_INPUT")
				.description(ErrorCodes.SCHEDULE_GENERATION_FAILURE).timestamp(Instant.now()).build();
		response = ScheduleResp.builder().status(status).build();
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

	}
	
	

}
