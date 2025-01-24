package com.example.schedule.repository;

import com.example.schedule.dto.UserResponseDto;
import com.example.schedule.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public UserResponseDto saveUser(User user) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);
        insert.withTableName("user").usingGeneratedKeyColumns("id");

        Map<String, Object> params = new HashMap<>();
        params.put("name", user.getName());
        if(StringUtils.hasText(user.getEmail())) {
            params.put("email", user.getEmail());
        }
        LocalDateTime now = LocalDateTime.now();
        params.put("regDate", now);
        params.put("modDate", now);

        Number id = insert.executeAndReturnKey(params);
        return new UserResponseDto(id.longValue(), user.getName(), user.getEmail(), now, now);
    }

    @Override
    public User findUserByName(String name) {
        List<User> result = jdbcTemplate.query("SELECT * FROM user WHERE name = ?", userRowMapper(), name);
        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with name: " + name));
    }

    private RowMapper<User> userRowMapper() {
        return new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new User(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getTimestamp("regDate").toLocalDateTime(),
                        rs.getTimestamp("modDate").toLocalDateTime()
                );
            }
        };
    }
}
