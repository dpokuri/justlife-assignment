package com.justlife.hs.clean.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.justlife.hs.clean.dao.CleanDao;
import com.justlife.hs.clean.model.Schedule;

@ExtendWith(MockitoExtension.class)
public class CleanDaoTest {

	@Mock
	private NamedParameterJdbcTemplate npjt;

	@InjectMocks
	private CleanDao cleanDao;

	@Test
	void getAvailabilitySlots() {

		Schedule schedule1 = new Schedule(1, 10, 2, 2, LocalDate.now(), LocalTime.of(10, 0),
				LocalTime.of(14, 0).plusHours(4));
		Schedule schedule2 = new Schedule(2, 13, 2, 2, LocalDate.now(), LocalTime.of(10, 0),
				LocalTime.of(10, 0).plusHours(4));
		List<Schedule> avlList = List.of(schedule1, schedule2);
		
		final Map<String, Object> params = Map.of("serviceId", 1, "date", LocalDate.now(), "startTime", LocalTime.of(10, 0),
				"endTime", LocalTime.of(10, 0).plusHours(4));
		Mockito.when(npjt.query(any(), any(Map.class), any(RowMapper.class))).thenReturn(avlList);
		List<Schedule> result = cleanDao.getAvailability(1, LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(10, 0).plusHours(4));

		assertThat(result).isNotNull();
		assertThat(result.size()).isEqualTo(2);

	}

}
