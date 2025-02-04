package com.example.schedule.repository;

import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.exception.CustomException;
import com.example.schedule.exception.ErrorCode;
import com.example.schedule.util.EmptyTool;
import com.example.schedule.util.Paging;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class ScheduleRepositoryImpl implements ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public ScheduleResponseDto saveSchedule(Schedule schedule, String author) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);
        insert.withTableName("schedule").usingGeneratedKeyColumns("id");

        Map<String, Object> params = new HashMap<>();
        params.put("author_id", schedule.getAuthorId());
        params.put("todo", schedule.getTodo());
        params.put("password", schedule.getPassword());

        LocalDateTime now = LocalDateTime.now();
        params.put("reg_date", now);
        params.put("mod_date", now);
        params.put("is_deleted", 0);

        Number id = insert.executeAndReturnKey(params);
        return new ScheduleResponseDto(id.longValue(), author, schedule.getTodo(), now, now);
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules(Schedule dto, Paging paging) {
        String sql = "SELECT s.id, u.name author, s.todo, s.reg_date, s.mod_date " +
                "FROM schedule s " +
                "JOIN user u ON s.author_id = u.id " +
                "WHERE s.is_deleted = 0 ";
        List<Object> params = new ArrayList<>();

        if (EmptyTool.notEmpty(dto.getAuthorId())) {
            sql += " AND s.author_id = ?";
            params.add(dto.getAuthorId());
        }
        if (EmptyTool.notEmpty(dto.getModDate())) {
            sql += " AND s.mod_date BETWEEN ? AND ?";
            params.add(dto.getModDate());
            params.add(dto.getModDate().plusDays(1));
        }

        sql += " ORDER BY s.mod_date DESC";

        if (EmptyTool.notEmpty(paging)) {
            sql += " LIMIT ?, ?";
            params.add(paging.getStart());
            params.add(paging.getEnd());
        }

        log.info("sql = {}", sql);
        return jdbcTemplate.query(sql, scheduleRowMapper(), params.toArray());
    }

    @Override
    public Schedule findScheduleById(Long id) {
        List<Schedule> result = jdbcTemplate.query(
                "SELECT s.id, s.author_id, u.name author, s.todo, s.password, s.is_deleted, s.reg_date, s.mod_date " +
                        "FROM schedule s " +
                        "JOIN user u ON s.author_id = u.id " +
                        "WHERE s.id = ?", scheduleRowMapper2(), id);
        return result.stream().findAny().orElseThrow(() -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND, String.valueOf(id)));
    }

    @Override
    public int updateSchedule(Long id, Schedule schedule) {
        String sql = "UPDATE schedule SET author_id = ?, todo = ? WHERE is_deleted = 0 AND id = ?";
        return jdbcTemplate.update(sql, schedule.getAuthorId(), schedule.getTodo(), id);
    }

    @Override
    public int deleteSchedule(Long id) {
        return jdbcTemplate.update("UPDATE schedule SET is_deleted = 1 WHERE id = ?", id);
    }


    private RowMapper<Schedule> scheduleRowMapper2() {
        return (rs, rowNum) -> new Schedule(
                rs.getLong("id"),
                rs.getLong("author_id"),
                rs.getString("author"),
                rs.getString("todo"),
                rs.getString("password"),
                rs.getBoolean("is_deleted"),
                rs.getTimestamp("reg_date").toLocalDateTime(),
                rs.getTimestamp("mod_date").toLocalDateTime()
        );
    }

    private RowMapper<ScheduleResponseDto> scheduleRowMapper() {
        return (rs, rowNum) -> new ScheduleResponseDto(
                rs.getLong("id"),
                rs.getString("author"),
                rs.getString("todo"),
                rs.getTimestamp("reg_date").toLocalDateTime(),
                rs.getTimestamp("mod_date").toLocalDateTime()
        );
    }
}
