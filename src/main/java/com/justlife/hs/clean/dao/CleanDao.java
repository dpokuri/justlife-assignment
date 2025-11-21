package com.justlife.hs.clean.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.justlife.hs.clean.model.BookingInfo;
import com.justlife.hs.clean.model.Schedule;
import com.justlife.hs.clean.request.CreateBookingRequest;
import com.justlife.hs.clean.request.ProfSlot;
import com.justlife.hs.clean.util.Slot;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
@PropertySource("classpath:com/justlife/hs/clean/dao/CleanDao.xml")
public class CleanDao {

	private final NamedParameterJdbcTemplate npjt;

	private final JdbcTemplate jdbcTemplate;

	@Value("${com.justlife.hs.clean.dao.CleanDao.getSchedulesByDate}")
	private String getSchedulesByDate;

	@Value("${com.justlife.hs.clean.dao.CleanDao.getSchedulesByDateAndTime}")
	private String getSchedulesByDateAndTime;

	@Value("${com.justlife.hs.clean.dao.CleanDao.storeBooking}")
	private String storeBooking;

	@Value("${com.justlife.hs.clean.dao.CleanDao.getSlotsByDateAndtime}")
	private String getSlotsByDateAndtime;

	@Value("${com.justlife.hs.clean.dao.CleanDao.getBookingInfoById}")
	private String getBookingInfoById;

	@Value("${com.justlife.hs.clean.dao.CleanDao.getMergableSlots}")
	private String getMergableSlots;

	@Value("${com.justlife.hs.clean.dao.CleanDao.deleteSlots}")
	private String deleteSlots;

	@Value("${com.justlife.hs.clean.dao.CleanDao.updateBooking}")
	private String updateBooking;

	private final RowMapper<Schedule> ScheduleRowMapper = BeanPropertyRowMapper.newInstance(Schedule.class);
	private final RowMapper<ProfSlot> profRowMapper = BeanPropertyRowMapper.newInstance(ProfSlot.class);
	private final RowMapper<BookingInfo> bookRowMapper = BeanPropertyRowMapper.newInstance(BookingInfo.class);
	private final RowMapper<Schedule> slotRowMapper = BeanPropertyRowMapper.newInstance(Schedule.class);

	public List<Long> getAvailableVehicles(LocalDate date) {
		String sql = "SELECT id FROM test.vehicle_info";
		return jdbcTemplate.queryForList(sql, Long.class);

	}

	public List<Long> getAvailableProfs(LocalDate date) {
		String sql = "SELECT id FROM test.professional_info";
		return jdbcTemplate.queryForList(sql, Long.class);

	}

