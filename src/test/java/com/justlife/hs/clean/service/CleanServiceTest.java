package com.justlife.hs.clean.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.justlife.hs.clean.dao.CleanDao;
import com.justlife.hs.clean.model.Schedule;
import com.justlife.hs.clean.service.CleanService;

@ExtendWith(MockitoExtension.class)
public class CleanServiceTest {

	@Mock
	private CleanDao cleanDao;

	@InjectMocks
	private CleanService cleanService;

	@Test
	void getAvailabilitySlots() {

		Schedule schedule1 = new Schedule(1, 10, 2, 2, LocalDate.now(), LocalTime.of(10, 0),
				LocalTime.of(14, 0).plusHours(4));
		Schedule schedule2 = new Schedule(2, 13, 2, 2, LocalDate.now(), LocalTime.of(10, 0),
				LocalTime.of(14, 0).plusHours(4));
		List<Schedule> avlList = List.of(schedule1, schedule2);
		Mockito.when(
				cleanDao.getAvailability(1, LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(10, 0).plusHours(4)))
				.thenReturn(avlList);
		List<Schedule> result = cleanService.getAvailability(1, LocalDate.now(), LocalTime.of(10, 0), 4);

		assertThat(result).isNotNull();
		assertThat(result.size()).isEqualTo(2);

	}

}
