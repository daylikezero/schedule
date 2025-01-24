package com.example.schedule.repository;

import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

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
        params.put("regDate", now);
        params.put("modDate", now);

        Number id = insert.executeAndReturnKey(params);
        return new ScheduleResponseDto(id.longValue(), author, schedule.getTodo(), now, now);
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules(Schedule dto) {
        String sql = "SELECT s.id, u.name author, s.todo, s.regDate, s.modDate " +
                "FROM schedule s " +
                "JOIN user u ON s.author_id = u.id " +
                "WHERE 1=1";
        List<Object> params = new ArrayList<>();

        if (dto.getAuthorId() != null || dto.getModDate() != null) {
            if (dto.getAuthorId() != null) {
                sql += " AND s.author_id = ?";
                params.add(dto.getAuthorId());
            }
            if (dto.getModDate() != null) {
                sql += " AND s.modDate < ?";
                params.add(dto.getModDate().plusDays(1));
            }
        }
        sql += " ORDER BY s.modDate DESC";
        log.info("sql = {}", sql);
        return jdbcTemplate.query(sql, scheduleRowMapper(), params.toArray());
    }

    @Override
    public Schedule findScheduleById(Long id) {
        List<Schedule> result = jdbcTemplate.query(
                "SELECT s.id, u.name author, s.todo, s.regDate, s.modDate " +
                        "FROM schedule s " +
                        "JOIN user u ON s.author_id = u.id " +
                        "WHERE s.id = ?", scheduleRowMapper2(), id);
        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id));
    }

    @Override
    public int updateSchedule(Long id, Schedule schedule) {
        String sql = "UPDATE schedule SET author_id = ?, todo = ?, modDate = ? WHERE id = ? AND password = ?";
        return jdbcTemplate.update(sql, schedule.getAuthorId(), schedule.getTodo(), schedule.getModDate(), id, schedule.getPassword());
    }

    @Override
    public int deleteSchedule(Long id, String password) {
        return jdbcTemplate.update("DELETE FROM schedule WHERE id = ? AND password = ?", id, password);
    }


    private RowMapper<Schedule> scheduleRowMapper2() {
        return (rs, rowNum) -> new Schedule(
                rs.getLong("id"),
                rs.getString("author"),
                rs.getString("todo"),
                rs.getTimestamp("regDate").toLocalDateTime(),
                rs.getTimestamp("modDate").toLocalDateTime()
        );
    }

    private RowMapper<ScheduleResponseDto> scheduleRowMapper() {
        return (rs, rowNum) -> new ScheduleResponseDto(
                rs.getLong("id"),
                rs.getString("author"),
                rs.getString("todo"),
                rs.getTimestamp("regDate").toLocalDateTime(),
                rs.getTimestamp("modDate").toLocalDateTime()
        );
    }
}
