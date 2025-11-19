package com.justlife.hs.clean.util;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.justlife.hs.clean.request.CreateBookingRequest;
import com.justlife.hs.clean.request.ProfSlot;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TimeSlotCalculator {


	public List<ProfSlot> deriveFreeSlots(CreateBookingRequest req, List<ProfSlot> profSlots) {

		ExecutorService executor = Executors.newFixedThreadPool(5);

		List<CompletableFuture<List<ProfSlot>>> futures = profSlots.stream()
				.map(item -> CompletableFuture.supplyAsync(() -> subtractSlots(item, req), executor)).toList();

		// Combine all results into one list
		List<List<ProfSlot>> results = futures.stream().map(CompletableFuture::join).collect(Collectors.toList());

		List<ProfSlot> newAvlSlots = results.stream().flatMap(List::stream).collect(Collectors.toList());

		executor.shutdown();

		return newAvlSlots;

	}
	

	public static List<ProfSlot> subtractSlots(ProfSlot available, CreateBookingRequest booked) {
		List<ProfSlot> freeSlots = new ArrayList<>();

		LocalTime currentStart = available.getStartTime();

		// If booked slot starts after current start and overlaps inside available
		// window
		if (booked.getStartTime().isAfter(currentStart)) {
			ProfSlot newSlot = ProfSlot.builder().profId(available.getProfId()).serviceId(available.getServiceId())
					.vehicleId(available.getVehicleId()).date(available.getDate()).startTime(currentStart).endTime(booked.getStartTime()).build();

			freeSlots.add(newSlot);
		}

		// Move currentStart forward
		if (booked.getEndTime().isAfter(currentStart)) {
			currentStart = booked.getEndTime();
		}

		// Remaining time after last booked slot
		if (currentStart.isBefore(available.getEndTime())) {
			ProfSlot newSlot = ProfSlot.builder().profId(available.getProfId()).serviceId(available.getServiceId())
					.vehicleId(available.getVehicleId()).date(available.getDate()).startTime(currentStart).endTime(available.getEndTime())
					.build();
			freeSlots.add(newSlot);
		}

		return freeSlots;
	}
	
	
	

}
