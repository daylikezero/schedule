package com.example.schedule.repository;

import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
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
    public ScheduleResponseDto createSchedule(Schedule schedule, String author) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);
        insert.withTableName("schedule").usingGeneratedKeyColumns("id");

        Map<String, Object> params = new HashMap<>();
        params.put("author_id", schedule.getAuthorId());
        params.put("todo", schedule.getTodo());
        params.put("password", schedule.getPassword());
        // 등록일, 수정일 생성
        LocalDateTime now = LocalDateTime.now();
        params.put("regDate", now);
        params.put("modDate", now);

        Number id = insert.executeAndReturnKey(params);
        return new ScheduleResponseDto(id.longValue(), author, schedule.getTodo(), now, now);
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules(Schedule dto) {
        // 조건에 따라 동적 sql 생성
        String sql = "select s.id, u.name as author, s.todo, s.regDate, s.modDate from schedule s join user u on s.author_id = u.id where 1=1";
        List<Object> params = new ArrayList<>();

        if (dto.getAuthorId() != null || dto.getModDate() != null) {
            if (dto.getAuthorId() != null) {
                sql += " and s.author_id = ?";
                params.add(dto.getAuthorId());
            }
            if (dto.getModDate() != null) {
                LocalDateTime from = dto.getModDate();
                LocalDateTime to = from.plusDays(1);
                sql += " and s.modDate between ? and ?";
                params.add(from);
                params.add(to);
            }
        }
        log.info("sql={}", sql);

        return jdbcTemplate.query(sql, scheduleRowMapper(), params.toArray());
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
