package com.justlife.hs.clean.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.justlife.hs.clean.dao.CleanDao;
import com.justlife.hs.clean.model.BookingInfo;
import com.justlife.hs.clean.model.ProfInfo;
import com.justlife.hs.clean.model.Schedule;
import com.justlife.hs.clean.request.CreateBookingRequest;
import com.justlife.hs.clean.request.ProfSlot;
import com.justlife.hs.clean.request.UpdateBookingRequest;
import com.justlife.hs.clean.response.BookingProfInfo;
import com.justlife.hs.clean.util.Slot;
import com.justlife.hs.clean.util.TimeSlotCalculator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CleanService {
	
	
	private final CleanDao cleanDao;
	private final TimeSlotCalculator slotCalcator;
	

	
	public List<Schedule> getAvailability(int serviceId, LocalDate date, LocalTime startTime, int duration) {
		List<Schedule> avls = null;

		if (null == startTime) {
			avls = cleanDao.getAvailability(serviceId, date);
		} else {
			LocalTime endTime = startTime.plusHours(duration);
			avls = cleanDao.getAvailability(serviceId, date, startTime, endTime);
		}

		return avls;
	}

	
	
	@Transactional
	public BookingProfInfo createAppointment(CreateBookingRequest req) {

		List<ProfSlot> avlProfSlots = cleanDao.getAvailableProfs(req);
		if (!avlProfSlots.isEmpty()) {
			req.setVehicleId(avlProfSlots.get(0).getVehicleId());
			long bookingId = cleanDao.storeBookingInfo(req);

			cleanDao.storeBookingProfMap(bookingId, avlProfSlots);

			List<Slot> bookedSlots = avlProfSlots.stream()
					.map(e -> new Slot(e.getSlotId(), req.getDate(), req.getStartTime(), req.getEndTime())).collect(Collectors.toList());

			List<ProfInfo> profs = avlProfSlots.stream().map(
					e -> new ProfInfo(e.getProfId(), e.getFirstName(), e.getLastName(), e.getPhone(), e.getEmail()))
					.collect(Collectors.toList());

			cleanDao.updateBookedSlots(bookedSlots);
			List<ProfSlot> derivedSlots = slotCalcator.deriveFreeSlots(req, avlProfSlots);
			cleanDao.updateProfsAvailability(derivedSlots);

			BookingProfInfo bookingInfo = BookingProfInfo.builder().bookindId(bookingId).date(req.getDate())
					.startTime(req.getStartTime()).endTime(req.getEndTime()).serviceId(req.getServiceId())
					.vehicleId(avlProfSlots.get(0).getVehicleId()).profs(profs).build();

			return bookingInfo;

		}

		return null;

	}
		
		

	public BookingProfInfo updateBooking(UpdateBookingRequest req) {

		List<BookingInfo> bookInfoList = cleanDao.getBookingInfo(req.getBookingId());
//
//		// If only in start time
//		if (!bookInfoList.isEmpty() && req.getDate().isEqual(bookInfoList.get(0).getDate())) {
//			BookingInfo bookInfo = bookInfoList.get(0);
//			LocalTime currentEndTime = req.getStartTime().plus(bookInfo.getDuration(), ChronoUnit.DAYS);
//
//			List<Long> profIds = bookInfoList.stream().map(bi -> bi.getId()).collect(Collectors.toList());
//
//			List<Schedule> slots = cleanDao.getMergableSlots(bookInfo.getDate(), bookInfo.getServiceId(), profIds);
//			if (!slots.isEmpty()) {
//
//				Map<Long, List<Schedule>> byProfId = slots.stream().collect(Collectors.groupingBy(i -> i.getProfId()));
//				
//				Schedule bookedSlot = Schedule.builder().
//				
//				for (Map.Entry<Long, List<Schedule>> entry : byProfId.entrySet()) {
//					
//					
//					
//					
//		            String name = entry.getKey();
//		            Integer score = entry.getValue();
//		            System.out.println(name + ": " + score);
//		        }
//				
//				
//				
//				
//				
//				
//
//			}
//
//			if (req.getStartTime().isBefore(bookInfo.getStartTime())) {
//
//				// Will have to get before availabilities for the same professional slots
//				return null;
//
//			} else if (req.getStartTime().isAfter(bookInfo.getStartTime())) {
//
//				// Will have to get after availabilities for the same professional slots
//				return null;
//
//			} else {
//
//				CreateBookingRequest createReq = CreateBookingRequest.builder().customerId(bookInfo.getCustomerId())
//						.date(bookInfo.getDate()).serviceId(bookInfo.getServiceId()).startTime(req.getStartTime())
//						.duration(bookInfo.getDuration()).profCount(bookInfo.getProfCount()).build();
//
//				return createAppointment(createReq);
//			}
//
//		} else {
		BookingInfo bookInfo = bookInfoList.get(0);
		CreateBookingRequest createReq = CreateBookingRequest.builder().customerId(bookInfo.getCustomerId())
				.date(bookInfo.getDate()).serviceId(bookInfo.getServiceId()).startTime(req.getStartTime())
				.duration(bookInfo.getDuration()).profCount(bookInfo.getProfCount()).build();

		return createAppointment(createReq);

//		}

	}
	
	public String createProfSchedules(LocalDate date) {

		List<Long> vehicles = cleanDao.getAvailableVehicles(date.plusDays(1));
		List<Long> profs = cleanDao.getAvailableProfs(date.plusDays(1));

		if (!vehicles.isEmpty() && !profs.isEmpty()) {
			List<Schedule> avls = new ArrayList<>();
			int k = 0;
			for (Long vehicle : vehicles) {
				int counter = 0;
				for (int i = k; profs.size() > i; i++) {
					Schedule avl = Schedule.builder().profId(profs.get(i)).vehicleId(vehicle).serviceId(1)
							.date(LocalDate.now()).startTime(LocalTime.of(8, 0)).endTime(LocalTime.of(22, 0)).build();
					avls.add(avl);
					k++;
					counter++;
					if (counter == 5) {
						counter = 0;
						break;
					}
				}
			}

			if (!avls.isEmpty()) {
				cleanDao.storeSchedules(avls);
			}

			return "SCHEDULE_GENERATED";
		}
		return "ERROR";
	}
	
	
	private List<Schedule> mergeslots(List<Schedule> slots) {
		slots.sort(Comparator.comparing(i -> i.getStartTime()));
		List<Schedule> merged = new ArrayList<>();
		Schedule current = slots.get(0);

		for (int i = 1; i < slots.size(); i++) {
			Schedule next = slots.get(i);

			// Overlap OR continuous
			if (!next.getStartTime().isAfter(current.getEndTime())) {
				LocalTime end = current.getEndTime().isAfter(next.getEndTime()) ? current.getEndTime()
						: next.getEndTime();
				current.setEndTime(end);
			} else {
				merged.add(current);
				current = next;
			}
		}

		merged.add(current);
		return merged;

	}

}