	public int[] storeSchedules(List<Schedule> avls) {

		return jdbcTemplate.batchUpdate("INSERT INTO test.prof_schedule(\n"
				+ "	prof_id, vehicle_id, service_id, date, start_time, end_time)\n" + "	VALUES (?, ?, ?, ?, ?, ?);",
				new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						ps.setLong(1, avls.get(i).getProfId());
						ps.setLong(2, avls.get(i).getVehicleId());
						ps.setInt(3, avls.get(i).getServiceId());
						ps.setObject(4, avls.get(i).getDate());
						ps.setObject(5, avls.get(i).getStartTime());
						ps.setObject(6, avls.get(i).getEndTime());
					}

					@Override
					public int getBatchSize() {
						return avls.size();
					}

				});
	}

	public List<Schedule> getAvailability(int serviceId, LocalDate date) {
		final Map<String, Object> params = Map.of("serviceId", serviceId, "date", date);
		return Optional.ofNullable(npjt.query(getSchedulesByDate, params, ScheduleRowMapper))
				.orElse(new ArrayList<Schedule>());
	}

	public List<Schedule> getAvailability(int serviceId, LocalDate date, LocalTime startTime, LocalTime endTime) {
		final Map<String, Object> params = Map.of("serviceId", serviceId, "date", date, "startTime", startTime,
				"endTime", endTime);
		return Optional.ofNullable(npjt.query(getSchedulesByDateAndTime, params, ScheduleRowMapper))
				.orElse(new ArrayList<Schedule>());

	}

	public long storeBookingInfo(CreateBookingRequest req) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		npjt.update(storeBooking, new BeanPropertySqlParameterSource(req), keyHolder, new String[] { "id" });
		return keyHolder.getKey().longValue();
	}

	public int[] storeBookingProfMap(long bookingId, List<ProfSlot> profSlots) {

		return jdbcTemplate.batchUpdate(
				"INSERT INTO test.booking_prof_map(\n" + "	booking_id, prof_id)\n" + "	VALUES (?, ?);",
				new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						ps.setLong(1, bookingId);
						ps.setLong(2, profSlots.get(i).getProfId());
					}

					@Override
					public int getBatchSize() {
						return profSlots.size();
					}

				});
	}

	public void updateSchedule(List<ProfSlot> profSlots) {

	}

	public List<ProfSlot> getAvailableProfs(CreateBookingRequest req) {

		final Map<String, Object> params = Map.of("serviceId", req.getServiceId(), "date", req.getDate(), "startTime",
				req.getStartTime(), "endTime", req.getEndTime(), "profCount", req.getProfCount());

		return Optional.ofNullable(npjt.query(getSlotsByDateAndtime, params, profRowMapper))
				.orElse(new ArrayList<ProfSlot>());
	}

	public int[] updateBookedSlots(List<Slot> bookedSlots) {

		return jdbcTemplate.batchUpdate(
				"UPDATE test.prof_schedule SET start_time=?, end_time =?, updated_on=CURRENT_TIMESTAMP, status='BOOKED' \n"
						+ " WHERE id = ?;",
				new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						ps.setObject(1, bookedSlots.get(i).getStartTime());
						ps.setObject(2, bookedSlots.get(i).getEndTime());
						ps.setLong(3, bookedSlots.get(i).getSlotId());
					}

					@Override
					public int getBatchSize() {
						return bookedSlots.size();
					}

				});
	}

	public int[] updateProfsAvailability(List<ProfSlot> dsl) {
		return jdbcTemplate.batchUpdate("INSERT INTO test.prof_schedule(\n"
				+ "	prof_id, date, start_time, end_time, vehicle_id, service_id, status)\n"
				+ "	VALUES (?, ?, ?, ?, ?, ?, ?);", new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						ps.setLong(1, dsl.get(i).getProfId());
						ps.setObject(2, dsl.get(i).getDate());
						ps.setObject(3, dsl.get(i).getStartTime());
						ps.setObject(4, dsl.get(i).getEndTime());
						ps.setLong(5, dsl.get(i).getVehicleId());
						ps.setLong(6, dsl.get(i).getServiceId());
						ps.setString(7, "AVAILABLE");
					}

					@Override
					public int getBatchSize() {
						return dsl.size();
					}
				});

	}

	public List<BookingInfo> getBookingInfo(long bookingId) {
		final Map<String, Object> params = Map.of("bookingId", bookingId);
		return Optional.ofNullable(npjt.query(getBookingInfoById, params, bookRowMapper))
				.orElse(new ArrayList<BookingInfo>());
	}

	public List<Schedule> getMergableSlots(BookingInfo bi, List<Long> profIds) {
		final Map<String, Object> params = Map.of("date", bi.getDate(), "startTime", bi.getStartTime(), "endTime",
				bi.getEndTime(), "serviceId", bi.getServiceId(), "profIds", profIds);
		return Optional.ofNullable(npjt.query(getMergableSlots, params, slotRowMapper))
				.orElse(new ArrayList<Schedule>());
	}

	public int[] storeMergedSlots(List<Schedule> newSlots) {

		return jdbcTemplate.batchUpdate("INSERT INTO test.prof_schedule(\n"
				+ "	prof_id, date, start_time, end_time, vehicle_id, service_id, status)\n"
				+ "	VALUES (?, ?, ?, ?, ?, ?, ?);", new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						ps.setLong(1, newSlots.get(i).getProfId());
						ps.setObject(2, newSlots.get(i).getDate());
						ps.setObject(3, newSlots.get(i).getStartTime());
						ps.setObject(4, newSlots.get(i).getEndTime());
						ps.setLong(5, newSlots.get(i).getVehicleId());
						ps.setLong(6, newSlots.get(i).getServiceId());
						ps.setString(7, "AVAILABLE");
					}

					@Override
					public int getBatchSize() {
						return newSlots.size();
					}
				});
	}

	public int deleteExistingSlots(List<Long> slotIds) {
		final Map<String, Object> params = Map.of("ids", slotIds);
		return npjt.update(deleteSlots, params);
	}

	public int[] deleteBookedSlots(List<BookingInfo> list) {
		return jdbcTemplate.batchUpdate("DELETE FROM test.prof_schedule \n"
				+ "	WHERE prof_id = ? AND date = ? AND start_time = ? AND end_time = ? AND status = 'BOOKED' \n",
				new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						ps.setLong(1, list.get(i).getProfId());
						ps.setObject(2, list.get(i).getDate());
						ps.setObject(3, list.get(i).getStartTime());
						ps.setObject(4, list.get(i).getEndTime());
					}

					@Override
					public int getBatchSize() {
						return list.size();
					}
				});

	}

	public int deleteBookingProfMappingData(long bookingId) {
		final Map<String, Object> params = Map.of("id", bookingId);
		return npjt.update("DELETE FROM test.booking_prof_map WHERE booking_id=:id", params);

	}

	public long updateBookingInfo(CreateBookingRequest req) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		npjt.update(updateBooking, new BeanPropertySqlParameterSource(req), keyHolder, new String[] { "id" });
		return keyHolder.getKey().longValue();
	}

}
