package com.justlife.hs.clean.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.justlife.hs.clean.HomeServiceApplication;
import com.justlife.hs.clean.model.Schedule;
import com.justlife.hs.clean.service.CleanService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CleanController.class)
@Import(HomeServiceApplication.class)
public class CleanControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CleanService cleanService;

	@Autowired
	private ObjectMapper objectMapper;

	@InjectMocks
	private CleanController cleanController;

	List<Schedule> avlList;

	@BeforeEach
	void setup() {
		Schedule schedule1 = new Schedule(1, 10, 2, 2, LocalDate.of(2025, 11, 18), LocalTime.of(10, 0),
				LocalTime.of(14, 0).plusHours(4));
		Schedule schedule2 = new Schedule(2, 13, 2, 2, LocalDate.of(2025, 11, 18), LocalTime.of(10, 0),
				LocalTime.of(10, 0).plusHours(4));
		avlList = List.of(schedule1, schedule2);
	}

	@Test
	void getAvailabilitySlots() throws Exception {

		when(cleanService.getAvailability(1, LocalDate.of(2025, 11, 18), LocalTime.of(10, 0), 4)).thenReturn(avlList);

		mockMvc.perform(
				get("/v1/home-services/cleaning/availability").param("serviceId", "1").param("date", "2025-11-18"))
				.andExpect(status().isOk());
	}

}
